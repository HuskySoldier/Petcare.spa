package com.example.HistorialMedico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HistorialMedico.client.UsuarioClient;
import com.example.HistorialMedico.dto.UsuarioDTO;
import com.example.HistorialMedico.enums.Rol;
import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.repository.HistorialMedicoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class HistorialMedicoService {
    @Autowired
    private HistorialMedicoRepository historialmedicorepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public List<HistorialMedico> listarHistorialMedico() {
        return historialmedicorepository.findAll();
    }

    public HistorialMedico buscarHistorialMedicoPorId(Long id) {
        return historialmedicorepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
    }

    public HistorialMedico agregarHistorialMedico(HistorialMedico historialmedico, Long idUsuario) {
        UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
        Rol rol = Rol.valueOf(usuario.getRol()); 
        if (rol != Rol.JEFE_INVENTARIO && rol != Rol.ADMINISTRADOR) {
            throw new RuntimeException(
                    "Acceso denegado: no tienes permisos suficientes para crear un historial médico.");
        }

        return historialmedicorepository.save(historialmedico);
    }

    public void eliminarHistorialMedico(Long id) {
        historialmedicorepository.deleteById(id);
    }

    public List<HistorialMedico> buscarPorIdMascota(Long idMascota) {
        return historialmedicorepository.findByIdMascota(idMascota);
    }

    public HistorialMedico modificarHistorialMedico(Long id, HistorialMedico actualizado, Long idUsuario) {
        UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
        Rol rol = Rol.valueOf(usuario.getRol());

        if (rol != Rol.JEFE_INVENTARIO && rol != Rol.ADMINISTRADOR) {
            throw new RuntimeException("Acceso denegado: no tienes permisos para modificar un historial médico.");
        }

        HistorialMedico hismed = historialmedicorepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));

        hismed.setFechaRegistro(actualizado.getFechaRegistro());
        hismed.setAntecedentes(actualizado.getAntecedentes());
        hismed.setComentario(actualizado.getComentario());
        hismed.setIdMascota(actualizado.getIdMascota());
        hismed.setDiagnostico(actualizado.getDiagnostico());

        return historialmedicorepository.save(hismed);
    }

}
