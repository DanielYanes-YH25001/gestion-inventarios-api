package ues.ids.poo.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.StockSucursalResponseDTO;
import ues.ids.poo.api.exception.GlobalExceptionHandler;
import ues.ids.poo.api.service.StockSucursalService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockSucursalController.class)
@Import(GlobalExceptionHandler.class)
class StockSucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StockSucursalService stockSucursalService;

    @Test
    void testGetStock_Exito() throws Exception {
        Integer idSucursal = 1;

        StockSucursalResponseDTO dto = new StockSucursalResponseDTO(10, "Laptop HP", 50);

        when(stockSucursalService.obtenerStock(idSucursal)).thenReturn(List.of(dto));

        // Ruta exacta de tu @RequestMapping y @GetMapping
        mockMvc.perform(get("/api/stocksucursales/{id}", idSucursal))
                .andExpect(status().isOk())
                // Al ser una lista, apuntamos al primer elemento con $[0]
                .andExpect(jsonPath("$[0].nombreProducto").value("Laptop HP"))
                .andExpect(jsonPath("$[0].cantidadDisponible").value(50));
    }

    @Test
    void testGetStock_NoEncontrado() throws Exception {
        Integer idSucursal = 99;

        when(stockSucursalService.obtenerStock(idSucursal))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Sucursal no encontrada"));

        mockMvc.perform(get("/api/stocksucursales/{id}", idSucursal))
                .andExpect(status().isNotFound());
    }
}