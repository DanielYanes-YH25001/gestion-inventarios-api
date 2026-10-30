package ues.ids.poo.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProveedorRequestDTO {

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    private String nombre;

    @NotBlank(message = "El contacto del proveedor es obligatorio")
    private String contacto;

    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "El formato del teléfono es inválido")
    private String telefono;

    private String direccion;
}
