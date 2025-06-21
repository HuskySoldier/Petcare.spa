package com.example.mascota.Service;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.mascota.client.UsuarioClient;
import com.example.mascota.model.Especie;
import com.example.mascota.model.Mascota;
import com.example.mascota.model.Raza;
import com.example.mascota.repository.EspecieRepository;
import com.example.mascota.repository.MascotaRepository;
import com.example.mascota.repository.RazaRepository;
import com.example.mascota.service.MascotaService;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MascotaServiceTest {

    @InjectMocks
    private MascotaService mascotaService;

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private RazaRepository razaRepository;

    @Mock
    private EspecieRepository especieRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Test
    void agregarMascota_CuandoDatosValidos_DeberiaGuardarYRetornarMascota() {
        // Arrange
        Mascota mascota = new Mascota();
        mascota.setIdUsuario(1L);
        mascota.setRaza(new Raza(null, "Poodle", null));
        mascota.setEspecie(new Especie(null, "Canino", null));

        when(usuarioClient.getUsuarioById(1L)).thenReturn(null); // usuario existe

        Raza raza = new Raza(1L, "Poodle", null);
        Especie especie = new Especie(1L, "Canino", null);

        when(razaRepository.findByNombreRaza("Poodle")).thenReturn(Optional.of(raza));
        when(especieRepository.findByNombreEspecie("Canino")).thenReturn(Optional.of(especie));

        Mascota mascotaGuardada = new Mascota();
        mascotaGuardada.setIdMascota(1L);
        mascotaGuardada.setIdUsuario(1L);
        mascotaGuardada.setRaza(raza);
        mascotaGuardada.setEspecie(especie);

        when(mascotaRepository.save(any(Mascota.class))).thenReturn(mascotaGuardada);

        
        Mascota resultado = mascotaService.agregarMascota(mascota);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdMascota());
        assertEquals("Poodle", resultado.getRaza().getNombreRaza());
        assertEquals("Canino", resultado.getEspecie().getNombreEspecie());

        verify(mascotaRepository).save(any(Mascota.class));
    }

    @Test
    void agregarMascota_CuandoIdMascotaYaExiste_DeberiaLanzarExcepcion() {
        Mascota mascota = new Mascota();
        mascota.setIdMascota(1L); // ya existe

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            mascotaService.agregarMascota(mascota);
        });

        assertEquals("No debe enviar el idMascota al crear una nueva mascota.", ex.getMessage());
    }

    @Test
    void agregarMascota_CuandoUsuarioNoExiste_DeberiaLanzarExcepcion() {
        Mascota mascota = new Mascota();
        mascota.setIdUsuario(99L);
        mascota.setRaza(new Raza(null, "Poodle", null));
        mascota.setEspecie(new Especie(null, "Canino", null));

        doThrow(new RuntimeException()).when(usuarioClient).getUsuarioById(99L);

        Exception ex = assertThrows(RuntimeException.class, () -> {
            mascotaService.agregarMascota(mascota);
        });

        assertEquals("Usuario con ID 99 no existe.", ex.getMessage());
    }

    @Test
    void listarMascotas_DeberiaRetornarListaDeMascotas() {
        when(mascotaRepository.findAll()).thenReturn(List.of(new Mascota(), new Mascota()));

        List<Mascota> resultado = mascotaService.listarMacotas();

        assertEquals(2, resultado.size());
        verify(mascotaRepository).findAll();
    }
}
