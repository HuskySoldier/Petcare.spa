package com.example.mascota.Service;

import com.example.mascota.client.UsuarioClient;
import com.example.mascota.dto.UsuarioDTO;
import com.example.mascota.model.Especie;
import com.example.mascota.model.Mascota;
import com.example.mascota.model.Raza;
import com.example.mascota.repository.EspecieRepository;
import com.example.mascota.repository.MascotaRepository;
import com.example.mascota.repository.RazaRepository;
import com.example.mascota.service.MascotaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MascotaServiceTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private RazaRepository razaRepository;

    @Mock
    private EspecieRepository especieRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private MascotaService mascotaService;

    private Mascota mascota;
    private UsuarioDTO usuarioDTO;

    @BeforeEach//se usa para inicializar objetos comunes antes de cada test y evitar repetir código.
    void setUp() {
        Raza raza = new Raza(null, "Labrador", null);
        Especie especie = new Especie(null, "Perro", null);

        mascota = new Mascota();
        mascota.setIdUsuario(1L);
        mascota.setRaza(raza);
        mascota.setEspecie(especie);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setRol("CLIENTE");
    }

    @Test
    void agregarMascota_datosValidos_deberiaGuardar() {
        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioDTO);
        when(razaRepository.findByNombreRaza("Labrador")).thenReturn(Optional.empty());
        when(razaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(especieRepository.findByNombreEspecie("Perro")).thenReturn(Optional.empty());
        when(especieRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(mascotaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Mascota resultado = mascotaService.agregarMascota(mascota);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getRaza().getNombreRaza()).isEqualTo("Labrador");
        assertThat(resultado.getEspecie().getNombreEspecie()).isEqualTo("Perro");
    }

    @Test
    void agregarMascota_usuarioNoEsCliente_deberiaLanzarExcepcion() {
        usuarioDTO.setRol("ADMINISTRADOR");
        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioDTO);

        assertThatThrownBy(() -> mascotaService.agregarMascota(mascota))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Solo los usuarios con rol CLIENTE");
    }

    @Test
    void buscarMascotaPorId_existente_deberiaRetornarMascota() {
        Mascota m = new Mascota();
        m.setIdMascota(1L);
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(m));

        Mascota resultado = mascotaService.buscarMascotaPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdMascota()).isEqualTo(1L);
    }

    @Test
    void buscarMascotaPorId_inexistente_deberiaLanzarExcepcion() {
        when(mascotaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mascotaService.buscarMascotaPorId(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Mascota no encontrada");
    }

    @Test
    void eliminarMascota_deberiaEliminarPorId() {
        mascotaService.eliminarMascota(1L);
        verify(mascotaRepository).deleteById(1L);
    }

    @Test
    void obtenerPorIdUsuario_deberiaRetornarListaMascotas() {
        when(mascotaRepository.findByIdUsuario(1L)).thenReturn(List.of(new Mascota()));
        List<Mascota> resultado = mascotaService.obtenerPorIdUsuario(1L);

        assertThat(resultado).hasSize(1);
    }
}
