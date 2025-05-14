package com.example.mascota.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especies")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Especie {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private Long idEspecie;

    @Column(length =50 , nullable =  false)
    private String nombreEspecie;


    @OneToMany(mappedBy = "especie",cascade= CascadeType.ALL)
    @JsonIgnore
    List<Mascota> mascota;

}
