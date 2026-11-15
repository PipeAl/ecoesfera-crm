
package com.sena.adso.crm.controller;

import com.sena.adso.crm.entidades.DetalleVenta;
import com.sena.adso.crm.servicio.DetalleVentaServicio;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-venta")
@CrossOrigin(origins = "*")
public class DetalleVentaControlador {

    private final DetalleVentaServicio detalleVentaServicio;

    public DetalleVentaControlador(DetalleVentaServicio detalleVentaServicio) {
        this.detalleVentaServicio = detalleVentaServicio;
    }

    // Listar todos los detalles
    @GetMapping
    public List<DetalleVenta> listarDetalles() {
        return detalleVentaServicio.listarDetalles();
    }

    // Consultar detalles por venta
    @GetMapping("/venta/{idVenta}")
    public List<DetalleVenta> listarDetallesPorVenta(
            @PathVariable Integer idVenta) {
        return detalleVentaServicio.listarDetallesPorVenta(idVenta);
    }

    // Consultar detalles por producto
    @GetMapping("/producto/{idProducto}")
    public List<DetalleVenta> listarDetallesPorProducto(
            @PathVariable Integer idProducto) {
        return detalleVentaServicio.listarDetallesPorProducto(idProducto);
    }
}
