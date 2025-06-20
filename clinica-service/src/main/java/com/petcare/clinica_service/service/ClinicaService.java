package com.petcare.clinica_service.service;

import com.petcare.clinica_service.model.Clinica;
import com.petcare.clinica_service.repository.ClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de clínicas.
 */
@Service
public class ClinicaService {

    @Autowired
    private ClinicaRepository clinicaRepository;

    /**
     * Obtiene todas las clínicas.
     * @return Lista de clínicas.
     */
    public List<Clinica> obtenerTodas() {
        return clinicaRepository.findAll();
    }

    /**
     * Obtiene una clínica por su ID.
     * @param id Identificador de la clínica.
     * @return Clínica encontrada o vacía si no existe.
     */
    public Optional<Clinica> obtenerPorId(Long id) {
        return clinicaRepository.findById(id);
    }

    /**
     * Guarda una clínica (nueva o existente).
     * @param clinica Clínica a guardar.
     * @return Clínica guardada.
     */
    public Clinica guardar(Clinica clinica) {
        return clinicaRepository.save(clinica);
    }

}
