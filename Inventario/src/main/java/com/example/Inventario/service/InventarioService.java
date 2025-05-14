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
    public List<Inventario> getInventario(int stock_actual) {
        return (List<Inventario>) inventarioRepository.findByInventario(stock_actual);
    }
    

    // Obtener un inventario por su ID
    public Optional<Inventario> obtenerInventarioPorId(Long id_producto) {
        return inventarioRepository.findById(id_producto);
    }

    // Actualizar el inventario
    public Inventario actualizarInventario(Long id_producto, Inventario inventarioActualizada) {
        if (inventarioRepository.existsById(id_producto)) {
            inventarioActualizada.setId_inventario(id_producto);
            return inventarioRepository.save(inventarioActualizada);
        } else {
            throw new RuntimeException("inventario no encontrado");
        }
    }

    // Borrar un inventario
    public Boolean deleteInventario(Long id_producto) { 
    if (!inventarioRepository.existsById(id_producto)) {
        throw new RuntimeException("Inventario no encontrado");
    }
    inventarioRepository.deleteById(id_producto);
    return true;
    }
    
}
