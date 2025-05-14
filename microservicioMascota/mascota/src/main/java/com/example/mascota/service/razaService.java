package com.example.mascota.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mascota.repository.razaRepository;

@Service
public class razaService {
    @Autowired
    private razaRepository razarepository;

    public List<Raza>listarMacotas;


}
