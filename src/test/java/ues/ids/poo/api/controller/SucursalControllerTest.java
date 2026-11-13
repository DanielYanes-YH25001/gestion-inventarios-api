package ues.ids.poo.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.SucursalResponseDTO;
import ues.ids.poo.api.exception.GlobalExceptionHandler;
import ues.ids.poo.api.service.SucursalService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SucursalController.class)
@Import(GlobalExceptionHandler.class)
class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SucursalService sucursalService;

    @Test
    void testGetSucursal_Exito() throws Exception {
        Integer id = 1;

        SucursalResponseDTO dto = new SucursalResponseDTO(
                id,
                "Sucursal Centro",
                "San Salvador",
                true
        );

        when(sucursalService.obtenerPorId(id)).thenReturn(dto);

        mockMvc.perform(get("/api/sucursales/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sucursal Centro"));
    }

    @Test
    void testGetSucursal_NoEncontrado() throws Exception {
        Integer id = 99;

        when(sucursalService.obtenerPorId(id))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la sucursal"));

        mockMvc.perform(get("/api/sucursales/{id}", id))
                .andExpect(status().isNotFound());
    }
}