package ues.ids.poo.api.dto.response;

import java.math.BigDecimal;

/**
 * @author Fran Recinos
 */
public record ProductoResponseDTO(
    Integer idProducto,
    String nombre,
    String descripcion,
    BigDecimal precioBase,
    BigDecimal precioVenta,
    Integer idProveedor,
    Integer idCategoria
) {}
