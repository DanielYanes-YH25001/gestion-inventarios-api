package ues.ids.poo.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SucursalResponseDTO {
    private Integer idSucursal;
    private String nombre;
    private String direccion;
    private Boolean activa;
}