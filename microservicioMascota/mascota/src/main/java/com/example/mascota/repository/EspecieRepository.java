package com.example.mascota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mascota.model.Especie;

@Repository
public interface EspecieRepository extends JpaRepository<Especie,Long> {

}
