package com.example.Servicios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name="servicio") 
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(
    name = "Servicio",
    description = "Entidad que representa un servicio ofrecido por la veterinaria"
)
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Schema(
        description = "Identificador único del servicio",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idServicio;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(nullable = false) 
    @Schema(
        description = "Nombre del servicio",
        example = "Baño y corte de pelo",
        required = true
    )
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar vacio")
    @Column(nullable = false) 
    @Schema(
        description = "Descripción detallada del servicio",
        example = "Incluye baño, corte de pelo y limpieza de oídos",
        required = true
    )
    private String descripcion;

    @NotNull(message = "El precio no puede estar vacio")
    @Column(nullable = false) 
    @Schema(
        description = "Precio del servicio en CLP",
        example = "15000",
        required = true
    )
    private int precio;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idCategoria")
    @Schema(
        description = "Categoría a la que pertenece el servicio"
    )
    private Categoria categoria;
}
