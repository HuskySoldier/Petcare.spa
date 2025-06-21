package com.example.HistorialMedico.Service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.example.HistorialMedico.client.InventarioClient;
import com.example.HistorialMedico.dto.InventarioDTO;
import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.model.Tratamiento;
import com.example.HistorialMedico.repository.TratamientoRepository;
import com.example.HistorialMedico.service.TratamientoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TratamientoServiceTest {

    @Mock
    private TratamientoRepository tratamientoRepository;

    @Mock
    private InventarioClient inventarioClient;

    @InjectMocks
    private TratamientoService tratamientoService;

    @Test
    void listarTratamientos_debeRetornarLista() {
        Tratamiento t1 = new Tratamiento(1L, LocalDate.now(), "Descripción 1", 1L, new HistorialMedico());
        Tratamiento t2 = new Tratamiento(2L, LocalDate.now(), "Descripción 2", 2L, new HistorialMedico());

        List<Tratamiento> mockList = Arrays.asList(t1, t2);

        when(tratamientoRepository.findAll()).thenReturn(mockList);

        List<Tratamiento> resultado = tratamientoService.listarTratamientos();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).containsExactly(t1, t2);
    }

    @Test
    void guardarTratamiento_debeGuardarCorrectamente() {
        Tratamiento tratamiento = new Tratamiento(1L, LocalDate.now(), "Tratamiento prueba", 1L, new HistorialMedico());

        InventarioDTO inventarioDTO = new InventarioDTO();
        inventarioDTO.setIdInventario(1L);
        inventarioDTO.setNombreInv("Ibuprofeno");
        inventarioDTO.setStockActual(10);
        inventarioDTO.setStockMinimo(2);
        inventarioDTO.setIdProducto(1);

        
        when(tratamientoRepository.save(tratamiento)).thenReturn(tratamiento);

        Tratamiento resultado = tratamientoService.guardarTratamiento(tratamiento);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getDescripcion()).isEqualTo("Tratamiento prueba");
    }
}
