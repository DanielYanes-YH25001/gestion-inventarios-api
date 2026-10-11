package ues.ids.poo.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoriaResponseDTO {
    private Integer idCategoria;
    private String nombre;
    private String descripcion;
}
