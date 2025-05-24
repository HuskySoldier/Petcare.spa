package com.example.Servicios.model;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name="servicio") 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long idServicio;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(nullable = false) 
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar vacio")
    @Column(nullable = false) 
    private String descripcion;

    @NotBlank(message = "El precio no puede estar vacio")
    @Column(nullable = false) 
    private int precio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;
    

}
