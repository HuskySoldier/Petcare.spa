package com.example.resenas.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.resenas.Dto.ReseniaDto;
import com.example.resenas.Dto.ServicioDto;
import com.example.resenas.Dto.UsuarioDto;
import com.example.resenas.client.ServicioClient;
import com.example.resenas.client.UsuarioClient;
import com.example.resenas.model.Resenia;
import com.example.resenas.repository.ReseniaRepository;
import com.example.resenas.service.ReseniaService;

@ExtendWith(MockitoExtension.class)
public class reseniaServiceTest {

    private ReseniaRepository reseniaRepository;
    private UsuarioClient usuarioClient;
    private ServicioClient servicioClient;
    private ReseniaService reseniaService;

    @BeforeEach
    void setUp() throws Exception {
        reseniaRepository = mock(ReseniaRepository.class);
        usuarioClient = mock(UsuarioClient.class);
        servicioClient = mock(ServicioClient.class);
        reseniaService = new ReseniaService();

        injectMock(reseniaService, "reseniaRepository", reseniaRepository);
        injectMock(reseniaService, "usuarioClient", usuarioClient);
        injectMock(reseniaService, "servicioClient", servicioClient);
    }

    private void injectMock(Object target, String fieldName, Object mock) throws Exception {
        var field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }

    @Test
    void testCrearReseniaDesdeDto() {
        ReseniaDto dto = new ReseniaDto("esta bien ", 5, 9L, 1L);

        // Simular respuesta válida del usuario y servicio
        when(usuarioClient.obtenerUsuarioPorId(1L))
                .thenReturn(new UsuarioDto(1L, "Juan", "juan@mail.com", "CLIENTE"));

        when(servicioClient.getServicioById(9L))
                .thenReturn(new ServicioDto(9L, "Peluquería", 10000));

        when(reseniaRepository.save(any(Resenia.class)))
                .thenAnswer(i -> i.getArgument(0));

        Resenia result = reseniaService.crearReseniaDesdeDto(dto);

        assertEquals(dto.getComentario(), result.getComentario());
        assertEquals(dto.getUsuarioId(), result.getUsuarioId());
        assertEquals(dto.getServicioId(), result.getServicioId());
        verify(reseniaRepository).save(any(Resenia.class));
    }

    @Test
    void testObtenerPorId_Existe() {
        Resenia r = new Resenia(1L, "Excelente", 10, 2L, 3L);
        when(reseniaRepository.findById(1L)).thenReturn(Optional.of(r));

        Optional<Resenia> result = reseniaService.obtenerReseniaPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdResenia());
    }

    @Test
    void testObtenerTodas() {
        List<Resenia> lista = Arrays.asList(new Resenia(), new Resenia());
        when(reseniaRepository.findAll()).thenReturn(lista);

        List<Resenia> result = reseniaService.allResenia();

        assertEquals(2, result.size());
    }

    @Test
    void testDelete_Existe() {
        when(reseniaRepository.existsById(1L)).thenReturn(true);

        Boolean result = reseniaService.deleteResenia(1L);

        assertTrue(result);
        verify(reseniaRepository).deleteById(1L);
    }

    @Test
    void testDelete_NoExiste() {
        when(reseniaRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reseniaService.deleteResenia(1L);
        });

        assertEquals("Reseña no encontrado", exception.getMessage());
    }
}