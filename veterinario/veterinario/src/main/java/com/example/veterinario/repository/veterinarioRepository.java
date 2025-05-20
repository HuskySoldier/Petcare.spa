package com.example.veterinario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.veterinario.model.Veterinario;

@Repository
public interface veterinarioRepository extends JpaRepository<Veterinario, Long>{

}
