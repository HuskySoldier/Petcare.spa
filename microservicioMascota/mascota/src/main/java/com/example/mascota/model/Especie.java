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
public class Especie {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private Long IdEspecie;

    @Column(length =50 , nullable =  false)
    private String nombreEspecie;
    

}
