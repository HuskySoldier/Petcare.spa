package com.example.Reserva.Service;

import com.example.Reserva.client.UsuarioClient;
import com.example.Reserva.client.VeterinarioClient;
import com.example.Reserva.dto.UsuarioDTO;
import com.example.Reserva.dto.VeterinarioDTO;
import com.example.Reserva.model.Estado;
import com.example.Reserva.model.Reserva;
import com.example.Reserva.repository.ReservaRepository;
import com.example.Reserva.service.ReservaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private VeterinarioClient veterinarioClient;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reserva;
    private VeterinarioDTO vetDTO;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        Estado estado = new Estado(1L, "Confirmada", "Reserva confirmada", null);
        reserva = new Reserva(1L, Date.valueOf("2025-06-01"), Date.valueOf("2025-06-10"), 5L, 1L, 10000, estado);

        vetDTO = new VeterinarioDTO();
        vetDTO.setCorreo("vet@ejemplo.com");

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("vet@ejemplo.com");
    }

    @Test
    void listarReservas_deberiaRetornarLista() {
        when(reservaRepository.findAll()).thenReturn(List.of(reserva));

        List<Reserva> resultado = reservaService.listarReservas();

        assertEquals(1, resultado.size());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    void buscarReservaPorId_existente_deberiaRetornarReserva() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        Reserva resultado = reservaService.buscarReservaPorId(1L);

        assertNotNull(resultado);
        assertEquals(10000, resultado.getTotal());
    }

    @Test
    void crearReserva_valida_deberiaGuardar() {
        when(veterinarioClient.obtenerVeterinarioPorId(5L)).thenReturn(vetDTO);
        when(usuarioClient.findByEmail("vet@ejemplo.com")).thenReturn(usuarioDTO);
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        Reserva resultado = reservaService.crearReserva(reserva);

        assertNotNull(resultado);
        verify(reservaRepository).save(reserva);
    }

    @Test
    void crearReserva_conReservaNula_deberiaLanzarExcepcion() {
        assertThrows(RuntimeException.class, () -> reservaService.crearReserva(null));
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void crearReserva_veterinarioNoExiste_deberiaLanzarExcepcion() {
        when(veterinarioClient.obtenerVeterinarioPorId(5L)).thenThrow(new RuntimeException());

        assertThrows(IllegalArgumentException.class, () -> reservaService.crearReserva(reserva));
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void crearReserva_usuarioNoExiste_deberiaLanzarExcepcion() {
        when(veterinarioClient.obtenerVeterinarioPorId(5L)).thenReturn(vetDTO);
        when(usuarioClient.findByEmail("vet@ejemplo.com")).thenThrow(new RuntimeException());

        assertThrows(IllegalArgumentException.class, () -> reservaService.crearReserva(reserva));
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void actualizarReserva_existente_deberiaActualizarCampos() {
        Reserva nueva = new Reserva(null, Date.valueOf("2025-07-01"), Date.valueOf("2025-07-10"), 5L, 1L, 20000, reserva.getEstado());

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        Reserva resultado = reservaService.actualizarReserva(1L, nueva);

        assertEquals(20000, resultado.getTotal());
        assertEquals(Date.valueOf("2025-07-10"), resultado.getFechareserva());
    }

    @Test
    void eliminarReserva_existente_deberiaEjecutarDelete() {
        doNothing().when(reservaRepository).deleteById(1L);

        reservaService.eliminarReserva(1L);

        verify(reservaRepository, times(1)).deleteById(1L);
    }

    @Test
    void listarReservasPorUsuarioId_deberiaFiltrarCorrectamente() {
        when(reservaRepository.findByUsuarioId(1L)).thenReturn(List.of(reserva));

        List<Reserva> resultado = reservaService.listarReservasPorUsuarioId(1L);

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getUsuarioId());
    }
}
