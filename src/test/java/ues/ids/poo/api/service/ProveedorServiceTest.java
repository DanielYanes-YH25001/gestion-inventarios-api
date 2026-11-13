package ues.ids.poo.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.ProveedorResponseDTO;
import ues.ids.poo.api.mapper.ProveedorMapper;
import ues.ids.poo.api.model.Proveedor;
import ues.ids.poo.api.repository.ProveedorRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private ProveedorMapper proveedorMapper;

    @InjectMocks
    private ProveedorService proveedorService;

    @Test
    void testObtenerPorId_Exito() {
        Integer id = 1;

        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(id);
        proveedor.setNombre("Distribuidora Central");
        proveedor.setContacto("Juan Perez");
        proveedor.setTelefono("7777-8888");
        proveedor.setDireccion("San Salvador");

        ProveedorResponseDTO dtoEsperado = new ProveedorResponseDTO(
                id,
                "Distribuidora Central",
                "Juan Perez",
                "7777-8888",
                "San Salvador"
        );

        when(proveedorRepository.findById(id)).thenReturn(Optional.of(proveedor));
        when(proveedorMapper.toResponseDTO(proveedor)).thenReturn(dtoEsperado);

        ProveedorResponseDTO resultado = proveedorService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("Distribuidora Central", resultado.nombre());
        verify(proveedorRepository, times(1)).findById(id);
        verify(proveedorMapper, times(1)).toResponseDTO(proveedor);
    }

    @Test
    void testObtenerPorId_Error() {
        Integer id = 99;

        when(proveedorRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            proveedorService.obtenerPorId(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}