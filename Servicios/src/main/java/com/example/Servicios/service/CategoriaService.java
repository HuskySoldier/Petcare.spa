package com.example.Servicios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Servicios.model.Categoria;
import com.example.Servicios.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    //para llamar todas las categorias
    public List<Categoria> listarCategoria() {
        return categoriaRepository.findAll();
    }

    //creacion para la categoria
    public Categoria crearCategoria(Categoria categoria) {
    //validacion para la creacion del nombre y este no pueda estar vacio
    if (categoria.getNombreCategoria() == null || !categoria.getNombreCategoria().matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
            throw new IllegalArgumentException("La categoria solo puede contener letras y espacios");
        }
    return categoriaRepository.save(categoria);
    } 

    // buscar una categoria por id
    public Optional<Categoria> obtenerCategoriaPorId(Long idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

}
