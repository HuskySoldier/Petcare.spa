package com.example.Reportes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Reportes.config.UsuarioClient;
import com.example.Reportes.dto.UsuarioDto;
import com.example.Reportes.enums.Rol;
import com.example.Reportes.model.Reporte;
import com.example.Reportes.repository.ReporteRepository;


@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private UsuarioClient usuarioClient;
       
    // Crear nuevo reporte
    public Reporte crearReporte(Reporte reporte,Long idUsuario) {
        UsuarioDto usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
        Rol rol = Rol.valueOf(usuario.getRol());

        if (rol != Rol.JEFE_INVENTARIO && rol != Rol.ADMINISTRADOR) {
            throw new RuntimeException("Acceso denegado: no tienes permisos para crear un Reporte");
        }
        return reporteRepository.save(reporte);
    }

    public Optional<Reporte> obtenerReportePorId(Long idReporte) {
        return reporteRepository.findById(idReporte);
    }

    public List<Reporte> allReporte(Long idUsuario){
        UsuarioDto usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
        Rol rol = Rol.valueOf(usuario.getRol());

        if (rol != Rol.JEFE_INVENTARIO && rol != Rol.ADMINISTRADOR) {
            throw new RuntimeException("Acceso denegado: no tienes permisos para obtener un reporte");
        }
    return reporteRepository.findAll();
    }



    // Actualizar el reporte
    public Reporte actualizarReporte(Long idReporte, Reporte reporteActualizada, Long idUsuario) {

        UsuarioDto usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
        Rol rol = Rol.valueOf(usuario.getRol());

        if (rol != Rol.JEFE_INVENTARIO && rol != Rol.ADMINISTRADOR) {
            throw new RuntimeException("Acceso denegado: no tienes permisos para actualizar reporte");
        }
        if (reporteRepository.existsById(idReporte)) {
            reporteActualizada.setIdReporte(idReporte);
            return reporteRepository.save(reporteActualizada);
        } else {
            throw new RuntimeException("reporte no encontrado");
        }
    }
    
}