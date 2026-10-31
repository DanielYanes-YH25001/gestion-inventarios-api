package ues.ids.poo.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SucursalRequestDTO {

    @NotBlank(message = "El nombre de la sucursal es requerido")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "La dirección de la sucursal es requerida")
    @Size(max = 255)
    private String direccion;

    @NotNull(message = "El estado de la sucursal es requerido")
    private Boolean activa;
}
