package com.example.Reserva.Service;

import com.example.Reserva.model.Estado;
import com.example.Reserva.repository.EstadoRepository;
import com.example.Reserva.service.EstadoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadoServiceTest {

    @Mock
    private EstadoRepository estadoRepository;

    @InjectMocks
    private EstadoService estadoService;

    private Estado estado;

    @BeforeEach//se usa para inicializar objetos comunes antes de cada test y evitar repetir código.
    void setUp() {
        estado = new Estado(1L, "Confirmada", "Reserva confirmada", null);
    }

    @Test
    void listarEstados_deberiaRetornarLista() {
        List<Estado> lista = Arrays.asList(estado);

        when(estadoRepository.findAll()).thenReturn(lista);

        List<Estado> resultado = estadoService.listarEstados();

        assertEquals(1, resultado.size());
        assertEquals("Confirmada", resultado.get(0).getNombreEstado());
        verify(estadoRepository, times(1)).findAll();
    }

    @Test
    void crearEstado_valido_deberiaGuardarYRetornar() {
        Estado nuevo = new Estado(null, "Pendiente", "En espera", null);
        Estado guardado = new Estado(1L, "Pendiente", "En espera", null);

        when(estadoRepository.save(nuevo)).thenReturn(guardado);

        Estado resultado = estadoService.crearEstado(nuevo);

        assertNotNull(resultado);
        assertEquals("Pendiente", resultado.getNombreEstado());
        verify(estadoRepository, times(1)).save(nuevo);
    }

    @Test
    void buscarPorId_existente_deberiaRetornarEstado() {
        when(estadoRepository.findById(1L)).thenReturn(Optional.of(estado));

        Estado resultado = estadoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Confirmada", resultado.getNombreEstado());
        verify(estadoRepository).findById(1L);
    }

    @Test
    void crearEstado_conId_deberiaLanzarExcepcion() {
        Estado estadoConId = new Estado(5L, "Rechazada", "No válida", null);

        assertThrows(IllegalArgumentException.class, () -> {
            estadoService.crearEstado(estadoConId);
        });

        verify(estadoRepository, never()).save(any());
    }

}
