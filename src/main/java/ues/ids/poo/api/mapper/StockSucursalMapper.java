package ues.ids.poo.api.mapper;

import org.springframework.stereotype.Component;
import ues.ids.poo.api.dto.response.StockSucursalResponseDTO;
import ues.ids.poo.api.model.StockSucursal;

@Component
public class StockSucursalMapper {

    public StockSucursalResponseDTO toStockResponse(StockSucursal stock) {
        if (stock == null) {
            return null;
        }

        return new StockSucursalResponseDTO(
            stock.getProducto() != null ? stock.getProducto().getIdProducto() : null,
            stock.getProducto() != null ? stock.getProducto().getNombre() : null,
            stock.getCantidadDisponible()
        );
    }
}
