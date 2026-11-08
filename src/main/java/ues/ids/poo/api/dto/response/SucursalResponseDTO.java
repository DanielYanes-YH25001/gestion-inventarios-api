package ues.ids.poo.api.dto.response;

public record SucursalResponseDTO(
    Integer idSucursal,
    String nombre,
    String direccion,
    Boolean activa
) {}
