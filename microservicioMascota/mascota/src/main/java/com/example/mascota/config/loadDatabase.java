package com.example.mascota.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.mascota.model.Especie;
import com.example.mascota.model.Mascota;
import com.example.mascota.model.Raza;
import com.example.mascota.repository.especieRepository;
import com.example.mascota.repository.mascotaRepository;
import com.example.mascota.repository.razaRepository;

@Configuration
public class loadDatabase {

    @Bean
    CommandLineRunner initDatabase(razaRepository razaRepo, especieRepository especieRepo, mascotaRepository mascRepo){
        return args ->{
            //si no hay registros en la tablas
            if(razaRepo.count() == 0 && especieRepo.count() == 0){
                //insertar los raza de perro por defecto
                //raza de perro
                Raza PasAle= new Raza();
                PasAle.setNombreRaza("Pastor Alemán");
                razaRepo.save(PasAle);

                Raza bull= new Raza();
                bull.setNombreRaza("Bulldog");
                razaRepo.save(bull);

                Raza husSib= new Raza();
                husSib.setNombreRaza("Husky siberiano");
                razaRepo.save(husSib);

            // raza de gatos
                Raza persa= new Raza();
                persa.setNombreRaza("Gato persa");
                razaRepo.save(persa);

                Raza rag= new Raza();
                rag.setNombreRaza("Ragdoll");
                razaRepo.save(rag);

                Raza bom= new Raza();
                bom.setNombreRaza("Bombay");
                razaRepo.save(bom);

            // raza cuyo
                Raza ted= new Raza();
                ted.setNombreRaza("Cobaya Teddy");
                razaRepo.save(ted);

                Raza amer= new Raza();
                amer.setNombreRaza("Cuyo americano");
                razaRepo.save(amer);

                Raza rex= new Raza();
                rex.setNombreRaza("Cobaya rex");
                razaRepo.save(rex);

            // especies mascota
                Especie perro= new Especie();
                perro.setNombreEspecie("Perro");
                especieRepo.save(perro);

                Especie gato= new Especie();
                gato.setNombreEspecie("Gato");
                especieRepo.save(gato);

                Especie cuyo= new Especie();
                cuyo.setNombreEspecie("Cobaya");
                especieRepo.save(cuyo);
            
            

                //cargar dos usuarios por defecto (opcional)
                mascRepo.save(new Mascota(null, "Pichicha",2, "Hembra", persa,gato));
                mascRepo.save(new Mascota(null, "Yeremy",3,"Macho",bull,perro));
                mascRepo.save(new Mascota(null, "Toby", 1,"Macho",ted,cuyo ));
                System.out.println("Datos iniciales Cargados");
            }
            else{
                System.out.println("Datos ya existen. No se cargaron nuevos");
            }
        };
    }



}
