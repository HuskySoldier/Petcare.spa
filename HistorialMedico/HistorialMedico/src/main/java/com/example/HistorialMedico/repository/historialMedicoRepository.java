package com.example.HistorialMedico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HistorialMedico.model.HistorialMedico;

import feign.Param;
@Repository

public interface historialMedicoRepository extends JpaRepository<HistorialMedico,Long>{
    @Query("SELECT h FROM HistorialMedico h LEFT JOIN FETCH h.tratamientos WHERE h.historialId = :id")
    Optional<HistorialMedico> findByIdWithTratamientos(@Param("id") Long id);

}
