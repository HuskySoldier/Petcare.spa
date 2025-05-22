package com.example.mascota.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "El nombre es obligatorio")
    @Column(length = 100, nullable= false)
    private String nombre;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Column(length = 4, nullable= false)
    private int edad;

    @NotBlank(message = "El sexo es obligatorio")
    @Column(length = 100, nullable= false)
    private String sexo;

    @ManyToOne
    @JoinColumn(name = "idRaza")
    @JsonIgnoreProperties("mascota")
    private Raza raza;

    @ManyToOne
    @JoinColumn(name = "idEspecie")
    @JsonIgnoreProperties("mascota")
    private Especie especie;


}
