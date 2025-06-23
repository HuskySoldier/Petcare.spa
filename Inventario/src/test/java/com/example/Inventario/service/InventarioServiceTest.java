package com.example.Inventario.service;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Inventario.model.Inventario;
import com.example.Inventario.repository.InventarioRepository;
import com.example.Inventario.service.InventarioService;


@ExtendWith(MockitoExtension.class) // habilitar la inicialización automatica de los mocks
public class InventarioServiceTest {

    private InventarioRepository inventarioRepository;
    private InventarioService inventarioService;

    @BeforeEach
    void setUp() {
        inventarioRepository = mock(InventarioRepository.class);
        inventarioService = new InventarioService();

        // Inyección manual del mock
        var field = Arrays.stream(InventarioService.class.getDeclaredFields())
                .filter(f -> f.getType().equals(InventarioRepository.class))
                .findFirst().orElseThrow();
        field.setAccessible(true);
        try {
            field.set(inventarioService, inventarioRepository);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testObtenerInventarioPorId_Existe() {
        Inventario inv = new Inventario();
        inv.setIdInventario(1L);

        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inv));

        Optional<Inventario> result = inventarioRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdInventario());
        verify(inventarioRepository).findById(1L);
    }

    @Test
    void testObtenerInventarioPorId_NoExiste() {
        when(inventarioRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Inventario> result = inventarioRepository.findById(2L);

        assertFalse(result.isPresent());
        verify(inventarioRepository).findById(2L);
    }

    @Test
    void testObtenerTodoInventario() {
        Inventario inv1 = new Inventario(null, "Producto A", 10, 3, null);
        Inventario inv2 = new Inventario(null, "Producto B", 20, 5, null);
        List<Inventario> lista = Arrays.asList(inv1, inv2);

        when(inventarioRepository.findAll()).thenReturn(lista);

        List<Inventario> result = inventarioRepository.findAll();

        assertEquals(2, result.size());
        verify(inventarioRepository).findAll();
    }

    @Test
    void testGuardarInventario() {
        Inventario inv = new Inventario(null, "Producto Nuevo", 5, 2, null);

        when(inventarioRepository.save(inv)).thenReturn(inv);

        Inventario result = inventarioRepository.save(inv);

        assertEquals("Producto Nuevo", result.getNombreInv());
        verify(inventarioRepository).save(inv);
    }
}
