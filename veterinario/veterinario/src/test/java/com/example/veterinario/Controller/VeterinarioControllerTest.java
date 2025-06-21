package com.example.veterinario.Controller;


import com.example.veterinario.controller.VeterinarioController;
import com.example.veterinario.model.Veterinario;
import com.example.veterinario.service.VeterinarioService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VeterinarioController.class)
class VeterinarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeterinarioService veterinarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarVeterinarios_cuandoHayDatos_deberiaRetornarOk() throws Exception {
        Veterinario v1 = new Veterinario(1L, 12345678, "Juan", "Perez", "Cardiologia", "juan@mail.com", 101L);
        Veterinario v2 = new Veterinario(2L, 87654321, "Ana", "Gomez", "Dermatologia", "ana@mail.com", 102L);

        when(veterinarioService.listarVeterinarios()).thenReturn(List.of(v1, v2));

        mockMvc.perform(get("/api/v1/veterinario/Total"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].nombre").value("Juan"))
            .andExpect(jsonPath("$[1].correo").value("ana@mail.com"));
    }

    @Test
    void obtenerVeterinarioPorId_cuandoExiste_deberiaRetornarOk() throws Exception {
        Veterinario v = new Veterinario(1L, 12345678, "Juan", "Perez", "Cardiologia", "juan@mail.com", 101L);

        when(veterinarioService.buscarVeterinarioPorId(1L)).thenReturn(v);

        mockMvc.perform(get("/api/v1/veterinario/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan"))
            .andExpect(jsonPath("$.apellido").value("Perez"));
    }

    @Test
    void agregarVeterinario_cuandoDatosValidos_deberiaRetornarCreated() throws Exception {
        Veterinario v = new Veterinario(null, 87654321, "Ana", "Gomez", "Dermatologia", "ana@mail.com", 102L);
        Veterinario vGuardado = new Veterinario(2L, 87654321, "Ana", "Gomez", "Dermatologia", "ana@mail.com", 102L);

        when(veterinarioService.agregarVeterinario(any(Veterinario.class))).thenReturn(vGuardado);

        mockMvc.perform(post("/api/v1/veterinario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(v)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.veterinarioId").value(2))
            .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    void deleteVeterinarioPorId_cuandoExito_deberiaRetornarNoContent() throws Exception {
        doNothing().when(veterinarioService).eliminarVeterinario(1L);

        mockMvc.perform(delete("/api/v1/veterinario/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void modificarVeterinario_cuandoExito_deberiaRetornarOk() throws Exception {
        Veterinario vetOriginal = new Veterinario(1L, 12345678, "Juan", "Perez", "Cardiologia", "juan@mail.com", 101L);
        Veterinario vetModificado = new Veterinario(1L, 12345678, "Juan Carlos", "Perez", "Cardiologia", "juan@mail.com", 101L);

        when(veterinarioService.buscarVeterinarioPorId(1L)).thenReturn(vetOriginal);
        when(veterinarioService.agregarVeterinario(any(Veterinario.class))).thenReturn(vetModificado);

        mockMvc.perform(put("/api/v1/veterinario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vetModificado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan Carlos"));
    }
}
