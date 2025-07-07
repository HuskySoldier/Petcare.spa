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

import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
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
        vetDTO.setVeterinarioId(5L);
        vetDTO.setCorreo("vet@ejemplo.com");
        vetDTO.setNombre("Carlos");
        vetDTO.setApellido("Pérez");

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("vet@ejemplo.com");
        usuarioDTO.setRol("CLIENTE");
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
        // Mock de respuesta de veterinario y usuario con ResponseEntity.ok()
        ResponseEntity<VeterinarioDTO> vetResponse = ResponseEntity.ok(vetDTO);
        ResponseEntity<UsuarioDTO> usuarioResponse = ResponseEntity.ok(usuarioDTO);

        when(veterinarioClient.obtenerVeterinarioPorId(eq(5L), anyLong())).thenReturn(vetResponse);
        when(usuarioClient.getUsuarioById(eq(1L))).thenReturn(usuarioResponse);
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        Reserva resultado = reservaService.crearReserva(reserva, 1L);

        assertNotNull(resultado);
        verify(reservaRepository).save(reserva);
    }

    @Test
    void crearReserva_veterinarioNoExiste_deberiaLanzarExcepcion() {
        when(veterinarioClient.obtenerVeterinarioPorId(eq(5L), anyLong()))
                .thenReturn(ResponseEntity.notFound().build());

        // Mock usuario para evitar NullPointerException aunque no se use
        when(usuarioClient.getUsuarioById(anyLong())).thenReturn(ResponseEntity.ok(usuarioDTO));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            reservaService.crearReserva(reserva, 1L);
        });

        assertEquals("El veterinario no existe.", thrown.getMessage());
        verify(reservaRepository, never()).save(any());
    }

    // En el test crearReserva_usuarioNoExiste_deberiaLanzarExcepcion

    @Test
    void crearReserva_usuarioNoExiste_deberiaLanzarExcepcion() {
        ResponseEntity<VeterinarioDTO> vetResponse = ResponseEntity.ok(vetDTO);
        // marcar mock lenient para evitar error UnnecessaryStubbingException
        lenient().when(veterinarioClient.obtenerVeterinarioPorId(eq(5L), anyLong())).thenReturn(vetResponse);
        when(usuarioClient.getUsuarioById(eq(1L))).thenReturn(ResponseEntity.notFound().build());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            reservaService.crearReserva(reserva, 1L);
        });

        assertEquals("El usuario no existe.", thrown.getMessage());
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void crearReserva_usuarioRolNoCliente_deberiaLanzarExcepcion() {
        ResponseEntity<VeterinarioDTO> vetResponse = ResponseEntity.ok(vetDTO);
        lenient().when(veterinarioClient.obtenerVeterinarioPorId(eq(5L), anyLong())).thenReturn(vetResponse);

        UsuarioDTO usuarioNoCliente = new UsuarioDTO();
        usuarioNoCliente.setRol("ADMIN");
        ResponseEntity<UsuarioDTO> usuarioResponse = ResponseEntity.ok(usuarioNoCliente);

        when(usuarioClient.getUsuarioById(eq(1L))).thenReturn(usuarioResponse);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            reservaService.crearReserva(reserva, 1L);
        });

        assertEquals("Solo los usuarios con rol CLIENTE pueden crear reservas.", thrown.getMessage());
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void actualizarReserva_existente_deberiaActualizarCampos() {
        Reserva nueva = new Reserva(null, Date.valueOf("2025-07-01"), Date.valueOf("2025-07-10"), 5L, 1L, 20000,
                reserva.getEstado());

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

    @Test
    void obtenerTodosLosVeterinarios_deberiaRetornarLista() {
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

        when(veterinarioClient.listarVeterinarios(anyLong())).thenReturn(List.of(v1, v2));

        List<VeterinarioDTO> resultado = reservaService.obtenerTodosLosVeterinarios(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Carlos", resultado.get(0).getNombre());
        assertEquals("Ana", resultado.get(1).getNombre());
        verify(veterinarioClient, times(1)).listarVeterinarios(anyLong());
    }
}
