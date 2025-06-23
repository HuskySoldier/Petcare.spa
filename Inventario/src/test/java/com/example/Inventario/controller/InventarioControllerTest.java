package com.example.Inventario.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Inventario.controller.InventarioController;
import com.example.Inventario.model.Inventario;
import com.example.Inventario.service.InventarioService;

@WebMvcTest(InventarioController.class)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    @Test
    void testListarInventario() throws Exception {
        Inventario i1 = new Inventario(null, "Producto 1", 10, 3, new Date());
        Inventario i2 = new Inventario(null, "Producto 2", 5, 2, new Date());
        List<Inventario> inventarios = List.of(i1, i2);

        when(inventarioService.allInventario(1L)).thenReturn(inventarios);

        mockMvc.perform(get("/api/v1/inventario/total")
                .header("X-USER-ID", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombreInv").value("Producto 1"))
            .andExpect(jsonPath("$[1].nombreInv").value("Producto 2"));
    }

    @Test
    void testObtenerInventario_Existe() throws Exception {
        Inventario i = new Inventario(1L, "Producto X", 12, 4, new Date());
        when(inventarioService.obtenerInventarioPorId(1L, 1L)).thenReturn(Optional.of(i));

        mockMvc.perform(get("/api/v1/inventario/1")
                .header("X-USER-ID", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombreInv").value("Producto X"));
    }

    @Test
    void testObtenerInventario_NoExiste() throws Exception {
        when(inventarioService.obtenerInventarioPorId(99L, 1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/inventario/99")
                .header("X-USER-ID", 1L))
            .andExpect(status().isNotFound());
    }
}