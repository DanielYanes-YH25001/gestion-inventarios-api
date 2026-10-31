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

        StockSucursalResponseDTO dto = new StockSucursalResponseDTO();

        if (stock.getProducto() != null) {
            dto.setIdProducto(stock.getProducto().getIdProducto());
            dto.setNombreProducto(stock.getProducto().getNombre());
        }

        dto.setCantidadDisponible(stock.getCantidadDisponible());

        return dto;
    }
}