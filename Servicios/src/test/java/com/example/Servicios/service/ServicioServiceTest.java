package com.example.Servicios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.Servicios.model.Categoria;
import com.example.Servicios.model.Servicio;
import com.example.Servicios.repository.ServicioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)//habilitar la inicialización automatica de los mocks
public class ServicioServiceTest {

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks// objeto de prueba los mocks inyectados
    private ServicioService servicioService;

    private Servicio servicioValido;

    @BeforeEach// Este método se ejecuta automáticamente **antes de cada test** inicializar objetos comunes
                // * o preparar datos necesarios para cada uno de los métodos de prueba de forma independiente
    public void setUp() {
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria("Veterinaria");

        servicioValido = new Servicio();
        servicioValido.setNombre("Baño");
        servicioValido.setDescripcion("Baño completo");
        servicioValido.setPrecio(5000);
        servicioValido.setCategoria(categoria);
    }

    @Test
    public void testCrearServicio_Exitoso() {
        when(servicioRepository.save(any(Servicio.class))).thenReturn(servicioValido);

        Servicio resultado = servicioService.crearServicio(servicioValido);

        assertNotNull(resultado);
        assertEquals("Baño", resultado.getNombre());
        assertEquals(5000, resultado.getPrecio());
    }

    @Test
    public void testCrearServicio_conIdServicio() {
        servicioValido.setIdServicio(1L);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            servicioService.crearServicio(servicioValido);
        });

        assertEquals("No debe enviar el idServicio al crear un nuevo servicio.", e.getMessage());
    }

    @Test
    public void testCrearServicio_nombreInvalido() {
        servicioValido.setNombre("123#");

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            servicioService.crearServicio(servicioValido);
        });

        assertEquals("El nombre solo puede contener letras y espacios", e.getMessage());
    }

    @Test
    public void testCrearServicio_conIdCategoriaManual() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(10L);
        categoria.setNombreCategoria("Peluquería");

        servicioValido.setCategoria(categoria);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            servicioService.crearServicio(servicioValido);
        });

        assertEquals("No escriba la idCategoria unicamente escriba el nombre de la categoría.", e.getMessage());
    }

    @Test
    public void testValidarPrecio_invalido() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            servicioService.validarPrecio("abc");
        });

        assertEquals("El precio debe ser un número entero válido", e.getMessage());
    }

    @Test
    public void testObtenerServicioPorId_existe() {
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicioValido));

        Optional<Servicio> resultado = servicioService.obtenerServicioPorId(1L);

        assertTrue(resultado.isPresent());
    }

    @Test
    public void testActualizarServicio_exitoso() {
        when(servicioRepository.existsById(1L)).thenReturn(true);
        when(servicioRepository.save(any(Servicio.class))).thenReturn(servicioValido);

        Servicio resultado = servicioService.actualizarServicio(1L, servicioValido);

        assertEquals("Baño", resultado.getNombre());
    }

    @Test
    public void testEliminarServicio_exitoso() {
        when(servicioRepository.existsById(1L)).thenReturn(true);

        Boolean eliminado = servicioService.deleteServicio(1L);

        assertTrue(eliminado);
        verify(servicioRepository).deleteById(1L);
    }

    @Test
    public void testEliminarServicio_noExiste() {
        when(servicioRepository.existsById(1L)).thenReturn(false);

        Exception e = assertThrows(RuntimeException.class, () -> {
            servicioService.deleteServicio(1L);
        });

        assertEquals("Servicio no encontrado", e.getMessage());
    }

    @Test
    public void testListarServicios() {
        when(servicioRepository.findAll()).thenReturn(Arrays.asList(servicioValido));

        List<Servicio> lista = servicioService.allServicio();

        assertEquals(1, lista.size());
    }
}