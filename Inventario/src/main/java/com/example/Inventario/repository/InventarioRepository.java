package com.example.Inventario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Inventario.model.Inventario;
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long>{
   List<Inventario> findByIdInventario(int idInventario);

   Optional<Inventario> findByNombreInv(String nombreInv);
}
