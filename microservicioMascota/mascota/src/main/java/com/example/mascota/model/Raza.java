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
@Table(name = "razaMascota")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Raza {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idRaza;

    @Column(length = 100, nullable= false)
    private String nombreRaza;

}
