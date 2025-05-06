package com.example.Rol.model;

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
@Table(name="rol") 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer rol_id;
    @Column(length = 100, nullable = false)
    private String nombre_rol;
}
