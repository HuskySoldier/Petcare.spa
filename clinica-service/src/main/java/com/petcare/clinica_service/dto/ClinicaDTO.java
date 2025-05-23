package com.petcare.clinica_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClinicaDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String direccion;

    @NotBlank(message = "El contacto es obligatorio")
    @Size(min = 9, max = 9, message = "El contacto debe tener exactamente 9 caracteres")
    private String contacto;

    @NotNull(message = "La capacidad es obligatoria")
    private Integer capacidad;
}

