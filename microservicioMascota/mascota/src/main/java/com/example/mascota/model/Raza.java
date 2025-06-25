package com.example.mascota.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "razaMascota")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
    name = "Raza",
    description = "Entidad que representa una raza de mascota"
)
public class Raza {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la raza", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idRaza;

    @Column(length = 100, nullable= false)
    @Schema(description = "Nombre de la raza", example = "Labrador", required = true)
    private String nombreRaza;

    @OneToMany(mappedBy = "raza",cascade= CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Lista de mascotas asociadas a la raza")
    List<Mascota> mascota;
}
