package com.example.Servicios.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Servicios.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
