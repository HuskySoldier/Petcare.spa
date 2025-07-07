package com.example.HistorialMedico.controller;

import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.service.HistorialMedicoService;
import com.example.HistorialMedico.service.TratamientoService;
import com.example.HistorialMedico.repository.HistorialMedicoRepository;
import com.example.HistorialMedico.repository.TratamientoRepository;
import com.example.HistorialMedico.client.MascotaClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistorialMedicoController.class)
public class HistorialMedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HistorialMedicoService historialMedicoService;

    @MockBean
    private TratamientoService tratamientoService;

    @MockBean
    private HistorialMedicoRepository historialRepo;

    @MockBean
    private TratamientoRepository tratamientoRepo;

    @MockBean
    private MascotaClient mascotaClient;

    @Test
    void testCrearHistorialMedico() throws Exception {
        HistorialMedico historial = new HistorialMedico();
        historial.setFechaRegistro(LocalDate.now());
        historial.setAntecedentes("Antecedente test");
        historial.setComentario("Comentario test");
        historial.setDiagnostico("Diagnóstico test");
        historial.setIdMascota(1L);

        when(historialMedicoService.agregarHistorialMedico(any(HistorialMedico.class), eq(null))).thenReturn(historial);

        mockMvc.perform(post("/api/v1/historialmedico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historial)))
                .andExpect(status().isCreated());
    }

    @Test
    void testListarHistorialVacio() throws Exception {
        when(historialMedicoService.listarHistorialMedico()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/historialmedico"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testListarHistorialConDatos() throws Exception {
        HistorialMedico historial = new HistorialMedico();
        historial.setIdMascota(1L);
        historial.setComentario("Comentario");
        List<HistorialMedico> lista = List.of(historial);

        when(historialMedicoService.listarHistorialMedico()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/historialmedico"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Comentario"));
    }

    @Test
    void testCrearHistorialMedicoUsuarioNoEncontrado() throws Exception {
        HistorialMedico historial = new HistorialMedico();
        historial.setIdMascota(1L);

        when(historialMedicoService.agregarHistorialMedico(any(HistorialMedico.class), eq(123L)))
                .thenThrow(FeignException.NotFound.class);

        mockMvc.perform(post("/api/v1/historialmedico")
                .header("X-USER-ID", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historial)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario con ID 123 no encontrado."));
    }

    @Test
    void testCrearHistorialMedicoErrorRuntime() throws Exception {
        HistorialMedico historial = new HistorialMedico();
        historial.setIdMascota(1L);

        when(historialMedicoService.agregarHistorialMedico(any(HistorialMedico.class), eq(123L)))
                .thenThrow(new RuntimeException("Error de permiso"));

        mockMvc.perform(post("/api/v1/historialmedico")
                .header("X-USER-ID", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historial)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Error de permiso"));
    }

    @Test
    void testEliminarHistorialMedico() throws Exception {
        doNothing().when(historialMedicoService).eliminarHistorialMedico(1L);

        mockMvc.perform(delete("/api/v1/historialmedico/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarHistorialMedicoNoEncontrado() throws Exception {
        doThrow(new RuntimeException("No encontrado")).when(historialMedicoService).eliminarHistorialMedico(1L);

        mockMvc.perform(delete("/api/v1/historialmedico/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No encontrado"));
    }

    @Test
    void testModificarHistorialMedicoExitoso() throws Exception {
        HistorialMedico actualizado = new HistorialMedico();
        actualizado.setIdMascota(1L);
        actualizado.setComentario("Modificado");
        // Asegúrate de poblar todos los campos requeridos por validaciones
        actualizado.setFechaRegistro(LocalDate.now());
        actualizado.setAntecedentes("Antecedentes test");
        actualizado.setDiagnostico("Diagnóstico test");

        when(historialMedicoService.modificarHistorialMedico(eq(1L), any(HistorialMedico.class), eq(123L)))
                .thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/historialmedico/1")
                .header("X-USER-ID", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comentario").value("Modificado"));
    }

    @Test
    void testModificarHistorialMedicoUsuarioNoEncontrado() throws Exception {
        HistorialMedico historial = new HistorialMedico();

        when(historialMedicoService.modificarHistorialMedico(eq(1L), any(HistorialMedico.class), eq(123L)))
                .thenThrow(FeignException.NotFound.class);

        mockMvc.perform(put("/api/v1/historialmedico/1")
                .header("X-USER-ID", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historial)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario con ID 123 no encontrado."));
    }

    @Test
    void testModificarHistorialMedicoNoEncontrado() throws Exception {
        HistorialMedico historial = new HistorialMedico();

        when(historialMedicoService.modificarHistorialMedico(eq(1L), any(HistorialMedico.class), eq(123L)))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/api/v1/historialmedico/1")
                .header("X-USER-ID", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historial)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testModificarHistorialMedicoErrorRuntime() throws Exception {
        HistorialMedico historial = new HistorialMedico();

        when(historialMedicoService.modificarHistorialMedico(eq(1L), any(HistorialMedico.class), eq(123L)))
                .thenThrow(new RuntimeException("Error de permiso"));

        mockMvc.perform(put("/api/v1/historialmedico/1")
                .header("X-USER-ID", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historial)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Error de permiso"));
    }

    @Test
    void testObtenerHistorialPorMascotaVacio() throws Exception {
        when(historialMedicoService.buscarPorIdMascota(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/historialmedico/mascota/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testObtenerHistorialPorMascotaConDatos() throws Exception {
        HistorialMedico historial = new HistorialMedico();
        historial.setComentario("Comentario mascota");

        when(historialMedicoService.buscarPorIdMascota(1L)).thenReturn(List.of(historial));

        mockMvc.perform(get("/api/v1/historialmedico/mascota/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Comentario mascota"));
    }
}
