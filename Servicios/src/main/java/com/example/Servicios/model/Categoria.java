package com.example.Servicios.model;

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
@Table(name="categoria") 
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(
    name = "Categoria",
    description = "Entidad que representa una categoría de servicios"
)
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
        description = "Identificador único de la categoría",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idCategoria;
    
    @NotBlank(message = "La categoria no puede estar vacío")
    @Column(nullable = false)
    @Schema(
        description = "Nombre de la categoría",
        example = "Estética",
        required = true
    )
    private String nombreCategoria;

    @OneToMany(mappedBy = "categoria",cascade= CascadeType.ALL)
    @JsonIgnore
    @Schema(
        description = "Lista de servicios asociados a la categoría"
    )
    List<Servicio> Servicio;
}
