package com.example.mascota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mascota.model.Mascota;

@Repository
public interface mascotaRepository extends JpaRepository<Mascota,Long>{
    //@Query(SELECT m FROM Mascota WHERE m.nombre= : nombre) -- busca a todas las mascotas con el mismo nombre

    //@Query(value = "SELECT * FROM mascota WHERE IdMascota= IdMascota",nativeQuery = true)
    //Mascota buscarMascotaId(@Param("IdMascota") String IdMascota );


}
