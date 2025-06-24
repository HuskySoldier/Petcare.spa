package com.example.veterinario.Service;

import com.example.veterinario.client.VeterinarioClient;
import com.example.veterinario.dto.UsuarioDTO;
import com.example.veterinario.model.Veterinario;
import com.example.veterinario.repository.VeterinarioRepository;
import com.example.veterinario.service.VeterinarioService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VeterinarioServiceTest {

    @InjectMocks
    private VeterinarioService veterinarioService;

    @Mock
    private VeterinarioRepository veterinariorepository;

    @Mock
    private VeterinarioClient veterinarioClient;

    @Test
    void listarVeterinarios_deberiaRetornarLista() {
        Veterinario v1 = new Veterinario(1L, 12345678, "Juan", "Perez", "Cardiologia", "juan@mail.com", 101L);
        Veterinario v2 = new Veterinario(2L, 87654321, "Ana", "Gomez", "Dermatologia", "ana@mail.com", 102L);

        when(veterinariorepository.findAll()).thenReturn(List.of(v1, v2));

        List<Veterinario> resultado = veterinarioService.listarVeterinarios();

        assertEquals(2, resultado.size());
        verify(veterinariorepository, times(1)).findAll();
    }

    @Test
    void agregarVeterinario_datosValidos_deberiaGuardarYRetornarVeterinario() {
        Veterinario v = new Veterinario(null, 12345678, "Juan", "Perez", "Cardiologia", "juan@mail.com", 101L);
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setRol("ADMINISTRADOR");

        when(veterinarioClient.findById(101L))
                .thenReturn(new ResponseEntity<>(usuarioDTO, HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(usuarioDTO, HttpStatus.OK)); // para la segunda llamada

        when(veterinariorepository.save(v)).thenReturn(v);

        Veterinario resultado = veterinarioService.agregarVeterinario(v, 101L);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());

        verify(veterinarioClient, times(2)).findById(101L); // espera dos llamadas
        verify(veterinariorepository, times(1)).save(v);
    }

    @Test
    void buscarVeterinarioPorId_cuandoExiste_deberiaRetornarVeterinario() {
        Veterinario v = new Veterinario(1L, 12345678, "Juan", "Perez", "Cardiologia", "juan@mail.com", 101L);

        when(veterinariorepository.findById(1L)).thenReturn(Optional.of(v));

        Veterinario resultado = veterinarioService.buscarVeterinarioPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(veterinariorepository, times(1)).findById(1L);
    }

    @Test
    void eliminarVeterinario_deberiaLlamarDelete() {
        doNothing().when(veterinariorepository).deleteById(1L);

        veterinarioService.eliminarVeterinario(1L);

        verify(veterinariorepository, times(1)).deleteById(1L);
    }
}
