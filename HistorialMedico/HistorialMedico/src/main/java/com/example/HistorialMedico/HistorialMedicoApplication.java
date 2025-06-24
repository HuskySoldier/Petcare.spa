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
}
