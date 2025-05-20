package com.example.veterinario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "veterinario")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long veterinario_id;

    @Column(nullable = false)
    private Integer rut;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column (nullable = false)
    private String especialidad;

    @Column(nullable = false)
    private String correo;



}
