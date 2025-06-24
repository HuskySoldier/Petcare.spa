package com.example.Servicios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.Servicios.model.Categoria;
import com.example.Servicios.repository.CategoriaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)//habilitar la inicialización automatica de los mocks
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks // objeto de prueba los mocks inyectados
    private CategoriaService categoriaService;

    private Categoria categoriaValida;

    @BeforeEach// Este método se ejecuta automáticamente **antes de cada test** inicializar objetos comunes
                // * o preparar datos necesarios para cada uno de los métodos de prueba de forma independiente
    public void setUp() {
        categoriaValida = new Categoria();
        categoriaValida.setNombreCategoria("Servicios Generales");
    }

    @Test
    public void testCrearCategoriaExitosa() {
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaValida);

        Categoria resultado = categoriaService.crearCategoria(categoriaValida);

        assertNotNull(resultado);
        assertEquals("Servicios Generales", resultado.getNombreCategoria());
    }

    @Test
    public void testCrearCategoriaNombreInvalido() {
        Categoria categoriaInvalida = new Categoria();
        categoriaInvalida.setNombreCategoria("123$$");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.crearCategoria(categoriaInvalida);
        });

        assertEquals("La categoria solo puede contener letras y espacios", exception.getMessage());
    }

    @Test
    public void testListarCategorias() {
        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(categoriaValida));

        List<Categoria> categorias = categoriaService.listarCategoria();

        assertEquals(1, categorias.size());
        assertEquals("Servicios Generales", categorias.get(0).getNombreCategoria());
    }

    @Test
    public void testObtenerCategoriaPorId_Existe() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaValida));

        Optional<Categoria> resultado = categoriaService.obtenerCategoriaPorId(1L);

        assertTrue(resultado.isPresent());
    }

    @Test
    public void testObtenerCategoriaPorId_NoExiste() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Categoria> resultado = categoriaService.obtenerCategoriaPorId(99L);

        assertFalse(resultado.isPresent());
    }
}