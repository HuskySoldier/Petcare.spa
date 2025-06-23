package com.example.resenas.controller;


import com.example.resenas.ReseniasApplication;
import com.example.resenas.Dto.ReseniaResponseDto;
import com.example.resenas.controller.ReseniaController;
import com.example.resenas.model.Resenia;
import com.example.resenas.service.ReseniaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReseniaController.class)
@ContextConfiguration(classes = ReseniasApplication.class)
class ReseniaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    

    @MockBean
    private ReseniaService reseniaService;

    @Test
    void testObtenerResenia_Existe() throws Exception {
        Resenia r = new Resenia(1L, "Excelente", 9, 10L, 20L);
        when(reseniaService.obtenerReseniaPorId(1L)).thenReturn(Optional.of(r));
        when(reseniaService.construirRespuestaConServicio(r)).thenReturn(new ReseniaResponseDto());

        mockMvc.perform(get("/api/v1/resena/1"))
            .andExpect(status().isOk());
    }

    @Test
    void testObtenerResenia_NoExiste() throws Exception {
        when(reseniaService.obtenerReseniaPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/resena/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testListarResenias() throws Exception {
        List<Resenia> lista = List.of(
            new Resenia(1L, "Genial", 10, 1L, 1L),
            new Resenia(2L, "Buena", 8, 2L, 2L)
        );

        when(reseniaService.allResenia()).thenReturn(lista);
        when(reseniaService.construirRespuestaConServicio(any()))
            .thenReturn(new ReseniaResponseDto(), new ReseniaResponseDto());

        mockMvc.perform(get("/api/v1/resena/total"))
            .andExpect(status().isOk());
    }
}