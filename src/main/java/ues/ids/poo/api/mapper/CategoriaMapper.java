package ues.ids.poo.api.mapper;

import ues.ids.poo.api.dto.request.CategoriaRequestDTO;
import ues.ids.poo.api.dto.response.CategoriaResponseDTO;
import ues.ids.poo.api.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoria;
    }

    public CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
            categoria.getIdCategoria().intValue(),
            categoria.getNombre(),
            categoria.getDescripcion()
        );
    }
}
