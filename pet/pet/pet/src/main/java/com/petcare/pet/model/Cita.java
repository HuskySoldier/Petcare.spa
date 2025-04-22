package com.petcare.pet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mascota;
    private String cliente;
    private String veterinario;

    @Column(nullable = false)
    private LocalDateTime fecha;

    private String estado; // Ej. "Programada", "Cancelada", "Completada"

    public Cita() {}

    public Cita(String mascota, String cliente, String veterinario, LocalDateTime fecha, String estado) {
        this.mascota = mascota;
        this.cliente = cliente;
        this.veterinario = veterinario;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Getters y setters

   
}
