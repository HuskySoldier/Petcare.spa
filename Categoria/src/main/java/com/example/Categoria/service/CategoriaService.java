package com.example.Categoria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Categoria.model.Categoria;
import com.example.Categoria.repository.CategoriaRepository;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> allCategoria() {
        return categoriaRepository.findAll();
    }

    public Categoria actualizarCategoria(Long idCategoria, Categoria categoriaActualizada) {
        if (categoriaRepository.existsById(idCategoria)) {
            categoriaActualizada.setIdCategoria(idCategoria);
            return categoriaRepository.save(categoriaActualizada);
        } else {
            throw new RuntimeException("Categoria no encontrado");
        }
    }

    // Borrar un Servicio
    public Boolean deleteCategoria(Long idCategoria) {
        if (!categoriaRepository.existsById(idCategoria)) {
            throw new RuntimeException("Categoria no encontrado");
        }
        categoriaRepository.deleteById(idCategoria);
        return true;
    }

}
