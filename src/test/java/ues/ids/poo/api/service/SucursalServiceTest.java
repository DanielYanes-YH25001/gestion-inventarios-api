package ues.ids.poo.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.SucursalResponseDTO;
import ues.ids.poo.api.mapper.SucursalMapper;
import ues.ids.poo.api.model.Sucursal;
import ues.ids.poo.api.repository.SucursalRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private SucursalMapper sucursalMapper;

    @InjectMocks
    private SucursalService sucursalService;

    @Test
    void testObtenerPorId_Exito() {
        Integer id = 1;

        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(id);
        sucursal.setNombre("Sucursal Centro");
        sucursal.setDireccion("San Salvador");
        sucursal.setActiva(true);

        SucursalResponseDTO dtoEsperado = new SucursalResponseDTO(
                id,
                "Sucursal Centro",
                "San Salvador",
                true
        );

        when(sucursalRepository.findById(id)).thenReturn(Optional.of(sucursal));
        when(sucursalMapper.toResponseDTO(sucursal)).thenReturn(dtoEsperado);

        SucursalResponseDTO resultado = sucursalService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("Sucursal Centro", resultado.nombre());
        assertTrue(resultado.activa());

        verify(sucursalRepository, times(1)).findById(id);
        verify(sucursalMapper, times(1)).toResponseDTO(sucursal);
    }

    @Test
    void testObtenerPorId_Error() {
        Integer id = 99;

        when(sucursalRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            sucursalService.obtenerPorId(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}