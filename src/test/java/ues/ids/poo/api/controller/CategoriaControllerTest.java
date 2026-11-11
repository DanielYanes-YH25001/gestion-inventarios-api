package ues.ids.poo.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.CategoriaResponseDTO;
import ues.ids.poo.api.exception.GlobalExceptionHandler;
import ues.ids.poo.api.service.CategoriaService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
@Import(GlobalExceptionHandler.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    @Test
    void testGetCategoria_Exito() throws Exception {
        Integer id = 1;


        // Si tu record tiene otro orden, ajústalo aquí (ej. nombre primero).
        CategoriaResponseDTO catfalsa = new CategoriaResponseDTO(id, "Electrónica", "Productos electrónicos");

        when(categoriaService.obtenerPorId(id)).thenReturn(catfalsa);

        // ⚠️ Cambia "/api/categorias" si tu controlador usa otra ruta
        mockMvc.perform(get("/api/categorias/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Electrónica"));
    }

    @Test
    void testGetCategoria_NoEncontrado() throws Exception {
        Integer id = 99;

        // Aquí no creamos DTO, solo lanzamos la excepción
        when(categoriaService.obtenerPorId(id))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la categoría"));

        mockMvc.perform(get("/api/categorias/{id}", id))
                .andExpect(status().isNotFound());
    }
}