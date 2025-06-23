package com.petcare.clinica_service.repository;

import com.petcare.clinica_service.model.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Clinica.
 * Proporciona métodos de acceso a la base de datos sin necesidad de consultas SQL manuales.
 */
@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
}


//Este código define una interfaz ClinicaRepository, que es un repositorio de 
//Spring Data JPA para la entidad Clinica. Su propósito es proporcionar métodos de acceso a la 
//base de datos sin necesidad de escribir consultas SQL manualmente.
