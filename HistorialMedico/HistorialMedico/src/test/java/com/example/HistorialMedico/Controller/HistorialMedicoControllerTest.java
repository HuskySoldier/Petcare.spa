package com.example.HistorialMedico.Controller;


import com.example.HistorialMedico.controller.HistorialMedicoController;
import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.service.HistorialMedicoService;
import com.example.HistorialMedico.service.TratamientoService;
import com.example.HistorialMedico.repository.HistorialMedicoRepository;
import com.example.HistorialMedico.repository.TratamientoRepository;
import com.example.HistorialMedico.client.MascotaClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
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

        when(historialMedicoService.agregarHistorialMedico(any(HistorialMedico.class))).thenReturn(historial);

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
}
