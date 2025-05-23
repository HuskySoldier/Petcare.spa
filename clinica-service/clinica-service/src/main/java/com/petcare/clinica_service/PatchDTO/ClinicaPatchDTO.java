package com.petcare.clinica_service.PatchDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class ClinicaPatchDTO {

    @Size(min = 5, message = "La dirección debe tener al menos 5 caracteres")
    private String direccion;

    @Size(min = 5, message = "El contacto debe tener al menos 5 caracteres")
    private String contacto;

    @Min(value = 1, message = "La capacidad debe ser mayor que 0")
    private Integer capacidad;
}
