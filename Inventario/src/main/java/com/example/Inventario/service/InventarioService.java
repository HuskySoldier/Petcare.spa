package com.example.Inventario.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Inventario.client.ReporteClient;
import com.example.Inventario.client.UsuarioClient;
import com.example.Inventario.dto.ReporteDto;
import com.example.Inventario.dto.UsuarioDto;
import com.example.Inventario.enums.Rol;
import com.example.Inventario.model.Inventario;
import com.example.Inventario.repository.InventarioRepository;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ReporteClient reporteClient;

    @Autowired
    private UsuarioClient usuarioClient;

    // Crear nuevo inventario
    public Inventario crearInventario(Inventario inventario, Long idUsuario) {

        UsuarioDto usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);

        Rol rol = Rol.valueOf(usuario.getRol());

        if (rol != Rol.JEFE_INVENTARIO && rol != Rol.ADMINISTRADOR) {
            throw new RuntimeException("Acceso denegado: no tienes permisos sufientes para crear un inventario  ");
        }
        // Aquí llamas para verificar si existe un inventario con este nombre
        Optional<Inventario> inventarioExistente = inventarioRepository.findByNombreInv(inventario.getNombreInv());
        if (inventarioExistente.isPresent()) {
            throw new RuntimeException("Un inventario ya tiene este nombre. El número de id es: "
                    + inventarioExistente.get().getIdInventario());
        }
        // Aquí llamas para verificar si se debe crear un reporte
        if (inventario.getIdInventario() != null) {
            throw new IllegalArgumentException("No debe enviar el idInventario al crear un nuevo servicio.");
        }
        if (inventario.getNombreInv() == null || !inventario.getNombreInv().matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios");
        }
        if (inventario.getFechaUltimaActualizacion() == null) {
            inventario.setFechaUltimaActualizacion(new Date(System.currentTimeMillis())); // Fecha actual si no se manda
        }
        if (inventario.getStockActual() < 0) {
            throw new IllegalArgumentException("El stock actual no puede ser negativo.");
        }

        if (inventario.getStockMinimo() <= 0) {
            throw new IllegalArgumentException("El stock mínimo debe ser mayor que 0.");
        }

        Inventario inventarioGuardado = inventarioRepository.save(inventario);
        verificarYReportarStock(inventarioGuardado, idUsuario);
        return inventarioGuardado;
    }

    // Obtener toda la cantidad de productos en un inventario
    public List<Inventario> getInventario(int stockActual) {
        return (List<Inventario>) inventarioRepository.findByIdInventario(stockActual);
    }

    public List<Inventario> allInventario(Long idUsuario) {
        UsuarioDto usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
        Rol rol = Rol.valueOf(usuario.getRol());

        if (rol != Rol.JEFE_INVENTARIO && rol != Rol.ADMINISTRADOR) {
            throw new RuntimeException("Acceso denegado: no tienes permisos para actualizar inventario");
        }
        return inventarioRepository.findAll();
    }

    // Obtener un inventario por su ID
    public Optional<Inventario> obtenerInventarioPorId(Long idProducto, Long idUsuario) {
        UsuarioDto usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
        Rol rol = Rol.valueOf(usuario.getRol());

        if (rol != Rol.JEFE_INVENTARIO && rol != Rol.ADMINISTRADOR) {
            throw new RuntimeException("Acceso denegado: no tienes permisos para actualizar inventario");
        }
        return inventarioRepository.findById(idProducto);
    }

    // Actualizar el inventario
    public Inventario actualizarInventario(Long idInventario, Inventario inventarioActualizada, Long idUsuario) {
        UsuarioDto usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
        Rol rol = Rol.valueOf(usuario.getRol());

        if (rol != Rol.JEFE_INVENTARIO && rol != Rol.ADMINISTRADOR) {
            throw new RuntimeException("Acceso denegado: no tienes permisos para actualizar inventario");
        }
        if (inventarioRepository.existsById(idInventario)) {
            inventarioActualizada.setIdInventario(idInventario);
            return inventarioRepository.save(inventarioActualizada);
        } else {
            throw new RuntimeException("inventario no encontrado");
        }
    }

    // Borrar un inventario
    public Boolean deleteInventario(Long idProducto, Long idUsuario) {
        UsuarioDto usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
        Rol rol = Rol.valueOf(usuario.getRol());

        if (rol != Rol.JEFE_INVENTARIO && rol != Rol.ADMINISTRADOR) {
            throw new RuntimeException("Acceso denegado: no tienes permisos para actualizar inventario");
        }
        if (!inventarioRepository.existsById(idProducto)) {
            throw new RuntimeException("Inventario no encontrado");
        }
        inventarioRepository.deleteById(idProducto);
        return true;
    }

    public List<Inventario> obtenerInventarioStockBajo() {
        List<Inventario> inventarios = inventarioRepository.findAll();
        List<Inventario> inventarioStockBajo = new ArrayList<>();
        for (Inventario inv : inventarios) {
            if (inv.getStockActual() <= inv.getStockMinimo()) {
                inventarioStockBajo.add(inv);
            }
        }
        return inventarioStockBajo;
    }

    // Verificacion del reporte stock
    public void verificarYReportarStock(Inventario inventario, Long idUsuario) {
    if (inventario.getStockActual() <= inventario.getStockMinimo()) {
        ReporteDto reporteDto = new ReporteDto(
                inventario.getIdInventario(),
                "Stock bajo para el producto: " + inventario.getNombreInv(),
                new Date()
        );
        reporteClient.crearReporte(reporteDto, idUsuario);
    }
}


}
