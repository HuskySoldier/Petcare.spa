package com.example.Reserva.service;

import com.example.Reserva.model.Estado;
import com.example.Reserva.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService{

    @Autowired
    private EstadoRepository EstadoRepository;

    public List<Estado> listarEstados() {
        return EstadoRepository.findAll();
    }

    public Estado crearEstado(Estado estado) {
        return EstadoRepository.save(estado);
    }

    public Estado buscarPorId(Long id) {
        return EstadoRepository.findById(id).orElse(null);
    }
}
