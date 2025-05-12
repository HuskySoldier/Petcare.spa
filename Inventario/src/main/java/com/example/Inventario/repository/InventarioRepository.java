package com.example.Inventario.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Inventario.model.Inventario;
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long>{


}
