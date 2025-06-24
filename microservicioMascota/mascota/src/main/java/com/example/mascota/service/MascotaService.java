    package com.example.mascota.service;

    import java.util.List;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import com.example.mascota.client.UsuarioClient;
    import com.example.mascota.dto.UsuarioDTO;
    import com.example.mascota.enums.Rol;
    import com.example.mascota.model.Especie;
    import com.example.mascota.model.Mascota;
    import com.example.mascota.model.Raza;
    import com.example.mascota.repository.EspecieRepository;
    import com.example.mascota.repository.MascotaRepository;
    import com.example.mascota.repository.RazaRepository;

    import feign.FeignException;

    @Service
    public class MascotaService {

        @Autowired
        private MascotaRepository mascotaRepository;

        @Autowired
        private RazaRepository razaRepository;

        @Autowired
        private EspecieRepository especieRepository;

        @Autowired
        private UsuarioClient usuarioClient;

        public List<Mascota> listarMacotas() {
            return mascotaRepository.findAll();
        }

        public Mascota agregarMascota(Mascota mascota) {
            if (mascota.getIdUsuario() == null) {
                throw new IllegalArgumentException("Debe indicar el ID del usuario dueño de la mascota.");
            }

            // Validar existencia del usuario
            UsuarioDTO usuario;
            try {
                usuario = usuarioClient.obtenerUsuarioPorId(mascota.getIdUsuario());
            } catch (FeignException.NotFound e) {
                throw new RuntimeException("El usuario con ID " + mascota.getIdUsuario() + " no existe.");
            }

            // Validar rol del usuario (por ejemplo, solo usuarios tipo CLIENTE pueden tener
            // mascotas)
            Rol rol = Rol.valueOf(usuario.getRol().toUpperCase());
            if (rol != Rol.CLIENTE) {
                throw new RuntimeException("Solo los usuarios con rol CLIENTE pueden tener mascotas.");
            }

            // Validar datos obligatorios de especie y raza
            if (mascota.getRaza() == null || mascota.getRaza().getNombreRaza() == null) {
                throw new IllegalArgumentException("Debe enviar el nombre de la raza.");
            }

            if (mascota.getEspecie() == null || mascota.getEspecie().getNombreEspecie() == null) {
                throw new IllegalArgumentException("Debe enviar el nombre de la especie.");
            }

            // Guardar especie y raza si no existen
            Raza raza = razaRepository.findByNombreRaza(mascota.getRaza().getNombreRaza())
                    .orElseGet(() -> razaRepository.save(new Raza(null, mascota.getRaza().getNombreRaza(), null)));

            Especie especie = especieRepository.findByNombreEspecie(mascota.getEspecie().getNombreEspecie())
                    .orElseGet(
                            () -> especieRepository.save(new Especie(null, mascota.getEspecie().getNombreEspecie(), null)));

            mascota.setRaza(raza);
            mascota.setEspecie(especie);

            return mascotaRepository.save(mascota);
        }

        public Mascota buscarMascotaPorId(Long id) {
            return mascotaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));
        }

        public void eliminarMascota(Long id) {
            mascotaRepository.deleteById(id);
        }

        // Nuevo método para filtrar por usuario
        public List<Mascota> obtenerPorIdUsuario(Long idUsuario) {
            return mascotaRepository.findByIdUsuario(idUsuario);
        }

        public List<Mascota> allMascotas(Long idUsuario) {
            UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);

            Rol rol;
            try {
                rol = Rol.valueOf(usuario.getRol().trim().toUpperCase());
            } catch (Exception e) {
                throw new RuntimeException("Rol no válido o no especificado.");
            }

            if (rol != Rol.ADMINISTRADOR && rol != Rol.VETERINARIO) {
                throw new RuntimeException("Acceso denegado: no tienes permisos para ver todas las mascotas.");
            }

            return mascotaRepository.findAll();
        }

    }
