package com.petcare.clinica_service.service;

import com.petcare.clinica_service.model.Clinica;
import com.petcare.clinica_service.repository.ClinicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClinicaServiceTest {

    private ClinicaRepository clinicaRepository;
    private ClinicaService clinicaService;

    @BeforeEach
    void setUp() {
        clinicaRepository = mock(ClinicaRepository.class);
        clinicaService = new ClinicaService();
        // Inyectar el mock manualmente
        var field = Arrays.stream(ClinicaService.class.getDeclaredFields())
                .filter(f -> f.getType().equals(ClinicaRepository.class))
                .findFirst().orElseThrow();
        field.setAccessible(true);
        try {
            field.set(clinicaService, clinicaRepository);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testObtenerTodas() {
        Clinica c1 = new Clinica();
        c1.setNombre("Clinica 1");
        Clinica c2 = new Clinica();
        c2.setNombre("Clinica 2");
        List<Clinica> clinicas = Arrays.asList(c1, c2);

        when(clinicaRepository.findAll()).thenReturn(clinicas);

        List<Clinica> result = clinicaService.obtenerTodas();

        assertEquals(2, result.size());
        verify(clinicaRepository).findAll();
    }

    @Test
    void testObtenerPorId_Existe() {
        Clinica c = new Clinica();
        c.setId(1L);

        when(clinicaRepository.findById(1L)).thenReturn(Optional.of(c));

        Optional<Clinica> result = clinicaService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(clinicaRepository).findById(1L);
    }

    @Test
    void testObtenerPorId_NoExiste() {
        when(clinicaRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Clinica> result = clinicaService.obtenerPorId(2L);

        assertFalse(result.isPresent());
        verify(clinicaRepository).findById(2L);
    }

    @Test
    void testGuardar() {
        Clinica c = new Clinica();
        c.setNombre("Clinica Guardada");

        when(clinicaRepository.save(c)).thenReturn(c);

        Clinica result = clinicaService.guardar(c);

        assertEquals("Clinica Guardada", result.getNombre());
        verify(clinicaRepository).save(c);
    }
}