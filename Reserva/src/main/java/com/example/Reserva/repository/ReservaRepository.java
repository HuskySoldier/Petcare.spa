package com.example.Reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Reserva.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{

}
