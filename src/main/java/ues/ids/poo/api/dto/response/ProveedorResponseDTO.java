package ues.ids.poo.api.dto.response;

public record ProveedorResponseDTO(
    Integer idProveedor,
    String nombre,
    String contacto,
    String telefono,
    String direccion
) {}
