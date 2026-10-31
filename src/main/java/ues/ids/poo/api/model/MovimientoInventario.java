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
    @Column
    private Long idMovimientoInventario;

    @Column(nullable = false, length = 20)
    private String tipoMovimiento; // 'ENTRADA' o 'SALIDA'

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    // Relación con Producto
    @Column(nullable = false)
    private Integer idProducto;

    // Relación con Sucursal
    @Column(nullable = false)
    private Integer idSucursal;

    // Relación con Usuario
    @Column(nullable = false)
    private Integer idUsuario;

}
