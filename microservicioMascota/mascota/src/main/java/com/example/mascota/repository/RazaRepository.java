package com.example.mascota.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mascota.model.Raza;

@Repository
public interface RazaRepository extends JpaRepository<Raza,Long> {
    Optional<Raza> findByNombreRaza(String nombreRaza);

}
