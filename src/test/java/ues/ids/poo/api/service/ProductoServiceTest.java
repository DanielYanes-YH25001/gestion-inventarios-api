package ues.ids.poo.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.ProductoResponseDTO;
import ues.ids.poo.api.model.Categoria;
import ues.ids.poo.api.model.Producto;
import ues.ids.poo.api.model.Proveedor;
import ues.ids.poo.api.repository.ProductoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void testObtenerPorId_Exito() {
        Integer id = 1;

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(20);

        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(10);

        Producto producto = new Producto();
        producto.setIdProducto(id);
        producto.setNombre("Laptop HP");
        producto.setCategoria(categoria);
        producto.setProveedor(proveedor);

        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        ProductoResponseDTO resultado = productoService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("Laptop HP", resultado.nombre());
        assertEquals(10, resultado.idProveedor());
        assertEquals(20, resultado.idCategoria());

        verify(productoRepository, times(1)).findById(id);
    }

    @Test
    void testObtenerPorId_Error() {
        Integer id = 99;

        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            productoService.obtenerPorId(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}