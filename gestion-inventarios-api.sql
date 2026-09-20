-- 0. Creamos la base de Datos
CREATE DATABASE gestion-inventarios-api;
--Dejo el comando de conexion por sino lo hace en ambiente grafico
--(Solo desmarque el comentario)
--\c gestion-inventarios-api

-- =========================================================
-- EXTENSIONES
-- =========================================================
CREATE EXTENSION IF NOT EXISTS "pgcrypto";  -- para gen_random_uuid()

-- =========================================================
-- TIPOS ENUMERADOS
-- =========================================================
CREATE TYPE tipo_movimiento AS ENUM ('ENTRADA', 'SALIDA', 'AJUSTE');
CREATE TYPE estado_producto AS ENUM ('ACTIVO', 'INACTIVO', 'DESCONTINUADO');
CREATE TYPE estado_transferencia AS ENUM ('PENDIENTE', 'EN_TRANSITO', 'COMPLETADA', 'CANCELADA');

-- =========================================================
-- 1. ROL (secundaria)
-- =========================================================
CREATE TABLE rol (
    id_rol      SERIAL PRIMARY KEY,
    nombre      VARCHAR(50)  NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    creado_en   TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

-- =========================================================
-- 2. SUCURSAL (secundaria)
-- =========================================================
CREATE TABLE sucursal (
    id_sucursal SERIAL PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL UNIQUE,
    direccion   VARCHAR(255),
    telefono    VARCHAR(30),
    activa      BOOLEAN      NOT NULL DEFAULT TRUE,
    creado_en   TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

-- =========================================================
-- 3. USUARIO (primaria)
-- =========================================================
CREATE TABLE usuario (
    id_usuario  SERIAL PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    id_rol      INT          NOT NULL REFERENCES rol(id_rol),
    id_sucursal INT          REFERENCES sucursal(id_sucursal),  -- sucursal por defecto
    activo      BOOLEAN      NOT NULL DEFAULT TRUE,
    creado_en   TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_usuario_rol ON usuario(id_rol);
CREATE INDEX idx_usuario_suc ON usuario(id_sucursal);

-- =========================================================
-- 4. CATEGORÍA (primaria) - soporta jerarquía
-- =========================================================
CREATE TABLE categoria (
    id_categoria  SERIAL PRIMARY KEY,
    nombre        VARCHAR(100) NOT NULL UNIQUE,
    descripcion   VARCHAR(255),
    id_padre      INT          REFERENCES categoria(id_categoria) ON DELETE SET NULL,
    creado_en     TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_categoria_padre ON categoria(id_padre);

-- =========================================================
-- 5. PROVEEDOR (primaria)
-- =========================================================
CREATE TABLE proveedor (
    id_proveedor SERIAL PRIMARY KEY,
    nombre       VARCHAR(150) NOT NULL,
    nit          VARCHAR(30)  UNIQUE,
    contacto     VARCHAR(100),
    telefono     VARCHAR(30),
    email        VARCHAR(150),
    direccion    VARCHAR(255),
    activo       BOOLEAN      NOT NULL DEFAULT TRUE,
    creado_en    TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

-- =========================================================
-- 6. PRODUCTO (primaria)
-- =========================================================
CREATE TABLE producto (
    id_producto   SERIAL PRIMARY KEY,
    sku           VARCHAR(50)  NOT NULL UNIQUE,
    nombre        VARCHAR(150) NOT NULL,
    descripcion   TEXT,
    precio        NUMERIC(12,2) NOT NULL CHECK (precio >= 0),
    costo         NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (costo >= 0),
    stock_minimo  INT           NOT NULL DEFAULT 0,
    id_categoria  INT           NOT NULL REFERENCES categoria(id_categoria),
    id_proveedor  INT           REFERENCES proveedor(id_proveedor),
    estado        estado_producto NOT NULL DEFAULT 'ACTIVO',
    creado_en     TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    actualizado_en TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_producto_categoria ON producto(id_categoria);
CREATE INDEX idx_producto_proveedor ON producto(id_proveedor);
CREATE INDEX idx_producto_estado    ON producto(estado);

-- =========================================================
-- 7. STOCK_SUCURSAL (secundaria) - stock real por ubicación
-- =========================================================
CREATE TABLE stock_sucursal (
    id_stock      SERIAL PRIMARY KEY,
    id_producto   INT NOT NULL REFERENCES producto(id_producto) ON DELETE CASCADE,
    id_sucursal   INT NOT NULL REFERENCES sucursal(id_sucursal) ON DELETE CASCADE,
    cantidad      INT NOT NULL DEFAULT 0 CHECK (cantidad >= 0),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE (id_producto, id_sucursal)
);
CREATE INDEX idx_stock_producto ON stock_sucursal(id_producto);
CREATE INDEX idx_stock_sucursal ON stock_sucursal(id_sucursal);

-- =========================================================
-- 8. MOVIMIENTO (primaria) - tipo Kardex
-- =========================================================
CREATE TABLE movimiento (
    id_movimiento SERIAL PRIMARY KEY,
    id_producto   INT NOT NULL REFERENCES producto(id_producto),
    id_usuario    INT NOT NULL REFERENCES usuario(id_usuario),
    id_sucursal   INT NOT NULL REFERENCES sucursal(id_sucursal),
    tipo          tipo_movimiento NOT NULL,
    cantidad      INT NOT NULL CHECK (cantidad > 0),
    motivo        VARCHAR(255),
    referencia    VARCHAR(100),         -- ej. factura, orden, etc.
    creado_en     TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_mov_producto ON movimiento(id_producto);
CREATE INDEX idx_mov_usuario  ON movimiento(id_usuario);
CREATE INDEX idx_mov_sucursal ON movimiento(id_sucursal);
CREATE INDEX idx_mov_fecha    ON movimiento(creado_en DESC);

-- =========================================================
-- 9. TRANSFERENCIA (secundaria) - entre sucursales
-- =========================================================
CREATE TABLE transferencia (
    id_transferencia SERIAL PRIMARY KEY,
    id_producto      INT NOT NULL REFERENCES producto(id_producto),
    id_sucursal_origen  INT NOT NULL REFERENCES sucursal(id_sucursal),
    id_sucursal_destino INT NOT NULL REFERENCES sucursal(id_sucursal),
    cantidad         INT NOT NULL CHECK (cantidad > 0),
    estado           estado_transferencia NOT NULL DEFAULT 'PENDIENTE',
    id_usuario       INT NOT NULL REFERENCES usuario(id_usuario),
    observaciones    VARCHAR(255),
    creado_en        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    actualizado_en   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CHECK (id_sucursal_origen <> id_sucursal_destino)
);
CREATE INDEX idx_transf_producto ON transferencia(id_producto);
CREATE INDEX idx_transf_origen   ON transferencia(id_sucursal_origen);
CREATE INDEX idx_transf_destino  ON transferencia(id_sucursal_destino);
CREATE INDEX idx_transf_estado   ON transferencia(estado);

-- =========================================================
-- 10. Trigger para mantener stock sincronizado con movimientos
-- =========================================================


CREATE OR REPLACE FUNCTION fn_aplicar_movimiento()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.tipo = 'ENTRADA' THEN
        INSERT INTO stock_sucursal (id_producto, id_sucursal, cantidad)
        VALUES (NEW.id_producto, NEW.id_sucursal, NEW.cantidad)
        ON CONFLICT (id_producto, id_sucursal)
        DO UPDATE SET cantidad = stock_sucursal.cantidad + EXCLUDED.cantidad,
                      actualizado_en = NOW();

    ELSIF NEW.tipo = 'SALIDA' THEN
        UPDATE stock_sucursal
           SET cantidad = cantidad - NEW.cantidad,
               actualizado_en = NOW()
         WHERE id_producto = NEW.id_producto
           AND id_sucursal = NEW.id_sucursal;

        IF NOT FOUND THEN
            RAISE EXCEPTION 'No existe stock para producto % en sucursal %',
                NEW.id_producto, NEW.id_sucursal;
        END IF;

    ELSIF NEW.tipo = 'AJUSTE' THEN
        UPDATE stock_sucursal
           SET cantidad = NEW.cantidad,
               actualizado_en = NOW()
         WHERE id_producto = NEW.id_producto
           AND id_sucursal = NEW.id_sucursal;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_movimiento_stock
AFTER INSERT ON movimiento
FOR EACH ROW EXECUTE FUNCTION fn_aplicar_movimiento();