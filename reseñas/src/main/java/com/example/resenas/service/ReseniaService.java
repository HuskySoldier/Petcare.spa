package com.example.resenas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.resenas.Dto.ReseniaDto;
import com.example.resenas.Dto.ReseniaResponseDto;
import com.example.resenas.Dto.ServicioDto;
import com.example.resenas.client.ServicioClient;
import com.example.resenas.model.Resenia;
import com.example.resenas.repository.ReseniaRepository;

import feign.FeignException;

@Service
public class ReseniaService {
    @Autowired
    private ReseniaRepository reseniaRepository;

    @Autowired
    private ServicioClient servicioClient;

    

    public Resenia crearReseniaDesdeDto(ReseniaDto dto) {
        // Validar calificación
        validarCalificacion(dto.getCalificacion());
        // Validar comentario
        if (dto.getComentario() == null || !dto.getComentario().matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
            throw new IllegalArgumentException("El comentario solo puede contener letras y espacios");
        }
        // Validar existencia del servicio
        ServicioDto servicio;
        try {
            servicio = servicioClient.getServicioById(dto.getServicioId());
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("El servicio con ID " + dto.getServicioId() + " no existe.");
        }
        // Crear y guardar la reseña
        Resenia resenia = new Resenia();
        resenia.setComentario(dto.getComentario());
        resenia.setCalificacion(dto.getCalificacion());
        resenia.setServicioId(dto.getServicioId());

        return reseniaRepository.save(resenia);
    }

    /**
     * Construye un DTO que combina los datos de una reseña con la información del
     * servicio asociado,
     * obtenida mediante una llamada al microservicio de servicios.
     */
    public ReseniaResponseDto construirRespuestaConServicio(Resenia resenia) {
        // Llamada al microservicio de servicios para obtener los datos del servicio
        // relacionado
        ServicioDto servicio = servicioClient.getServicioById(resenia.getServicioId());

        // Crear y completar el DTO de respuesta
        ReseniaResponseDto response = new ReseniaResponseDto();
        response.setIdResenia(resenia.getIdResenia());
        response.setComentario(resenia.getComentario());
        response.setCalificacion(resenia.getCalificacion());

        response.setServicioId(servicio.getIdServicio());
        response.setServicioNombre(servicio.getNombre());
        response.setServicioPrecio(servicio.getPrecio());

        return response;
    }

    // validacion para que el precio no sea nulo y no tenga caracteres
    public void validarCalificacion(int calificacion) {
        if (calificacion < 1 || calificacion > 10) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 10");
        }
    }

    // obtener una reseña por id
    public Optional<Resenia> obtenerReseniaPorId(Long idResenia) {
        return reseniaRepository.findById(idResenia);
    }

    // obtener todas las reseñas
    public List<Resenia> allResenia() {
        return reseniaRepository.findAll();
    }

    // actualizar una reseña
    public Resenia actualizarResenia(Long idResenia, Resenia reseniaActualizada) {
        if (reseniaRepository.existsById(idResenia)) {
            reseniaActualizada.setIdResenia(idResenia);
            return reseniaRepository.save(reseniaActualizada);
        } else {
            throw new RuntimeException("Reseña no encontrado");
        }
    }

    // eliminar una reseña
    public Boolean deleteResenia(Long idResenia) {
        if (!reseniaRepository.existsById(idResenia)) {
            throw new RuntimeException("Reseña no encontrado");
        }
        reseniaRepository.deleteById(idResenia);
        return true;
    }


}
