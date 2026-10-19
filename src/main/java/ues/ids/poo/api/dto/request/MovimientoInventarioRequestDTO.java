package ues.ids.poo.api.dto.request;

import ues.ids.poo.api.model.TipoMovimiento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovimientoInventarioRequestDTO {

    @NotNull(message = "El producto es requerido")
    private Integer idProducto;

    @NotNull(message = "La sucursal es requerida")
    private Integer idSucursal;

    @NotNull(message = "El usuario es requerido")
    private Integer idUsuario;

    @NotNull(message = "El tipo de movimiento es requerido")
    private TipoMovimiento tipoMovimiento;

    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
}
