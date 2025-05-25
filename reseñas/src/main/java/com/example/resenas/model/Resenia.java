package com.example.resenas.model;
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
@Table(name="resenas") 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Resenia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long idResenia;

    @Column(length = 100)
    private String comentario;

    @Column(nullable = false)
    private int calificacion;
    
    @Column(nullable = false)
    private Long servicioId;
}
