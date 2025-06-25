package com.example.mascota.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mascota")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(
    name = "Mascota",
    description = "Entidad que representa una mascota"
)
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la mascota", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long IdMascota;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(length = 100, nullable = false)
    @Schema(description = "Nombre de la mascota", example = "Firulais", required = true)
    private String nombre;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Column(nullable = false)
    @Schema(description = "Edad de la mascota", example = "3", required = true)
    private int edad;

    @NotBlank(message = "El sexo es obligatorio")
    @Column(length = 100, nullable = false)
    @Schema(description = "Sexo de la mascota", example = "Macho", required = true)
    private String sexo;

    @Column(nullable = false)
    @Schema(description = "Identificador del usuario dueño de la mascota", example = "5", required = true)
    private Long idUsuario;


    @ManyToOne
    @JoinColumn(name = "idRaza")
    @JsonIgnoreProperties("mascota")
    @Schema(description = "Raza de la mascota")
    private Raza raza;

    @ManyToOne
    @JoinColumn(name = "idEspecie")
    @JsonIgnoreProperties("mascota")
    @Schema(description = "Especie de la mascota")
    private Especie especie;

}
