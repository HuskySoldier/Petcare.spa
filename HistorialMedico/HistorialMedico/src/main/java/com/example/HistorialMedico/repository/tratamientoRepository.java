package com.example.HistorialMedico.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.HistorialMedico.model.Tratamiento;

public interface TratamientoRepository extends JpaRepository<Tratamiento,Long> {

}
