package ues.ids.poo.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.StockSucursalResponseDTO;
import ues.ids.poo.api.mapper.StockSucursalMapper;
import ues.ids.poo.api.repository.StockSucursalRepository;
import ues.ids.poo.api.repository.SucursalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockSucursalService {

    private final StockSucursalRepository stockSucursalRepository;
    private final SucursalRepository sucursalRepository;
    private final StockSucursalMapper stockSucursalMapper;

    @Transactional(readOnly = true)
    public List<StockSucursalResponseDTO> obtenerStock(Integer idSucursal) {
        if (!sucursalRepository.existsById(idSucursal)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sucursal no encontrada");
        }

        return stockSucursalRepository.findBySucursal_IdSucursal(idSucursal).stream()
                .map(stockSucursalMapper::toStockResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StockSucursalResponseDTO> obtenerTodoElStock() {
        return stockSucursalRepository.findAll().stream()
                .map(stockSucursalMapper::toStockResponse)
                .toList();
    }
}
