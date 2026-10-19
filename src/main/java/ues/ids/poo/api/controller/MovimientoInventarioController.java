package ues.ids.poo.api.controller;

import ues.ids.poo.api.dto.request.MovimientoInventarioRequestDTO;
import ues.ids.poo.api.dto.response.MovimientoInventarioResponseDTO;
import ues.ids.poo.api.service.MovimientoInventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoInventarioResponseDTO> registrar(
        @Valid @RequestBody MovimientoInventarioRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.registrar(request));
    }

    @GetMapping
    public ResponseEntity<List<MovimientoInventarioResponseDTO>> obtenerHistorial() {
        return ResponseEntity.ok(movimientoService.obtenerHistorial());
    }
}
