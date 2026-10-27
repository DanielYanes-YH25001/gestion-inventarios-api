INSERT INTO usuario (nombre, rol) VALUES
    ('Administrador', 'ADMIN'),
    ('Lector', 'LECTURA');

INSERT INTO categoria (nombre, descripcion) VALUES
    ('Camisetas', 'Camisas, camisetas casuales y tops'),
    ('Pantalones', 'Jeans, pantalones de vestir y shorts'),
    ('Calzado', 'Zapatos formales, zapatillas y sandalias'),
    ('Ropa deportiva', 'Prendas para entrenamiento y actividad física'),
    ('Accesorios', 'Cinturones, gorras, bufandas y complementos de vestir'),
    ('Abrigos', 'Chaquetas, suéteres y prendas para clima fresco');

INSERT INTO proveedor (nombre, contacto, telefono, direccion) VALUES
    ('Textil Sur S.A.', 'María Gómez', '+50367659495', 'Zona Industrial 1, San Salvador'),
    ('Moda Urbana S.A. de C.V.', 'Carlos Ruiz', '+50374995056', 'Calle Principal 45, Santa Ana'),
    ('Calzados El Paso', 'Ana López', '+50379673903', 'Avenida Comercio 10, San Miguel'),
    ('Confecciones Cuscatlán', 'Rosa Elena Menjívar', '+50375345154', 'Soyapango, San Salvador'),
    ('Taller Textil La Libertad', 'José Roberto Martínez', '+50376467893', 'Santa Tecla, La Libertad'),
    ('Calzado Oriente', 'Miguel Ángel Guardado', '+50374302203', 'San Miguel, San Miguel');

INSERT INTO producto (nombre, descripcion, precio_base, precio_venta, id_proveedor, id_categoria) VALUES
    ('Camiseta Básica Algodón', 'Camiseta manga corta 100% algodón color blanco', 15.99, 22.99, 1, 1),
    ('Pantalón Denim Clásico', 'Jeans corte recto azul oscuro para hombre', 35.50, 49.99, 2, 2),
    ('Zapatillas Deportivas Runner', 'Calzado deportivo ligero para correr', 45.00, 65.00, 3, 3),
    ('Camisa de Botones Elegante', 'Camisa manga larga para oficina color celeste', 25.00, 35.99, 1, 1),
    ('Camiseta deportiva dry-fit', 'Camiseta ligera de secado rápido para entrenamiento', 12.50, 18.99, 4, 4),
    ('Blusa bordada salvadoreña', 'Blusa de algodón con bordado artesanal', 18.00, 27.50, 5, 1),
    ('Jeans corte slim para mujer', 'Pantalón denim azul de corte slim', 29.00, 42.99, 2, 2),
    ('Pantalón chino para hombre', 'Pantalón casual de algodón color beige', 24.00, 36.50, 1, 2),
    ('Sandalias de cuero', 'Sandalias casuales de cuero sintético', 16.00, 25.99, 6, 3),
    ('Zapatos escolares negros', 'Calzado escolar resistente con suela antideslizante', 22.00, 34.99, 3, 3),
    ('Licra deportiva', 'Licra elástica para correr y hacer ejercicio', 14.00, 21.50, 4, 4),
    ('Sudadera con capucha', 'Sudadera de algodón con bolsillo frontal', 22.00, 34.99, 5, 6),
    ('Cinturón casual de cuero', 'Cinturón ajustable para uso diario', 12.00, 19.50, 1, 5),
    ('Gorra bordada', 'Gorra ajustable con bordado salvadoreño', 9.00, 15.99, 4, 5),
    ('Chaqueta impermeable', 'Chaqueta liviana resistente al agua', 32.00, 48.99, 5, 6),
    ('Suéter tejido', 'Suéter tejido de punto para clima fresco', 26.00, 39.99, 2, 6);

INSERT INTO sucursal (nombre, direccion, activa) VALUES
    ('Sucursal Central', 'San Salvador', TRUE),
    ('Sucursal Occidental', 'Santa Ana', TRUE),
    ('Sucursal Oriental', 'San Miguel', TRUE);

INSERT INTO stock_sucursal (id_producto, id_sucursal, cantidad_disponible) VALUES
    (1, 1, 50),
    (1, 2, 30),
    (2, 1, 20),
    (2, 2, 15),
    (3, 1, 10),
    (4, 2, 25);

INSERT INTO movimiento_inventario (tipo_movimiento, cantidad, fecha_registro, id_producto, id_sucursal, id_usuario) VALUES
    ('ENTRADA', 55, CURRENT_TIMESTAMP - INTERVAL '5 days', 1, 1, 1),
    ('SALIDA', 5, CURRENT_TIMESTAMP - INTERVAL '2 days', 1, 1, 1),
    ('ENTRADA', 30, CURRENT_TIMESTAMP - INTERVAL '4 days', 1, 2, 1),
    ('ENTRADA', 20, CURRENT_TIMESTAMP - INTERVAL '3 days', 2, 1, 1),
    ('ENTRADA', 15, CURRENT_TIMESTAMP - INTERVAL '3 days', 2, 2, 1),
    ('ENTRADA', 12, CURRENT_TIMESTAMP - INTERVAL '10 days', 3, 1, 1),
    ('SALIDA', 2, CURRENT_TIMESTAMP - INTERVAL '1 day', 3, 1, 1),
    ('ENTRADA', 25, CURRENT_TIMESTAMP - INTERVAL '5 days', 4, 2, 1);
