package com.petcare.clinica_service.service;

import com.petcare.clinica_service.model.Clinica;
import com.petcare.clinica_service.repository.ClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicaService {

    @Autowired
    private ClinicaRepository clinicaRepository;

    public List<Clinica> obtenerTodas() {
        return clinicaRepository.findAll();
    }

    public Optional<Clinica> obtenerPorId(Long id) {
        return clinicaRepository.findById(id);
    }

    public Clinica guardar(Clinica clinica) {
        return clinicaRepository.save(clinica);
    }

    public void eliminar(Long id) {
        clinicaRepository.deleteById(id);
    }
}
