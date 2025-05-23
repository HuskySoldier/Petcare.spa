package com.petcare.clinica_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "clinicas")
public class Clinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder 100 caracteres")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no debe exceder 255 caracteres")
    private String direccion;

    @NotBlank(message = "La comuna es obligatoria")
    @Size(max = 100, message = "La comuna no debe exceder 100 caracteres")
    private String comuna;

    @NotBlank(message = "La capacidad es obligatoria")
    private String capacidad;
}
