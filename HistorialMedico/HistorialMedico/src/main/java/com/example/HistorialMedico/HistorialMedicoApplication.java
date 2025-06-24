package com.example.HistorialMedico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.HistorialMedico.client")
public class HistorialMedicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistorialMedicoApplication.class, args);
	}

	// Para acceder a la documentación Swagger, visita:
	// http://localhost:8086/swagger-ui.html
	//HEADERS X-USER-ID y NUMERO VETERINARIO 3
	// http://localhost:8086/api/v1/historialmedico
	/*
	 * {
	 * "historialId": 13,
	 * "fechaRegistro": "2025-06-23",
	 * "antecedentes": "No alergias",
	 * "comentario": "Paciente estable",
	 * "idMascota": 1,
	 * "diagnostico": "Gripe leve"
	 * }
	 */

}
