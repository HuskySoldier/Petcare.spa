package com.example.Inventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Inventario.model.Inventario;
import com.example.Inventario.repository.InventarioRepository;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;
       
    // Crear nuevo inventario
    public Inventario crearInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }


    // Obtener toda la cantidad de productos en un inventario
    public List<Inventario> getInventario(int stockActual) {
        return (List<Inventario>) inventarioRepository.findByIdInventario(stockActual);
    }
    
    public List<Inventario> allInventario(){
        return inventarioRepository.findAll();
    }

    // Obtener un inventario por su ID
    public Optional<Inventario> obtenerInventarioPorId(Long idProducto) {
        return inventarioRepository.findById(idProducto);
    }

    // Actualizar el inventario
    public Inventario actualizarInventario(Long idInventario, Inventario inventarioActualizada) {
        if (inventarioRepository.existsById(idInventario)) {
            inventarioActualizada.setIdInventario(idInventario);
            return inventarioRepository.save(inventarioActualizada);
        } else {
            throw new RuntimeException("inventario no encontrado");
        }
    }

    // Borrar un inventario
    public Boolean deleteInventario(Long idProducto) { 
    if (!inventarioRepository.existsById(idProducto)) {
        throw new RuntimeException("Inventario no encontrado");
    }
    inventarioRepository.deleteById(idProducto);
    return true;
    }

}
