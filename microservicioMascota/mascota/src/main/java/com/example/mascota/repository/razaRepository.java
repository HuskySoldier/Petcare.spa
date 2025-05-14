package com.example.mascota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mascota.model.Raza;

@Repository
public interface razaRepository extends JpaRepository<Raza,Long> {

}
