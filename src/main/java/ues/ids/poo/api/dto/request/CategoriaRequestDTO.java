package ues.ids.poo.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaRequestDTO {
    @NotBlank(message = "El nombre de la categoría es requerido")
    private String nombre;
    private String descripcion;
}
