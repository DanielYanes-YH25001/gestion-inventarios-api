package ues.ids.poo.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Fran Recinos
 */
@Data
@Entity
@Table(name = "producto")
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idProducto;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal precioBase;

    @Column(nullable = false)
    private BigDecimal precioVenta;

    // --- RELACIONES FORÁNEAS DE LA TAREA 1 ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;
}
