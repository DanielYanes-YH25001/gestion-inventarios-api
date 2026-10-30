package ues.ids.poo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ues.ids.poo.api.dto.request.ProductoRequestDTO;
import ues.ids.poo.api.dto.response.ProductoResponseDTO;
import ues.ids.poo.api.service.ProductoService;

import java.util.List;

/**
 * @author Fran Recinos
 */
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<ProductoResponseDTO> listarTodos() {
        return productoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ProductoResponseDTO obtenerPorId(@PathVariable Integer id) {
        return productoService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoResponseDTO crear(@Valid @RequestBody ProductoRequestDTO dto) {
        return productoService.crearProducto(dto);
    }

    @PutMapping("/{id}")
    public ProductoResponseDTO actualizar(@PathVariable Integer id, @Valid @RequestBody ProductoRequestDTO dto) {
        return productoService.actualizarProducto(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
    }
}
