package com.example.HistorialMedico.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.HistorialMedico.client.UsuarioClient;
import com.example.HistorialMedico.dto.UsuarioDTO;
import com.example.HistorialMedico.enums.Rol;
import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.repository.HistorialMedicoRepository;
import com.example.HistorialMedico.service.HistorialMedicoService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HistorialMedicoServiceTest {

    @Mock
    private HistorialMedicoRepository historialRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private HistorialMedicoService historialService;

    @Test
    void listarHistorialMedico_deberiaRetornarLista() {
        HistorialMedico h1 = new HistorialMedico();
        h1.setHistorialId(1L);
        h1.setFechaRegistro(LocalDate.now());
        h1.setAntecedentes("Antecedente");
        h1.setDiagnostico("Diagnóstico");
        h1.setIdMascota(1L);

        when(historialRepository.findAll()).thenReturn(Arrays.asList(h1));

        List<HistorialMedico> resultado = historialService.listarHistorialMedico();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getHistorialId()).isEqualTo(1L);
    }

    @Test
    void buscarHistorialMedicoPorId_cuandoExiste() {
        HistorialMedico h1 = new HistorialMedico();
        h1.setHistorialId(2L);
        h1.setFechaRegistro(LocalDate.now());
        h1.setDiagnostico("Diagnóstico");

        when(historialRepository.findById(2L)).thenReturn(Optional.of(h1));

        HistorialMedico encontrado = historialService.buscarHistorialMedicoPorId(2L);

        assertThat(encontrado.getDiagnostico()).isEqualTo("Diagnóstico");
    }

    @Test
    void buscarHistorialMedicoPorId_cuandoNoExiste_lanzaExcepcion() {
        when(historialRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            historialService.buscarHistorialMedicoPorId(100L);
        });
    }

    @Test
    void agregarHistorialMedico_conRolPermitido_deberiaGuardarYRetornar() {
        HistorialMedico nuevo = new HistorialMedico();
        nuevo.setAntecedentes("A1");
        nuevo.setDiagnostico("D1");
        nuevo.setIdMascota(1L);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setRol(Rol.ADMINISTRADOR.name());

        when(usuarioClient.obtenerUsuarioPorId(anyLong())).thenReturn(usuarioDTO);
        when(historialRepository.save(nuevo)).thenReturn(nuevo);

        HistorialMedico guardado = historialService.agregarHistorialMedico(nuevo, 1L);

        assertThat(guardado.getDiagnostico()).isEqualTo("D1");
    }

    @Test
    void agregarHistorialMedico_conRolNoPermitido_deberiaLanzarExcepcion() {
        HistorialMedico nuevo = new HistorialMedico();

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setRol(Rol.VETERINARIO.name()); // rol NO permitido

        when(usuarioClient.obtenerUsuarioPorId(anyLong())).thenReturn(usuarioDTO);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            historialService.agregarHistorialMedico(nuevo, 1L);
        });

        assertThat(exception.getMessage()).contains("Acceso denegado");
    }

    @Test
    void eliminarHistorialMedico_deberiaEliminarPorId() {
        Long id = 5L;

        historialService.eliminarHistorialMedico(id);

        verify(historialRepository).deleteById(id);
    }

    @Test
    void buscarPorIdMascota_deberiaRetornarHistoriales() {
        HistorialMedico h = new HistorialMedico();
        h.setIdMascota(3L);

        when(historialRepository.findByIdMascota(3L)).thenReturn(Arrays.asList(h));

        List<HistorialMedico> resultado = historialService.buscarPorIdMascota(3L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getIdMascota()).isEqualTo(3L);
    }

    @Test
    void modificarHistorialMedico_conRolPermitido_deberiaModificarYRetornar() {
        HistorialMedico existente = new HistorialMedico();
        existente.setHistorialId(10L);
        existente.setDiagnostico("Viejo");

        HistorialMedico actualizado = new HistorialMedico();
        actualizado.setDiagnostico("Nuevo");

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setRol(Rol.JEFE_INVENTARIO.name());

        when(usuarioClient.obtenerUsuarioPorId(anyLong())).thenReturn(usuarioDTO);
        when(historialRepository.findById(10L)).thenReturn(Optional.of(existente));
        when(historialRepository.save(any(HistorialMedico.class))).thenAnswer(i -> i.getArgument(0));

        HistorialMedico resultado = historialService.modificarHistorialMedico(10L, actualizado, 1L);

        assertThat(resultado.getDiagnostico()).isEqualTo("Nuevo");
    }

    @Test
    void modificarHistorialMedico_conRolNoPermitido_deberiaLanzarExcepcion() {
        HistorialMedico actualizado = new HistorialMedico();

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setRol(Rol.VETERINARIO.name()); // No permitido

        when(usuarioClient.obtenerUsuarioPorId(anyLong())).thenReturn(usuarioDTO);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            historialService.modificarHistorialMedico(1L, actualizado, 1L);
        });

        assertThat(exception.getMessage()).contains("Acceso denegado");
    }

    @Test
    void modificarHistorialMedico_cuandoNoExisteHistorial_deberiaLanzarEntityNotFoundException() {
        HistorialMedico actualizado = new HistorialMedico();

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setRol(Rol.ADMINISTRADOR.name());

        when(usuarioClient.obtenerUsuarioPorId(anyLong())).thenReturn(usuarioDTO);
        when(historialRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            historialService.modificarHistorialMedico(99L, actualizado, 1L);
        });
    }

}
