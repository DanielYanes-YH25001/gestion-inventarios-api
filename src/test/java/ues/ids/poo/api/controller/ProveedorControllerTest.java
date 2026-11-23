package ues.ids.poo.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.ProveedorResponseDTO;
import ues.ids.poo.api.exception.GlobalExceptionHandler;
import ues.ids.poo.api.service.ProveedorService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProveedorController.class)
@Import(GlobalExceptionHandler.class)
class ProveedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProveedorService proveedorService;

    @Test
    void testGetProveedor_Exito() throws Exception {
        Integer id = 1;

        ProveedorResponseDTO dto = new ProveedorResponseDTO(
                id,
                "Distribuidora Central",
                "Juan Perez",
                "7777-8888",
                "San Salvador"
        );

        when(proveedorService.obtenerPorId(id)).thenReturn(dto);

        mockMvc.perform(get("/api/proveedores/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Distribuidora Central"));
    }

    @Test
    void testGetProveedor_NoEncontrado() throws Exception {
        Integer id = 99;

        when(proveedorService.obtenerPorId(id))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el proveedor"));

        mockMvc.perform(get("/api/proveedores/{id}", id))
                .andExpect(status().isNotFound());
    }
}