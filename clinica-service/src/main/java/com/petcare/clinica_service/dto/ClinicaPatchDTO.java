package com.petcare.clinica_service.dto;

import lombok.Data;

@Data
public class ClinicaPatchDTO {
    private String direccion;
    private String contacto;
    private Integer capacidad;
}
