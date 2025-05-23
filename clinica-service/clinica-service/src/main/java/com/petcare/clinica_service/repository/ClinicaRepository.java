package com.petcare.clinica_service.repository;

import com.petcare.clinica_service.model.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
}
//Este código define una interfaz ClinicaRepository, que es un repositorio de 
//Spring Data JPA para la entidad Clinica. Su propósito es proporcionar métodos de acceso a la 
//base de datos sin necesidad de escribir consultas SQL manualmente.
