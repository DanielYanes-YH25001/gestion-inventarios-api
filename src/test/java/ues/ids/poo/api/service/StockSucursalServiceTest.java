package ues.ids.poo.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.StockSucursalResponseDTO;
import ues.ids.poo.api.mapper.StockSucursalMapper;
import ues.ids.poo.api.model.Producto;
import ues.ids.poo.api.model.StockSucursal;
import ues.ids.poo.api.model.Sucursal;
import ues.ids.poo.api.repository.StockSucursalRepository;
import ues.ids.poo.api.repository.SucursalRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockSucursalServiceTest {

    @Mock
    private StockSucursalRepository stockSucursalRepository;

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private StockSucursalMapper stockSucursalMapper;

    @InjectMocks
    private StockSucursalService stockSucursalService;

    @Test
    void testObtenerStock_Exito() {
        Integer idSucursal = 1;

        Producto producto = new Producto();
        producto.setIdProducto(10);
        producto.setNombre("Laptop HP");

        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(idSucursal);

        StockSucursal stock = new StockSucursal();
        stock.setProducto(producto);
        stock.setSucursal(sucursal);
        stock.setCantidadDisponible(50);

        StockSucursalResponseDTO dtoEsperado = new StockSucursalResponseDTO(10, "Laptop HP", 50);

        when(sucursalRepository.existsById(idSucursal)).thenReturn(true);
        when(stockSucursalRepository.findBySucursal_IdSucursal(idSucursal)).thenReturn(List.of(stock));
        when(stockSucursalMapper.toStockResponse(stock)).thenReturn(dtoEsperado);

        List<StockSucursalResponseDTO> resultado = stockSucursalService.obtenerStock(idSucursal);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Laptop HP", resultado.get(0).nombreProducto());
        assertEquals(50, resultado.get(0).cantidadDisponible());

        verify(sucursalRepository, times(1)).existsById(idSucursal);
        verify(stockSucursalRepository, times(1)).findBySucursal_IdSucursal(idSucursal);
    }

    @Test
    void testObtenerStock_ErrorSucursalNoEncontrada() {
        Integer idSucursal = 99;

        when(sucursalRepository.existsById(idSucursal)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            stockSucursalService.obtenerStock(idSucursal);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Sucursal no encontrada", exception.getReason());

        verify(stockSucursalRepository, never()).findBySucursal_IdSucursal(anyInt());
    }

    @Test
    void testObtenerTodoElStock_Exito() {
        Producto producto = new Producto();
        producto.setNombre("Laptop HP");

        StockSucursal stock = new StockSucursal();
        stock.setProducto(producto);
        stock.setCantidadDisponible(50);

        StockSucursalResponseDTO dtoEsperado = new StockSucursalResponseDTO(10, "Laptop HP", 50);

        when(stockSucursalRepository.findAll()).thenReturn(List.of(stock));
        when(stockSucursalMapper.toStockResponse(stock)).thenReturn(dtoEsperado);

        List<StockSucursalResponseDTO> resultado = stockSucursalService.obtenerTodoElStock();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Laptop HP", resultado.get(0).nombreProducto());

        verify(stockSucursalRepository, times(1)).findAll();
    }
}