package com.example.Reportes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;
import java.util.List;

import com.example.Reportes.model.Reporte;
import com.example.Reportes.service.ReporteService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReporteController.class)
class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService reporteService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Long idUsuarioMock = 1L;

    @Test
    void testListarReportes() throws Exception {
        Reporte r1 = new Reporte(1L, "Comentario 1", new Date());
        Reporte r2 = new Reporte(2L, "Comentario 2", new Date());
        List<Reporte> reportes = List.of(r1, r2);

        when(reporteService.allReporte(eq(idUsuarioMock))).thenReturn(reportes);

        mockMvc.perform(get("/api/v1/reporte/total")
                .header("X-USER-ID", idUsuarioMock))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].comentario").value("Comentario 1"))
            .andExpect(jsonPath("$[1].comentario").value("Comentario 2"));
    }

    @Test
    void testListarReportes_Vacio() throws Exception {
        when(reporteService.allReporte(eq(idUsuarioMock))).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/reporte/total")
                .header("X-USER-ID", idUsuarioMock))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCrearReporte() throws Exception {
        Reporte nuevo = new Reporte(null, "Nuevo Reporte", new Date());
        Reporte guardado = new Reporte(1L, "Nuevo Reporte", nuevo.getFechaCreacion());

        when(reporteService.crearReporte(eq(nuevo), eq(idUsuarioMock))).thenReturn(guardado);

        mockMvc.perform(post("/api/v1/reporte")
                .header("X-USER-ID", idUsuarioMock)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.comentario").value("Nuevo Reporte"));
    }

    @Test
    void testCrearReporte_Error() throws Exception {
        Reporte invalido = new Reporte(null, null, new Date());

        when(reporteService.crearReporte(eq(invalido), eq(idUsuarioMock)))
            .thenThrow(new RuntimeException("Acceso denegado"));

        mockMvc.perform(post("/api/v1/reporte")
                .header("X-USER-ID", idUsuarioMock)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalido)))
            .andExpect(status().isForbidden())
            .andExpect(content().string("Acceso denegado"));
    }
}
