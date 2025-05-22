package com.example.HistorialMedico.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.HistorialMedico.model.Tratamiento;

public interface tratamientoRepository extends JpaRepository<Tratamiento,Long> {

}
