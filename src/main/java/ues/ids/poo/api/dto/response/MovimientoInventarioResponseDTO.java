package ues.ids.poo.api.dto.response;

import ues.ids.poo.api.model.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MovimientoInventarioResponseDTO {
    private Long idMovimiento;
    private Integer idProducto;
    private Integer idSucursal;
    private Integer idUsuario;
    private TipoMovimiento tipoMovimiento;
    private Integer cantidad;
    private LocalDateTime fechaRegistro;
}
