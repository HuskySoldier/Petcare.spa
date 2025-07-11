package com.example.Reserva.Controller;

import com.example.Reserva.client.VeterinarioClient;
import com.example.Reserva.controller.ReservaController;
import com.example.Reserva.dto.VeterinarioDTO;
import com.example.Reserva.model.Estado;
import com.example.Reserva.model.Reserva;
import com.example.Reserva.security.FiltroSimple;
import com.example.Reserva.service.EstadoService;
import com.example.Reserva.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactiva todos los filtros globales
class ReservaControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ReservaService reservaService;

        @MockBean
        private EstadoService estadoService;

        @MockBean
        private VeterinarioClient veterinarioClient;

        @MockBean
        private FiltroSimple filtroSimple; // Mockea el filtro para que no bloquee

        @Autowired
        private ObjectMapper objectMapper;

        private static final String USER_HEADER = "X-USER-ID";

        @Test
        void listarReservas_deberiaRetornarLista() throws Exception {
                Reserva r1 = new Reserva(1L, Date.valueOf("2025-06-01"), Date.valueOf("2025-06-05"), 5L, 1L, 15000,
                                null);
                Reserva r2 = new Reserva(2L, Date.valueOf("2025-06-10"), Date.valueOf("2025-06-15"), 6L, 2L, 20000,
                                null);

                when(reservaService.listarReservas()).thenReturn(List.of(r1, r2));

                mockMvc.perform(get("/api/v1/reserva/Total")
                                .header(USER_HEADER, "3"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2));
        }

        @Test
        void buscarReservaPorId_existente_deberiaRetornarReserva() throws Exception {
                Estado estado = new Estado(1L, "Confirmada", "Reserva confirmada", null);
                Reserva r = new Reserva(1L, Date.valueOf("2025-06-01"), Date.valueOf("2025-06-05"), 5L, 1L, 15000,
                                estado);

                when(reservaService.buscarReservaPorId(1L)).thenReturn(r);

                mockMvc.perform(get("/api/v1/reserva/1")
                                .header(USER_HEADER, "3"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.reservaId").value(1));
        }

        @Test
        void buscarReservaPorId_noExistente_deberiaRetornarNotFound() throws Exception {
                when(reservaService.buscarReservaPorId(99L)).thenReturn(null);

                mockMvc.perform(get("/api/v1/reserva/99")
                                .header(USER_HEADER, "3"))
                                .andExpect(status().isNotFound());
        }

        @Test
        void crearReserva_valida_deberiaRetornarOk() throws Exception {
                Estado estado = new Estado(1L, "Pendiente", "En espera", null);
                Reserva nueva = new Reserva(null, Date.valueOf("2025-06-01"), Date.valueOf("2025-06-05"), 5L, 1L, 15000,
                                estado);
                Reserva guardada = new Reserva(1L, nueva.getFechaCreacion(), nueva.getFechareserva(), 5L, 1L, 15000,
                                estado);

                when(reservaService.crearReserva(any(Reserva.class), any(Long.class))).thenReturn(guardada);

                mockMvc.perform(post("/api/v1/reserva")
                                .header(USER_HEADER, "3")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(nueva)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.reservaId").value(1));
        }

        @Test
        void crearReserva_invalida_deberiaRetornarBadRequest() throws Exception {
                when(reservaService.crearReserva(any(Reserva.class), any(Long.class)))
                                .thenThrow(new RuntimeException("Error interno"));

                mockMvc.perform(post("/api/v1/reserva")
                                .header(USER_HEADER, "3")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new Reserva())))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void crearEstado_deberiaRetornarEstado() throws Exception {
                Estado estado = new Estado(null, "Pendiente", "Esperando", null);
                Estado estadoCreado = new Estado(1L, "Pendiente", "Esperando", null);

                when(estadoService.crearEstado(any(Estado.class))).thenReturn(estadoCreado);

                mockMvc.perform(post("/api/v1/reserva/estado")
                                .header(USER_HEADER, "3")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(estado)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.nombreEstado").value("Pendiente"));
        }

        @Test
        void listarEstados_deberiaRetornarLista() throws Exception {
                Estado e1 = new Estado(1L, "Pendiente", "Esperando", null);
                Estado e2 = new Estado(2L, "Aprobada", "Confirmada", null);

                when(estadoService.listarEstados()).thenReturn(List.of(e1, e2));

                mockMvc.perform(get("/api/v1/reserva/estado")
                                .header(USER_HEADER, "3"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2));
        }

        @Test
        void obtenerReservasPorUsuario_deberiaRetornarLista() throws Exception {
                Reserva r = new Reserva(1L, Date.valueOf("2025-06-01"), Date.valueOf("2025-06-05"), 5L, 1L, 15000,
                                null);

                when(reservaService.listarReservasPorUsuarioId(1L)).thenReturn(List.of(r));

                mockMvc.perform(get("/api/v1/reserva/usuario/1")
                                .header(USER_HEADER, "3"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));
        }

        @Test
        void obtenerVeterinarios_deberiaRetornarLista() throws Exception {
                VeterinarioDTO v1 = new VeterinarioDTO();
                v1.setVeterinarioId(1L);
                v1.setNombre("Carlos");
                v1.setApellido("Pérez");
                v1.setEspecialidad("Cirugía");
                v1.setCorreo("carlos.vet@petcare.com");

                VeterinarioDTO v2 = new VeterinarioDTO();
                v2.setVeterinarioId(2L);
                v2.setNombre("Ana");
                v2.setApellido("Soto");
                v2.setEspecialidad("Medicina Interna");
                v2.setCorreo("ana.vet@petcare.com");

                // MOCKEAR EL SERVICIO, NO EL CLIENTE
                when(reservaService.obtenerTodosLosVeterinarios(any(Long.class))).thenReturn(List.of(v1, v2));

                mockMvc.perform(get("/api/v1/reserva/veterinarios")
                                .header(USER_HEADER, "3"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2));
        }

        @Test
        void eliminarReserva_existente_deberiaRetornarNoContent() throws Exception {
                doNothing().when(reservaService).eliminarReserva(1L);

                mockMvc.perform(delete("/api/v1/reserva/1")
                                .header(USER_HEADER, "3"))
                                .andExpect(status().isNoContent());

                verify(reservaService, times(1)).eliminarReserva(1L);
        }
}
