package ues.ids.poo.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.request.MovimientoInventarioRequestDTO;
import ues.ids.poo.api.dto.response.MovimientoInventarioResponseDTO;
import ues.ids.poo.api.enums.TipoMovimiento;
import ues.ids.poo.api.mapper.MovimientoInventarioMapper;
import ues.ids.poo.api.model.*;
import ues.ids.poo.api.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimientoInventarioServiceTest {

    @Mock private MovimientoInventarioRepository movimientoRepository;
    @Mock private ProductoRepository productoRepository;
    @Mock private SucursalRepository sucursalRepository;
    @Mock private StockSucursalRepository stockRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private MovimientoInventarioMapper movimientoMapper;

    @InjectMocks
    private MovimientoInventarioService movimientoService;

    @Test
    void testRegistrar_EntradaExitosa() {
        // Simulamos el Request
        MovimientoInventarioRequestDTO request = mock(MovimientoInventarioRequestDTO.class);
        when(request.getIdProducto()).thenReturn(10);
        when(request.getIdSucursal()).thenReturn(20);
        when(request.getIdUsuario()).thenReturn(30);
        when(request.getTipoMovimiento()).thenReturn(TipoMovimiento.ENTRADA);
        when(request.getCantidad()).thenReturn(5);

        // Simulamos las entidades
        Producto producto = new Producto();
        Sucursal sucursal = new Sucursal();
        Usuario usuario = new Usuario();
        StockSucursal stock = new StockSucursal();
        stock.setCantidadDisponible(10); // Stock actual

        when(productoRepository.findById(10)).thenReturn(Optional.of(producto));
        when(sucursalRepository.findById(20)).thenReturn(Optional.of(sucursal));
        when(usuarioRepository.findById(30)).thenReturn(Optional.of(usuario));
        when(stockRepository.buscarConBloqueo(10, 20)).thenReturn(Optional.of(stock));

        MovimientoInventario movimientoGuardado = new MovimientoInventario();
        MovimientoInventarioResponseDTO dtoEsperado = new MovimientoInventarioResponseDTO(
                1L, 10, 20, 30, TipoMovimiento.ENTRADA, 5, LocalDateTime.now()
        );

        when(movimientoRepository.save(any(MovimientoInventario.class))).thenReturn(movimientoGuardado);
        when(movimientoMapper.toResponseDTO(movimientoGuardado)).thenReturn(dtoEsperado);

        // Ejecutamos
        MovimientoInventarioResponseDTO resultado = movimientoService.registrar(request);

        // Verificamos
        assertNotNull(resultado);
        assertEquals(15, stock.getCantidadDisponible()); // 10 + 5 = 15
        assertEquals(TipoMovimiento.ENTRADA, resultado.tipoMovimiento());
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    void testRegistrar_SalidaConStockInsuficiente() {
        MovimientoInventarioRequestDTO request = mock(MovimientoInventarioRequestDTO.class);
        when(request.getIdProducto()).thenReturn(10);
        when(request.getIdSucursal()).thenReturn(20);
        when(request.getIdUsuario()).thenReturn(30);
        when(request.getTipoMovimiento()).thenReturn(TipoMovimiento.SALIDA);
        when(request.getCantidad()).thenReturn(15); // Queremos sacar 15

        StockSucursal stock = new StockSucursal();
        stock.setCantidadDisponible(10); // Pero solo hay 10

        when(productoRepository.findById(10)).thenReturn(Optional.of(new Producto()));
        when(sucursalRepository.findById(20)).thenReturn(Optional.of(new Sucursal()));
        when(usuarioRepository.findById(30)).thenReturn(Optional.of(new Usuario()));
        when(stockRepository.buscarConBloqueo(10, 20)).thenReturn(Optional.of(stock));

        // Ejecutamos y verificamos la excepción
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            movimientoService.registrar(request);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("La cantidad solicitada supera el stock disponible", exception.getReason());
    }

    @Test
    void testObtenerHistorial_Exito() {
        MovimientoInventario movimiento = new MovimientoInventario();
        MovimientoInventarioResponseDTO dto = new MovimientoInventarioResponseDTO(
                1L, 10, 20, 30, TipoMovimiento.ENTRADA, 5, LocalDateTime.now()
        );

        when(movimientoRepository.findAllByOrderByFechaRegistroDesc()).thenReturn(List.of(movimiento));
        when(movimientoMapper.toResponseDTO(movimiento)).thenReturn(dto);

        List<MovimientoInventarioResponseDTO> resultado = movimientoService.obtenerHistorial();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(movimientoRepository, times(1)).findAllByOrderByFechaRegistroDesc();
    }
}