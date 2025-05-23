package com.petcare.clinica_service.PatchDTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClinicaDTO {

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, message = "La dirección debe tener al menos 5 caracteres")
    private String direccion;

    @NotBlank(message = "El contacto es obligatorio")
    @Size(min = 5, message = "El contacto debe tener al menos 5 caracteres")
    private String contacto;

    @NotNull(message = "La capacidad es obligatoria")
    private Integer capacidad;
}
