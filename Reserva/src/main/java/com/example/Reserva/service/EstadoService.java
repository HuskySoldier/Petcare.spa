package com.example.Reserva.service;

import com.example.Reserva.model.Estado;
import com.example.Reserva.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService{

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> listarEstados() {
        return estadoRepository.findAll();
    }

    public Estado crearEstado(Estado estado) {
        if (estado.getEstadoId() != null) {
            throw new IllegalArgumentException("No debe enviar el estadoId al crear un nuevo estado.");
        }
        return estadoRepository.save(estado);
    }

    public Estado buscarPorId(Long id) {
        return estadoRepository.findById(id).orElse(null);
    }
}
