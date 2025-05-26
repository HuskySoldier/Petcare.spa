package com.example.Servicios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Servicios.model.Servicio;
import com.example.Servicios.repository.ServicioRepository;

@Service
public class ServicioService {
    @Autowired
    private ServicioRepository servicioRepository;

    // Creacion del servicio
    public Servicio crearServicio(Servicio servicio) {
        // Se valida el precio recibido como String
        validarPrecio(String.valueOf(servicio.getPrecio()));
        // validacion por si se escriben la idServicio que es totalmente innecesario ya que es autoincrementable
        if (servicio.getIdServicio() != null) {
            throw new IllegalArgumentException("No debe enviar el idServicio al crear un nuevo servicio.");
        }
        // validacion para el nombre, unicamente se permita letras 
        if (servicio.getNombre() == null || !servicio.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios");
        }
        // validacion por si se escriben la idCategoria que es totalmente innecesario ya que es autoincrementable
        if (servicio.getCategoria() != null && servicio.getCategoria().getIdCategoria() != null) {
            throw new IllegalArgumentException("No escriba la idCategoria unicamente escriba el nombre de la categoría.");
        }
        // validacion para el precio como String para evitar este error se convierte a int 
        int precioValido = Integer.parseInt(String.valueOf(servicio.getPrecio()));
        servicio.setPrecio(precioValido);
    
        return servicioRepository.save(servicio);


    }

    // validacion para que el precio no sea nulo y no tenga caracteres
    public void validarPrecio(String precioStr) {
        if (precioStr == null || precioStr.isEmpty()) {
            throw new IllegalArgumentException("El precio no puede estar vacío");
        }
        // Validar que solo contenga dígitos
        if (!precioStr.matches("^\\d+$")) {
            throw new IllegalArgumentException("El precio debe ser un número entero válido");
        }
        // Convertir a entero y validar que no sea negativo
        int precio = Integer.parseInt(precioStr);
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
    }

    // buscar todos los servicios creados
    public List<Servicio> allServicio() {
        return servicioRepository.findAll();
    }

    // buscar una servicio por id
    public Optional<Servicio> obtenerServicioPorId(Long idServicio) {
        return servicioRepository.findById(idServicio);
    }

    // Actualizar servicio
    public Servicio actualizarServicio(Long idServicio, Servicio servicioActualizada) {
        if (servicioRepository.existsById(idServicio)) {
            servicioActualizada.setIdServicio(idServicio);
            return servicioRepository.save(servicioActualizada);
        } else {
            throw new RuntimeException("Servicio no encontrado");
        }
    }

    // Borrar un Servicio
    public Boolean deleteServicio(Long idServicio) {
        if (!servicioRepository.existsById(idServicio)) {
            throw new RuntimeException("Servicio no encontrado");
        }
        servicioRepository.deleteById(idServicio);
        return true;
    }

}
