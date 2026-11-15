package com.sena.adso.crm.controller;

import com.sena.adso.crm.entidades.Proveedor;
import com.sena.adso.crm.servicio.ProveedorServicio;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")
public class ProveedorControlador {

    private final ProveedorServicio proveedorServicio;

    public ProveedorControlador(ProveedorServicio proveedorServicio) {
        this.proveedorServicio = proveedorServicio;
    }

    // Listar proveedores
    @GetMapping
    public List<Proveedor> listarProveedores() {
        return proveedorServicio.listarProveedores();
    }

    // Buscar proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> buscarProveedor(@PathVariable Integer id) {

        Proveedor proveedor = proveedorServicio.buscarProveedor(id);

        if (proveedor == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(proveedor);
    }

    // Registrar proveedor
    @PostMapping
    public ResponseEntity<Proveedor> crearProveedor(@RequestBody Proveedor proveedor) {

        proveedor.setIdProveedor(null);

        Proveedor guardado = proveedorServicio.guardarProveedor(proveedor);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // Actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(
            @PathVariable Integer id,
            @RequestBody Proveedor datos) {

        Proveedor actualizado = proveedorServicio.actualizarProveedor(id, datos);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }

    // Eliminar proveedor (eliminación lógica)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Integer id) {

        if (proveedorServicio.buscarProveedor(id) == null) {
            return ResponseEntity.notFound().build();
        }

        proveedorServicio.eliminarProveedor(id);

        return ResponseEntity.noContent().build();
    }
}