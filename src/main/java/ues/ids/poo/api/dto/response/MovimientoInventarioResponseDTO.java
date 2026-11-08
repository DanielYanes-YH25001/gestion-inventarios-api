package ues.ids.poo.api.dto.response;

import java.time.LocalDateTime;

import ues.ids.poo.api.enums.TipoMovimiento;

public record MovimientoInventarioResponseDTO(
    Long idMovimientoInventario,
    Integer idProducto,
    Integer idSucursal,
    Integer idUsuario,
    TipoMovimiento tipoMovimiento,
    Integer cantidad,
    LocalDateTime fechaRegistro
) {}
