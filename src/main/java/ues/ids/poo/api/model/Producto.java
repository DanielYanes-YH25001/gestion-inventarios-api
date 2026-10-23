package ues.ids.poo.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Fran Recinos
 */
@Data
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "precio_base", nullable = false)
    private BigDecimal precioBase;

    @Column(name = "precio_venta", nullable = false)
    private BigDecimal precioVenta;

    // --- RELACIONES FORÁNEAS DE LA TAREA 1 ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;
}