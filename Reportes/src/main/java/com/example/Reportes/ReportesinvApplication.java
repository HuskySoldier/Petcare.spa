package com.example.Reportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.Reporte.client")
public class ReportesinvApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportesinvApplication.class, args);
	}

}
