-- CREATE DATABASE gestion_inventarios_api;

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('ADMIN', 'LECTURA'))
);

CREATE TABLE IF NOT EXISTS categoria (
    id_categoria SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS proveedor (
    id_proveedor SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100),
    telefono VARCHAR(20),
    direccion VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS producto (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio_base NUMERIC(10,2) NOT NULL,
	precio_venta NUMERIC(10,2) NOT NULL,
    id_proveedor INT NOT NULL,
    id_categoria INT NOT NULL,
    CONSTRAINT fk_producto_proveedor FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor),
    CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

CREATE TABLE IF NOT EXISTS sucursal (
    id_sucursal SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    activa BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE IF NOT EXISTS stock_sucursal (
    id_stock_sucursal SERIAL PRIMARY KEY,
    id_producto INT NOT NULL,
    id_sucursal INT NOT NULL,
    cantidad_disponible INT DEFAULT 0 NOT NULL,
    CONSTRAINT fk_stock_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
    CONSTRAINT fk_stock_sucursal FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal),
    CONSTRAINT uq_producto_sucursal UNIQUE (id_producto, id_sucursal)
);

CREATE TABLE IF NOT EXISTS movimiento_inventario (
    id_movimiento_inventario BIGSERIAL PRIMARY KEY,
    tipo_movimiento VARCHAR(20) NOT NULL CHECK (tipo_movimiento IN ('ENTRADA', 'SALIDA')),
    cantidad INT NOT NULL CHECK (cantidad > 0),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    id_producto INT NOT NULL,
    id_sucursal INT NOT NULL,
    id_usuario INT NOT NULL,
    CONSTRAINT fk_movimiento_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
    CONSTRAINT fk_movimiento_sucursal FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal),
    CONSTRAINT fk_movimiento_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);
