package ues.ids.poo.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Fran Recinos
 */
@Data
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    private String descripcion;

    // --- TAREA 3: VALIDACIONES DE PRECIO ---
    @NotNull(message = "El precio base es obligatorio")
    @Positive(message = "El precio base debe ser mayor a cero")
    private BigDecimal precioBase;

    @NotNull(message = "El precio de venta es obligatorio")
    @Positive(message = "El precio de venta debe ser mayor a cero")
    private BigDecimal precioVenta;

    @NotNull(message = "El ID del proveedor es obligatorio")
    private Integer idProveedor;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Integer idCategoria;
}
