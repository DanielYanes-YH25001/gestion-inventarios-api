package ues.ids.poo.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockSucursalResponseDTO {
    private Integer idProducto;
    private String nombreProducto;
    private Integer cantidadDisponible;
}
