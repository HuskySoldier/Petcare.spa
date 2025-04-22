package com.petcare.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petcare.pet.model.Cita;

import java.util.List;


public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByCliente(String cliente);
   
}

