
package com.sena.adso.crm.servicio;

import com.sena.adso.crm.entidades.DetalleVenta;
import com.sena.adso.crm.repository.DetalleVentaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaServicio {

    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaServicio(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    // Listar todos los detalles de venta
    public List<DetalleVenta> listarDetalles() {
        return detalleVentaRepository.findAll();
    }

    // Buscar los detalles de una venta
    public List<DetalleVenta> listarDetallesPorVenta(Integer idVenta) {
        return detalleVentaRepository.findByIdVenta(idVenta);
    }

    // Buscar los detalles asociados a un producto
    public List<DetalleVenta> listarDetallesPorProducto(Integer idProducto) {
        return detalleVentaRepository.findByIdProducto(idProducto);
    }
}
