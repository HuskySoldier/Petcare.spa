package com.example.Cliente.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name="cliente") 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer cliente_id;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 100 ,nullable = false)
    private String apellido;

    @Column(length = 100)
    private String correo;

    @Column(length =  9, nullable = false )
    private int telefono;

    @Column(length = 100, nullable = false)
    private String direccion;
}
