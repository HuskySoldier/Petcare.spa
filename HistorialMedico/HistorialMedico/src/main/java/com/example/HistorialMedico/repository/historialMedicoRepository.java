package com.example.HistorialMedico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HistorialMedico.model.HistorialMedico;
@Repository

public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico,Long>{

}
