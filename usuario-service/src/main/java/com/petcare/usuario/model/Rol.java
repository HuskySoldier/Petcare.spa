package com.petcare.usuario.model;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Rol {
    @Schema(description = "Rol de usuario: CLIENTE", example = "CLIENTE")
    CLIENTE,
    @Schema(description = "Rol de usuario: VETERINARIO", example = "VETERINARIO")
    VETERINARIO,
    @Schema(description = "Rol de usuario: ADMINISTRADOR", example = "ADMINISTRADOR")
    ADMINISTRADOR,
    @Schema(description = "Rol de usuario: JEFE_INVENTARIO", example = "JEFE_INVENTARIO")
    JEFE_INVENTARIO,
    @Schema(description = "Rol de usuario: JEFE_CLINICA", example = "JEFE_CLINICA")
    JEFE_CLINICA
}
