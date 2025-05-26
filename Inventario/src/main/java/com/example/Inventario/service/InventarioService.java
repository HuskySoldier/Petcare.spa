package com.example.Inventario.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Inventario.client.ReporteClient;
import com.example.Inventario.dto.ReporteDto;
import com.example.Inventario.model.Inventario;
import com.example.Inventario.repository.InventarioRepository;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ReporteClient reporteClient;

    // Crear nuevo inventario
    public Inventario crearInventario(Inventario inventario) {
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
        verificarYReportarStock(inventarioGuardado);
        return inventarioGuardado;
    }

    // Obtener toda la cantidad de productos en un inventario
    public List<Inventario> getInventario(int stockActual) {
        return (List<Inventario>) inventarioRepository.findByIdInventario(stockActual);
    }

    public List<Inventario> allInventario() {
        return inventarioRepository.findAll();
    }

    // Obtener un inventario por su ID
    public Optional<Inventario> obtenerInventarioPorId(Long idProducto) {
        return inventarioRepository.findById(idProducto);
    }

    // Actualizar el inventario
    public Inventario actualizarInventario(Long idInventario, Inventario inventarioActualizada) {
        if (inventarioRepository.existsById(idInventario)) {
            inventarioActualizada.setIdInventario(idInventario);
            return inventarioRepository.save(inventarioActualizada);
        } else {
            throw new RuntimeException("inventario no encontrado");
        }
    }

    // Borrar un inventario
    public Boolean deleteInventario(Long idProducto) {
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
    public void verificarYReportarStock(Inventario inventario) {
        if (inventario.getStockActual() <= inventario.getStockMinimo()) {
            ReporteDto reporteDto = new ReporteDto(
                    inventario.getIdProducto(),
                    "Stock bajo para el producto: " + inventario.getNombreInv(),
                    new Date(System.currentTimeMillis()));

            reporteClient.crearReporte(reporteDto);
        }
    }
}
