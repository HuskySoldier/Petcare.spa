package com.example.mascota.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.mascota.model.Raza;
import com.example.mascota.repository.razaRepository;

@Configuration
public class loadDatabase {

    @Bean
    CommandLineRunner initDatabase(razaRepository razaRepo){
        return args ->{
            //si no hay registros en la tablas
            if(razaRepo.count() == 0){
                //insertar los roles por defecto
                Raza PasAle= new Raza();
                PasAle.setNombreRaza("Pastor Alemán");
                razaRepo.save(PasAle);

                Raza bull= new Raza();
                bull.setNombreRaza("Bulldog");
                razaRepo.save(bull);

                Raza PasAle= new Raza();
                PasAle.setNombreRaza("Pastor Alemán");
                razaRepo.save(PasAle);

                //cargar dos usuarios por defecto (opcional)
                userRepo.save(new Usuario(null, "vicros","123456",admin));
                userRepo.save(new Usuario(null, "camiuo","1234",usuario));
                System.out.println("Datos iniciales Cargados");
            }
            else{
                System.out.println("Datos ya existen. No se cargaron nuevos");
            }
        };
    }



}
