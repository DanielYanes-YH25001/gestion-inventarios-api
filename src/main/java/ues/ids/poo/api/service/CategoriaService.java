package ues.ids.poo.api.service;

import ues.ids.poo.api.dto.request.CategoriaRequestDTO;
import ues.ids.poo.api.dto.response.CategoriaResponseDTO;
import ues.ids.poo.api.model.Categoria;
import ues.ids.poo.api.mapper.CategoriaMapper;
import ues.ids.poo.api.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO request) {
        Categoria entidad = categoriaMapper.toEntity(request);
        Categoria guardada = categoriaRepository.save(entidad);
        return categoriaMapper.toResponseDTO(guardada);
    }

    public List<CategoriaResponseDTO> obtenerTodas() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toResponseDTO)
                .toList();
    }

    public CategoriaResponseDTO obtenerPorId(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));

        return categoriaMapper.toResponseDTO(categoria);
    }

    public CategoriaResponseDTO actualizarCategoria(Integer id, CategoriaRequestDTO request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));

        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        return categoriaMapper.toResponseDTO(categoriaRepository.save(categoria));
    }

    public void eliminarCategoria(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));

        categoriaRepository.delete(categoria);
    }
}
