package ues.ids.poo.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.ProductoResponseDTO;
import ues.ids.poo.api.exception.GlobalExceptionHandler;
import ues.ids.poo.api.service.ProductoService;

import java.math.BigDecimal; // <-- Importante para los precios

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductoController.class)
@Import(GlobalExceptionHandler.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @Test
    void testGetProducto_Exito() throws Exception {
        Integer id = 1;

        ProductoResponseDTO productoFalso = new ProductoResponseDTO(
                id,
                "Laptop HP",
                "Laptop de alta gama",
                new BigDecimal("1000.00"), // precioBase
                new BigDecimal("1500.00"), // precioVenta
                10, // idProveedor
                20  // idCategoria
        );

        when(productoService.obtenerPorId(id)).thenReturn(productoFalso);

        mockMvc.perform(get("/api/productos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Laptop HP"));
    }

    @Test
    void testGetProducto_NoEncontrado() throws Exception {
        Integer id = 99;

        when(productoService.obtenerPorId(id))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el producto"));

        mockMvc.perform(get("/api/productos/{id}", id))
                .andExpect(status().isNotFound());
    }
}