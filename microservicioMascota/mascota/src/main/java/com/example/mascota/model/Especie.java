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
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especies")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(
    name = "Especie",
    description = "Entidad que representa una especie de mascota"
)
public class Especie {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la especie", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idEspecie;
    
    @NotBlank(message = "El nombre de la especie es obligatorio")
    @Column(length =50 , nullable =  false)
    @Schema(description = "Nombre de la especie", example = "Canino", required = true)
    private String nombreEspecie;


    @OneToMany(mappedBy = "especie",cascade= CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Lista de mascotas asociadas a la especie")
    List<Mascota> mascota;

}
