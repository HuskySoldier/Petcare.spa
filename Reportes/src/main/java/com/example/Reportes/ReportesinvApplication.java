package com.example.Reportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.Reportes.config")
public class ReportesinvApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportesinvApplication.class, args);
	}

}

//http://localhost:8088/swagger-ui/index.html#/
 
/*http://localhost:8088/api/v1/reporte
 * SE TIENE QUE AGREGAR AL HEADERS X-USER-ID y el numero el id del usuario
 * {
        "comentario": "Stock bajo para el producto: lukaa",
        "fechaCreacion": "2025-05-22"
    }

 */