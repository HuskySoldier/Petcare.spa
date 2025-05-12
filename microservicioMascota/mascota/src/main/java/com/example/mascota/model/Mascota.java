package com.example.mascota.model;

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
@Table(name = "mascota")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdMascota;

    @Column(length = 100, nullable= false)
    private String nombre;

    @Column(length = 100, nullable = false)
    private String especie;

    @Column(length = 100, nullable = false)
    private String raza;

    @Column(length = 4, nullable= false)
    private int edad;

    @Column(length = 100, nullable= false)
    private String sexo;


}
