package ues.ids.poo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ues.ids.poo.api.dto.request.SucursalRequestDTO;
import ues.ids.poo.api.dto.response.SucursalResponseDTO;
import ues.ids.poo.api.service.SucursalService;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalService sucursalService;

    @PostMapping
    public ResponseEntity<SucursalResponseDTO> crear(@Valid @RequestBody SucursalRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.crearSucursal(request));
    }

    @GetMapping
    public ResponseEntity<List<SucursalResponseDTO>> listarTodas() {
        return ResponseEntity.ok(sucursalService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody SucursalRequestDTO request
    ) {
        return ResponseEntity.ok(sucursalService.actualizarSucursal(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}