
package com.sena.adso.crm.controller;

import com.sena.adso.crm.dto.VentaDTO;
import com.sena.adso.crm.entidades.Venta;
import com.sena.adso.crm.servicio.VentaServicio;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaControlador {

    private final VentaServicio ventaServicio;

    public VentaControlador(VentaServicio ventaServicio) {
        this.ventaServicio = ventaServicio;
    }

    // Listar todas las ventas
    @GetMapping
    public List<Venta> listarVentas() {
        return ventaServicio.listarVentas();
    }

    // Buscar una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscarVenta(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaServicio.buscarVenta(id));
    }

    // Listar ventas de un cliente
    @GetMapping("/cliente/{idCliente}")
    public List<Venta> listarVentasPorCliente(
            @PathVariable Integer idCliente) {
        return ventaServicio.listarVentasPorCliente(idCliente);
    }

    // Registrar una venta completa
    @PostMapping
    public ResponseEntity<Venta> registrarVenta(
            @RequestBody VentaDTO ventaDTO) {

        Venta ventaRegistrada = ventaServicio.registrarVenta(ventaDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ventaRegistrada);
    }
}
