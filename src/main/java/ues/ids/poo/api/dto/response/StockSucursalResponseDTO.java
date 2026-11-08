package ues.ids.poo.api.dto.response;

public record StockSucursalResponseDTO(
    Integer idProducto,
    String nombreProducto,
    Integer cantidadDisponible
) {}
