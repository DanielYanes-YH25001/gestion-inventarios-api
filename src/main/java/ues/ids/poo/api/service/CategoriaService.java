package ues.ids.poo.api.service;

import ues.ids.poo.api.dto.request.CategoriaRequestDTO;
import ues.ids.poo.api.dto.response.CategoriaResponseDTO;
import ues.ids.poo.api.model.Categoria;
import ues.ids.poo.api.mapper.CategoriaMapper;
import ues.ids.poo.api.repository.CategoriaRepository;
import ues.ids.poo.api.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final ProductoRepository productoRepository;

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

    @Transactional
    public void eliminarCategoria(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));

        if (productoRepository.existsByCategoria_IdCategoria(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar la categoría porque tiene productos asociados"
            );
        }

        try {
            categoriaRepository.delete(categoria);
            categoriaRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar la categoría porque tiene productos asociados",
                    exception
            );
        }
    }
}
