package ues.ids.poo.api.dto.response;

import lombok.Data;

@Data
public class ProveedorResponseDTO {
    private Integer id;
    private String nombre;
    private String contacto;
    private String telefono;
    private String direccion;
}