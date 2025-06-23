package com.example.Reportes.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.Reportes.config.UsuarioClient;
import com.example.Reportes.dto.UsuarioDto;
import com.example.Reportes.model.Reporte;
import com.example.Reportes.repository.ReporteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private ReporteService reporteService;

    private UsuarioDto usuarioValido;

    @BeforeEach
    void setUp() {
        usuarioValido = new UsuarioDto();
        usuarioValido.setRol("JEFE_INVENTARIO");
    }

    @Test
    void testCrearReporte_conRolValido() {
        Reporte reporte = new Reporte(null, "Comentario prueba", new Date());
        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioValido);
        when(reporteRepository.save(reporte)).thenReturn(reporte);

        Reporte result = reporteService.crearReporte(reporte, 1L);

        assertEquals("Comentario prueba", result.getComentario());
        verify(reporteRepository).save(reporte);
    }

    @Test
    void testCrearReporte_conRolInvalido() {
        UsuarioDto usuarioInvalido = new UsuarioDto();
        usuarioInvalido.setRol("CLIENTE");
        Reporte reporte = new Reporte(null, "Comentario", new Date());

        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioInvalido);

        Exception ex = assertThrows(RuntimeException.class, () ->
            reporteService.crearReporte(reporte, 1L)
        );

        assertEquals("Acceso denegado: no tienes permisos para crear un Reporte", ex.getMessage());
    }

    @Test
    void testAllReporte() {
        List<Reporte> lista = Arrays.asList(
            new Reporte(1L, "Uno", new Date()),
            new Reporte(2L, "Dos", new Date())
        );

        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioValido);
        when(reporteRepository.findAll()).thenReturn(lista);

        List<Reporte> result = reporteService.allReporte(1L);

        assertEquals(2, result.size());
        verify(reporteRepository).findAll();
    }

    @Test
    void testActualizarReporte_Existe() {
        Reporte actualizado = new Reporte(null, "Actualizado", new Date());

        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioValido);
        when(reporteRepository.existsById(1L)).thenReturn(true);
        when(reporteRepository.save(any(Reporte.class))).thenReturn(actualizado);

        Reporte result = reporteService.actualizarReporte(1L, actualizado, 1L);

        assertEquals("Actualizado", result.getComentario());
        verify(reporteRepository).save(actualizado);
    }

    @Test
    void testActualizarReporte_NoExiste() {
        Reporte reporte = new Reporte(null, "Texto", new Date());

        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioValido);
        when(reporteRepository.existsById(99L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            reporteService.actualizarReporte(99L, reporte, 1L)
        );

        assertEquals("reporte no encontrado", ex.getMessage());
    }
}