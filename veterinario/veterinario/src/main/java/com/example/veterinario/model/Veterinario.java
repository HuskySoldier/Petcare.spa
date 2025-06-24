package com.example.veterinario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private Long veterinarioId;

    @Min(value = 1000000, message = "El RUT debe tener al menos 7 dígitos")
    @Column(nullable = false)
    private Integer rut;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Column(nullable = false)
    private String apellido;

    @NotBlank(message = "La especialidad no puede estar vacía")
    @Column(nullable = false)
    private String especialidad;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe ser válido")
    @Column(nullable = false)
    private String correo;

    @NotNull(message = "El usuarioId no puede ser nulo")
    @Column(nullable = false)
    private Long usuarioId;
}

