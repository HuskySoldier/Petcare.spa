package com.petcare.clinica_service.controller;

import com.petcare.clinica_service.model.Clinica;
import com.petcare.clinica_service.service.ClinicaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClinicaControllerTest1 {

    private ClinicaService clinicaService;
    private ClinicaController clinicaController;

    @BeforeEach
    void setUp() {
        clinicaService = mock(ClinicaService.class);
        clinicaController = new ClinicaController();
        // Inyectar el mock manualmente porque no usamos @Autowired en test unitario
        var field = Arrays.stream(ClinicaController.class.getDeclaredFields())
                .filter(f -> f.getType().equals(ClinicaService.class))
                .findFirst().orElseThrow();
        field.setAccessible(true);
        try {
            field.set(clinicaController, clinicaService);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testListar() {
        Clinica c1 = new Clinica();
        c1.setNombre("Clinica 1");
        Clinica c2 = new Clinica();
        c2.setNombre("Clinica 2");
        List<Clinica> clinicas = Arrays.asList(c1, c2);

        when(clinicaService.obtenerTodas()).thenReturn(clinicas);

        ResponseEntity<List<Clinica>> response = clinicaController.listar();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(clinicaService).obtenerTodas();
    }

    @Test
    void testObtener_Existe() {
        Clinica c = new Clinica();
        c.setId(1L);
        c.setNombre("Clinica Test");

        when(clinicaService.obtenerPorId(1L)).thenReturn(Optional.of(c));

        ResponseEntity<Clinica> response = clinicaController.obtener(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Clinica Test", response.getBody().getNombre());
        verify(clinicaService).obtenerPorId(1L);
    }

    @Test
    void testObtener_NoExiste() {
        when(clinicaService.obtenerPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Clinica> response = clinicaController.obtener(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(clinicaService).obtenerPorId(99L);
    }

    @Test
    void testCrear() {
        Clinica c = new Clinica();
        c.setNombre("Nueva Clinica");

        when(clinicaService.guardar(ArgumentMatchers.any(Clinica.class))).thenReturn(c);

        ResponseEntity<Clinica> response = clinicaController.crear(c);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Nueva Clinica", response.getBody().getNombre());
        verify(clinicaService).guardar(c);
    }
}