package com.petcare.clinica_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una clínica veterinaria.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "clinicas")
@Schema(
    name = "Clinica",
    description = "Entidad que representa una clínica veterinaria"
)
public class Clinica {

    /** Identificador único de la clínica. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la clínica", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    /** Nombre de la clínica. */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder 100 caracteres")
    @Schema(description = "Nombre de la clínica", example = "Clínica Central", required = true)
    private String nombre;

    /** Dirección de la clínica. */
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no debe exceder 255 caracteres")
    @Schema(description = "Dirección de la clínica", example = "Av. Siempre Viva 123", required = true)
    private String direccion;

    /** Comuna donde se ubica la clínica. */
    @NotBlank(message = "La comuna es obligatoria")
    @Size(max = 100, message = "La comuna no debe exceder 100 caracteres")
    @Schema(description = "Comuna donde se ubica la clínica", example = "Santiago", required = true)
    private String comuna;

    /** Capacidad de la clínica (como String). */
    @NotBlank(message = "La capacidad es obligatoria")
    @Schema(description = "Capacidad de la clínica", example = "20", required = true)
    private String capacidad;

    /**
     * Constructor útil para pruebas unitarias.
     * @param id Identificador
     * @param nombre Nombre
     * @param direccion Dirección
     * @param comuna Comuna
     * @param capacidad Capacidad (entero, se convierte a String)
     */
    public Clinica(Long id, String nombre, String direccion, String comuna, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.comuna = comuna;
        this.capacidad = String.valueOf(capacidad); // porque el campo es String
    }
}
