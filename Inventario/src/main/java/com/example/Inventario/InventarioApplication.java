package com.example.Inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.Inventario.client")
public class InventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioApplication.class, args);
	}

}
//http://localhost:8087/swagger-ui/index.html#/
/*
 * http://localhost:8087/api/v1/inventario
 SE TIENE QUE AGREGAR AL HEADERS X-USER-ID y el numero el id del usuario
{
    "nombreInv": "harina",
    "stockActual": 4,
    "stockMinimo": 5,
    "fechaUltimaActualizacion": "2025-05-11"
}
 * 
*/