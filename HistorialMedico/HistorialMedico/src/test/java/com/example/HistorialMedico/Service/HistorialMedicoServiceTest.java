package com.example.HistorialMedico.Service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.repository.HistorialMedicoRepository;
import com.example.HistorialMedico.service.HistorialMedicoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HistorialMedicoServiceTest {

    @Mock
    private HistorialMedicoRepository historialRepository;

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
    void agregarHistorialMedico_deberiaGuardarYRetornar() {
        HistorialMedico nuevo = new HistorialMedico();
        nuevo.setAntecedentes("A1");
        nuevo.setDiagnostico("D1");
        nuevo.setIdMascota(1L);

        when(historialRepository.save(nuevo)).thenReturn(nuevo);

        HistorialMedico guardado = historialService.agregarHistorialMedico(nuevo);

        assertThat(guardado.getDiagnostico()).isEqualTo("D1");
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
}
