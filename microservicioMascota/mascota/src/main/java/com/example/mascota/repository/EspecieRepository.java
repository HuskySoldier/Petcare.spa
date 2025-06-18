package com.example.mascota.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mascota.model.Especie;

@Repository
public interface EspecieRepository extends JpaRepository<Especie,Long> {
    Optional<Especie> findByNombreEspecie(String nombreEspecie);

}
