package com.example.Reserva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.Reserva.client")
public class ReservaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservaApplication.class, args);
	}

}
