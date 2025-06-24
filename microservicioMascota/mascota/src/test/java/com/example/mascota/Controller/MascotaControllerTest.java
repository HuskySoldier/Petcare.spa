package com.example.mascota.Controller;

import com.example.mascota.controller.MascotaController;
import com.example.mascota.model.Mascota;
import com.example.mascota.service.MascotaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MascotaController.class)
class MascotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MascotaService mascotaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarTodas_deberiaRetornarListaMascotas() throws Exception {
        Mascota m1 = new Mascota();
        m1.setIdMascota(1L);
        m1.setIdUsuario(10L);

        Mascota m2 = new Mascota();
        m2.setIdMascota(2L);
        m2.setIdUsuario(11L);

        when(mascotaService.listarMacotas()).thenReturn(List.of(m1, m2));

        mockMvc.perform(get("/api/v1/mascota"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void obtenerPorId_cuandoExiste_deberiaRetornarOk() throws Exception {
        Mascota m = new Mascota();
        m.setIdMascota(1L);
        m.setIdUsuario(10L);

        when(mascotaService.buscarMascotaPorId(1L)).thenReturn(m);

        mockMvc.perform(get("/api/v1/mascota/1").header("X-USER-ID", 10L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idMascota").value(1));
    }

    @Test
    void obtenerPorId_cuandoNoExiste_deberiaRetornarNotFound() throws Exception {
        when(mascotaService.buscarMascotaPorId(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/mascota/1").header("X-USER-ID", 10L))
            .andExpect(status().isNotFound());
    }

    @Test
    void obtenerPorId_cuandoLanzaExcepcion_deberiaRetornarForbidden() throws Exception {
        when(mascotaService.buscarMascotaPorId(1L)).thenThrow(new RuntimeException("Acceso denegado"));

        mockMvc.perform(get("/api/v1/mascota/1").header("X-USER-ID", 10L))
            .andExpect(status().isForbidden());
    }

    @Test
    void crearMascota_cuandoDatosValidos_deberiaRetornarOk() throws Exception {
        Mascota m = new Mascota();
        m.setIdUsuario(10L);

        Mascota mGuardada = new Mascota();
        mGuardada.setIdMascota(1L);
        mGuardada.setIdUsuario(10L);

        when(mascotaService.agregarMascota(any(Mascota.class))).thenReturn(mGuardada);

        mockMvc.perform(post("/api/v1/mascota")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(m)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idMascota").value(1));
    }

    @Test
    void crearMascota_cuandoIllegalArgumentException_deberiaRetornarBadRequest() throws Exception {
        Mascota m = new Mascota();
        when(mascotaService.agregarMascota(any(Mascota.class))).thenThrow(new IllegalArgumentException("Datos inválidos"));

        mockMvc.perform(post("/api/v1/mascota")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(m)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void crearMascota_cuandoRuntimeException_deberiaRetornarInternalServerError() throws Exception {
        Mascota m = new Mascota();
        when(mascotaService.agregarMascota(any(Mascota.class))).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(post("/api/v1/mascota")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(m)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void eliminar_cuandoExito_deberiaRetornarNoContent() throws Exception {
        doNothing().when(mascotaService).eliminarMascota(1L);

        mockMvc.perform(delete("/api/v1/mascota/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_cuandoLanzaExcepcion_deberiaRetornarNotFound() throws Exception {
        doThrow(new RuntimeException("No existe")).when(mascotaService).eliminarMascota(1L);

        mockMvc.perform(delete("/api/v1/mascota/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void listarPorUsuario_cuandoHayMascotas_deberiaRetornarOk() throws Exception {
        Mascota m1 = new Mascota();
        m1.setIdMascota(1L);
        m1.setIdUsuario(10L);

        when(mascotaService.obtenerPorIdUsuario(10L)).thenReturn(List.of(m1));

        mockMvc.perform(get("/api/v1/mascota/usuario/10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void listarPorUsuario_cuandoNoHayMascotas_deberiaRetornarNoContent() throws Exception {
        when(mascotaService.obtenerPorIdUsuario(10L)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/mascota/usuario/10"))
            .andExpect(status().isNoContent());
    }
}
