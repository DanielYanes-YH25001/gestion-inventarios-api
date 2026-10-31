package ues.ids.poo.api.dto.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Fran Recinos
 */
@Data
public class ProductoResponseDTO {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precioBase;
    private BigDecimal precioVenta;
    private Integer idProveedor;
    private Integer idCategoria;
}
