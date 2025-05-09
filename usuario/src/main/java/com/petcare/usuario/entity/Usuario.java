package com.petcare.usuario.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String rol; // ej: "cliente", "veterinario", "admin"

    @Email(message = "El correo debe tener un formato válido")
    @NotBlank(message = "Correo es obligatorio")
    @Column(unique = true)
    private String correo;

    @NotBlank(message = "Contraseña es obligatoria")
    private String contraseña;
}
