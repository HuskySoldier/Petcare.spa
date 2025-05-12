package com.petcare.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$", message = "El nombre no debe contener números ni caracteres especiales")
    private String nombre;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$", message = "El apellido no debe contener números ni caracteres especiales")
    private String apellido;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo debe contener números")
    private String telefono;

    @NotBlank
    private String password;
}
