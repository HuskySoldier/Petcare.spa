package com.example.resenas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.resenas.model.Resenia;

@Repository
public interface ReseniaRepository extends JpaRepository<Resenia, Long>{
   List<Resenia> findByIdResenia(int idResenia);
}
