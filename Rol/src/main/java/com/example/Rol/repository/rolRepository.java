package com.example.Rol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Rol.model.Rol;
@Repository
public interface rolRepository extends JpaRepository<Rol,Long> {
   List<Rol> findByIdRol(int idRol);
}
