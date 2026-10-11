package ues.ids.poo.api.controller;

import ues.ids.poo.api.dto.request.CategoriaRequestDTO;
import ues.ids.poo.api.dto.response.CategoriaResponseDTO;
import ues.ids.poo.api.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crear(@Valid @RequestBody CategoriaRequestDTO request) {
        CategoriaResponseDTO response = categoriaService.crearCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(categoriaService.obtenerTodas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(
        @PathVariable Integer id,
        @Valid @RequestBody CategoriaRequestDTO request
    ) {
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
