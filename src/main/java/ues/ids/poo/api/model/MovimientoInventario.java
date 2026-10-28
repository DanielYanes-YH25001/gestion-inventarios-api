package ues.ids.poo.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento_inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    @Column(name = "tipo_movimiento", nullable = false, length = 20)
    private String tipoMovimiento; // 'ENTRADA' o 'SALIDA'

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    // Relación con Producto
    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;

    // Relación con Sucursal
    @Column(name = "id_sucursal", nullable = false)
    private Integer idSucursal;

    // Relación con Usuario
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

}