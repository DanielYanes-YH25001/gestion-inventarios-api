package ues.ids.poo.api.mapper;

import ues.ids.poo.api.dto.response.MovimientoInventarioResponseDTO;
import ues.ids.poo.api.model.MovimientoInventario;
import org.springframework.stereotype.Component;

@Component
public class MovimientoInventarioMapper {

    public MovimientoInventarioResponseDTO toResponseDTO(MovimientoInventario movimiento) {
        return new MovimientoInventarioResponseDTO(
            movimiento.getIdMovimiento(),
            movimiento.getProducto().getIdProducto(),
            movimiento.getSucursal().getIdSucursal(),
            movimiento.getUsuario().getIdUsuario(),
            movimiento.getTipoMovimiento(),
            movimiento.getCantidad(),
            movimiento.getFechaRegistro()
        );
    }
}
