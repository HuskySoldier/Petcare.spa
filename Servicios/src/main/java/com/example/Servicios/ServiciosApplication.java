package com.example.Servicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.Servicio.client")
public class ServiciosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiciosApplication.class, args);
	}

}
//http://localhost:8090/swagger-ui/index.html#/
/*http://localhost:8090/api/v1/servicio
 * 
 * {
        "nombre": "asd",
        "descripcion": "123",
        "precio": 123,
        "categoria": {
            "nombreCategoria": "asd"
        }
    }
 */