package com.example.Servicios.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Servicios.model.Categoria;
import com.example.Servicios.model.Servicio;
import com.example.Servicios.repository.CategoriaRepository;
import com.example.Servicios.repository.ServicioRepository;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDatabase(CategoriaRepository categoriaRepository, ServicioRepository servicioRepository){
        return args ->{
            //si no hay registros en la tablas
            if(categoriaRepository.count() == 0 ){
                //insertar la categoria defecto
            //Tipo Categoria
                Categoria banio= new Categoria();
                banio.setNombre("Baño");
                categoriaRepository.save(banio);

                Categoria unias= new Categoria();
                unias.setNombre("cortas uñas");
                categoriaRepository.save(unias);

                Categoria pelo= new Categoria();
                pelo.setNombre("Limpieza de pelo");
                categoriaRepository.save(pelo);;

                //cargar dos usuarios por defecto (opcional)
                servicioRepository.save(new Servicio(null, "Baño","Limpieza de cuerpo completo",9900, banio));
                servicioRepository.save(new Servicio(null, "Uñas","Cortar uñas",15990,unias));
                servicioRepository.save(new Servicio(null, "Pelo", "Cortar el pelo ",12990,pelo ));
                System.out.println("Datos iniciales Cargados");
            }
            else{
                System.out.println("Datos ya existen. No se cargaron nuevos");
            }
        };
    }
    
}
