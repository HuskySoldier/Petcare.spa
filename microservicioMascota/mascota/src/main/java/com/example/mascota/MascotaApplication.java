package com.example.mascota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.mascota.client")
public class MascotaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MascotaApplication.class, args);
	}

}
