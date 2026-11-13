package ues.ids.poo.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.CategoriaResponseDTO;
import ues.ids.poo.api.mapper.CategoriaMapper;
import ues.ids.poo.api.model.Categoria;
import ues.ids.poo.api.repository.CategoriaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void testObtenerPorId_Exito() {
        Integer id = 1;

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(id);
        categoria.setNombre("Electrónica");
        categoria.setDescripcion("Productos electrónicos");

        CategoriaResponseDTO dtoEsperado = new CategoriaResponseDTO(
                id,
                "Electrónica",
                "Productos electrónicos"
        );

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toResponseDTO(categoria)).thenReturn(dtoEsperado);

        CategoriaResponseDTO resultado = categoriaService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("Electrónica", resultado.nombre());
        verify(categoriaRepository, times(1)).findById(id);
        verify(categoriaMapper, times(1)).toResponseDTO(categoria);
    }

    @Test
    void testObtenerPorId_Error() {
        Integer id = 99;

        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            categoriaService.obtenerPorId(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}