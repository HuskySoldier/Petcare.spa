package com.petcare.register_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String mensaje;
    private String email;
    private String rol;
}
