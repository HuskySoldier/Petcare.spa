package com.example.HistorialMedico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.HistorialMedico.client.InventarioClient;
import com.example.HistorialMedico.client.UsuarioClient;
import com.example.HistorialMedico.dto.UsuarioDTO;
import com.example.HistorialMedico.enums.Rol;
import com.example.HistorialMedico.model.Tratamiento;
import com.example.HistorialMedico.repository.TratamientoRepository;

@Service
public class TratamientoService {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private InventarioClient Inventario;

    @Autowired
    UsuarioClient usuarioClient;

    public List<Tratamiento> listarTratamientos() {
        return tratamientoRepository.findAll();
    }

    public Tratamiento guardarTratamiento(Tratamiento tratamiento, Long usuarioId) {
        UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorId(usuarioId);

        Rol rol;
        try {
            rol = Rol.valueOf(usuario.getRol());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Acceso denegado: rol inválido");
        }

        if (rol != Rol.VETERINARIO && rol != Rol.ADMINISTRADOR && rol != Rol.JEFE_CLINICA) {
            throw new RuntimeException("Acceso denegado: no tienes permisos para realizar esta acción.");
        }

        // resto del código para guardar tratamiento
        return tratamientoRepository.save(tratamiento);
    }

}
