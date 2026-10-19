package ues.ids.poo.api.service;

import ues.ids.poo.api.dto.request.MovimientoInventarioRequestDTO;
import ues.ids.poo.api.dto.response.MovimientoInventarioResponseDTO;
import ues.ids.poo.api.mapper.MovimientoInventarioMapper;
import ues.ids.poo.api.model.MovimientoInventario;
import ues.ids.poo.api.model.Producto;
import ues.ids.poo.api.model.StockSucursal;
import ues.ids.poo.api.model.Sucursal;
import ues.ids.poo.api.model.TipoMovimiento;
import ues.ids.poo.api.model.Usuario;
import ues.ids.poo.api.repository.MovimientoInventarioRepository;
import ues.ids.poo.api.repository.ProductoRepository;
import ues.ids.poo.api.repository.StockSucursalRepository;
import ues.ids.poo.api.repository.SucursalRepository;
import ues.ids.poo.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioService {

    private final MovimientoInventarioRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    private final StockSucursalRepository stockRepository;
    private final UsuarioRepository usuarioRepository;
    private final MovimientoInventarioMapper movimientoMapper;

    @Transactional
    public MovimientoInventarioResponseDTO registrar(MovimientoInventarioRequestDTO request) {
        Producto producto = productoRepository.findById(request.getIdProducto())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        Sucursal sucursal = sucursalRepository.findById(request.getIdSucursal())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sucursal no encontrada"));
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        StockSucursal stock = stockRepository.buscarConBloqueo(
                request.getIdProducto(),
                request.getIdSucursal()
            )
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No existe stock para el producto en la sucursal indicada"
            ));

        int cantidadMovimiento = request.getCantidad();
        if (request.getTipoMovimiento() == TipoMovimiento.ENTRADA) {
            if (stock.getCantidadDisponible() > Integer.MAX_VALUE - cantidadMovimiento) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad resultante supera el límite soportado"
                );
            }
            stock.setCantidadDisponible(stock.getCantidadDisponible() + cantidadMovimiento);
        } else if (request.getTipoMovimiento() == TipoMovimiento.SALIDA) {
            if (cantidadMovimiento > stock.getCantidadDisponible()) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad solicitada supera el stock disponible"
                );
            }
            stock.setCantidadDisponible(stock.getCantidadDisponible() - cantidadMovimiento);
        }

        stockRepository.save(stock);

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setSucursal(sucursal);
        movimiento.setUsuario(usuario);
        movimiento.setTipoMovimiento(request.getTipoMovimiento());
        movimiento.setCantidad(cantidadMovimiento);
        movimiento.setFechaRegistro(LocalDateTime.now());

        return movimientoMapper.toResponseDTO(movimientoRepository.save(movimiento));
    }

    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponseDTO> obtenerHistorial() {
        return movimientoRepository.findAllByOrderByFechaRegistroDesc().stream()
            .map(movimientoMapper::toResponseDTO)
            .toList();
    }
}
