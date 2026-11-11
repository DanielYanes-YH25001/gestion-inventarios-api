package ues.ids.poo.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.MovimientoInventarioResponseDTO;
import ues.ids.poo.api.enums.TipoMovimiento;
import ues.ids.poo.api.exception.GlobalExceptionHandler;
import ues.ids.poo.api.service.MovimientoInventarioService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovimientoInventarioController.class)
@Import(GlobalExceptionHandler.class)
class MovimientoInventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovimientoInventarioService movimientoService;

    @Test
    void testGetHistorial_Exito() throws Exception {
        MovimientoInventarioResponseDTO dto = new MovimientoInventarioResponseDTO(
                1L, 10, 20, 30, TipoMovimiento.ENTRADA, 5, LocalDateTime.now()
        );

        when(movimientoService.obtenerHistorial()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/movimientos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idMovimientoInventario").value(1L))
                .andExpect(jsonPath("$[0].tipoMovimiento").value("ENTRADA"));
    }

    @Test
    void testGetHistorial_Error() throws Exception {
        when(movimientoService.obtenerHistorial())
                .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener historial"));

        mockMvc.perform(get("/api/movimientos"))
                .andExpect(status().is5xxServerError());
    }
}