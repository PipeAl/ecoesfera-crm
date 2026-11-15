package com.sena.adso.crm.controller;

import com.sena.adso.crm.entidades.Rol;
import com.sena.adso.crm.servicio.RolServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolControlador {

    private final RolServicio rolServicio;

    public RolControlador(RolServicio rolServicio) {
        this.rolServicio = rolServicio;
    }

    // Listar roles activos
    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(rolServicio.listarRoles());
    }

    // Buscar rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<Rol> buscarRol(@PathVariable Integer id) {

        Rol rol = rolServicio.buscarRol(id);

        if (rol == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rol);
    }

    // Registrar rol
    @PostMapping
    public ResponseEntity<?> registrarRol(@RequestBody Rol rol) {

        try {
            Rol nuevoRol = rolServicio.registrarRol(rol);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRol);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Actualizar rol
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRol(
            @PathVariable Integer id,
            @RequestBody Rol datos) {

        try {
            Rol rolActualizado = rolServicio.actualizarRol(id, datos);

            if (rolActualizado == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(rolActualizado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Eliminación lógica
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Integer id) {

        boolean eliminado = rolServicio.eliminarRol(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}