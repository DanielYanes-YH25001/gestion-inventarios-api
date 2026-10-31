package ues.ids.poo.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ues.ids.poo.api.dto.response.StockSucursalResponseDTO;
import ues.ids.poo.api.service.StockSucursalService;

import java.util.List;

@RestController
@RequestMapping("/api/Stocksucursales")
@RequiredArgsConstructor
public class StockSucursalController {

    private final StockSucursalService stockSucursalService;

    @GetMapping("/{id}")
    public ResponseEntity<List<StockSucursalResponseDTO>> obtenerStock(@PathVariable Integer id) {
        return ResponseEntity.ok(stockSucursalService.obtenerStock(id));
    }
}