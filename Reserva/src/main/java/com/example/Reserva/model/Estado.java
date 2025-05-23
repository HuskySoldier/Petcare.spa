package com.example.Reserva.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "estado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estadoId;

    @NotBlank(message = "El nombre del estado no puede estar vacío")
    @Column(nullable = false)
    private String nombreEstado;
    
    @Column(nullable = false, length = 100)
    private String descripcion;

    @OneToMany(mappedBy = "estado",cascade= CascadeType.ALL)
    @JsonIgnore
    List<Reserva> reserva;

}
