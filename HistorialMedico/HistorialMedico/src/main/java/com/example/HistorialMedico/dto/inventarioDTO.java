package com.example.HistorialMedico.dto;

import lombok.Data;


@Data
public class inventarioDTO {
    private Long idInventario;
    private int idProducto;
    private String nombreInv;
    private int stockActual;
    private int stockMinimo;


}
