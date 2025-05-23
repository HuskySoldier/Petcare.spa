package com.example.Reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Reserva.model.Estado;

@Repository
public interface estadoRepository extends JpaRepository<Estado, Long> {

}
