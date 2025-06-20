package com.petcare.clinica_service.dto;

import lombok.Data;

/**
 * DTO para la actualización parcial de una clínica.
 */
@Data
public class ClinicaPatchDTO {
    /** Nueva dirección de la clínica. */
    private String direccion;
    /** Nuevo contacto de la clínica. */
    private String contacto;
    /** Nueva capacidad de la clínica. */
    private Integer capacidad;
}
