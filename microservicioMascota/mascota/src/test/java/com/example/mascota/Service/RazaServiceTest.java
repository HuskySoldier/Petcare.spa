package com.example.mascota.Service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.mascota.model.Raza;
import com.example.mascota.repository.RazaRepository;
import com.example.mascota.service.RazaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RazaServiceTest {

    @InjectMocks
    private RazaService razaService;

    @Mock
    private RazaRepository razaRepository;

    @Test
    void listarRaza_DeberiaRetornarListaDeRazas() {
        // Arrange
        List<Raza> lista = List.of(new Raza(1L, "Poodle", null), new Raza(2L, "Bulldog", null));
        when(razaRepository.findAll()).thenReturn(lista);

        // Act
        List<Raza> resultado = razaService.listarRaza();

        // Assert
        assertEquals(2, resultado.size());
        verify(razaRepository).findAll();
    }

    @Test
    void buscarRaza_CuandoExiste_DeberiaRetornarRaza() {
        // Arrange
        Raza raza = new Raza(1L, "Poodle", null);
        when(razaRepository.findById(1L)).thenReturn(Optional.of(raza));

        // Act
        Raza resultado = razaService.buscarRaza(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Poodle", resultado.getNombreRaza());
        verify(razaRepository).findById(1L);
    }

    @Test
    void buscarRaza_CuandoNoExiste_DeberiaLanzarExcepcion() {
        // Arrange
        when(razaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> razaService.buscarRaza(1L));
        verify(razaRepository).findById(1L);
    }

    @Test
    void agregarRaza_DeberiaGuardarYRetornarRaza() {
        // Arrange
        Raza raza = new Raza(null, "Poodle", null);
        Raza razaGuardada = new Raza(1L, "Poodle", null);
        when(razaRepository.save(raza)).thenReturn(razaGuardada);

        // Act
        Raza resultado = razaService.agregarRaza(raza);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdRaza());
        verify(razaRepository).save(raza);
    }

    @Test
    void eliminarRaza_DeberiaLlamarAlRepositorio() {
        // Act
        razaService.eliminarRaza(1L);

        // Assert
        verify(razaRepository).deleteById(1L);
    }
}
