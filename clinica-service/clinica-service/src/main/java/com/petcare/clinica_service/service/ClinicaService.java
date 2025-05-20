package com.petcare.clinica_service.service;

import com.petcare.clinica_service.PatchDTO.ClinicaDTO;
import com.petcare.clinica_service.PatchDTO.ClinicaPatchDTO;
import com.petcare.clinica_service.model.Clinica;
import com.petcare.clinica_service.repository.ClinicaRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicaService {

    @Autowired
    private ClinicaRepository repository;

    public Clinica guardarClinica(Clinica clinica) {
        return repository.save(clinica);
    }

    public List<Clinica> listarClinicas() {
        return repository.findAll();
    }

    public Optional<Clinica> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void eliminarClinica(Long id) {
        repository.deleteById(id);
    }

    public Clinica actualizarClinica(Long id, Clinica clinicaActualizada) {
    Clinica existente = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Clínica no encontrada"));

    existente.setDireccion(clinicaActualizada.getDireccion());
    existente.setContacto(clinicaActualizada.getContacto());
    existente.setCapacidad(clinicaActualizada.getCapacidad());

    return repository.save(existente);}

    public Clinica updateParcial(Long id, ClinicaPatchDTO patchDTO) {
    Clinica clinica = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Clinica no encontrada"));

    if (patchDTO.getDireccion() != null) {
        clinica.setDireccion(patchDTO.getDireccion());
    }
    if (patchDTO.getContacto() != null) {
        clinica.setContacto(patchDTO.getContacto());
    }
    if (patchDTO.getCapacidad() != null) {
        clinica.setCapacidad(patchDTO.getCapacidad());
    }

    return repository.save(clinica);
    }

    public Clinica updateCompleto(Long id, ClinicaDTO dto) {
    Clinica clinica = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Clínica no encontrada con ID: " + id));

    clinica.setDireccion(dto.getDireccion());
    clinica.setContacto(dto.getContacto());
    clinica.setCapacidad(dto.getCapacidad());

    return repository.save(clinica);
    }

    public Clinica guardarDesdeDTO(ClinicaDTO dto) {
    Clinica clinica = new Clinica();
    clinica.setDireccion(dto.getDireccion());
    clinica.setContacto(dto.getContacto());
    clinica.setCapacidad(dto.getCapacidad());

    return repository.save(clinica);
    }


}
