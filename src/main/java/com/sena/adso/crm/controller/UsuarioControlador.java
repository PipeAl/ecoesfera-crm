package com.sena.adso.crm.controller;

import com.sena.adso.crm.entidades.Usuario;
import com.sena.adso.crm.servicio.UsuarioServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // Listar usuarios activos
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioServicio.listarUsuarios());
    }

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Integer id) {

        Usuario usuario = usuarioServicio.buscarUsuario(id);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    // Registrar usuario
    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {

        try {
            Usuario nuevoUsuario = usuarioServicio.registrarUsuario(usuario);

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody Usuario datos) {

        try {
            Usuario usuarioActualizado =
                    usuarioServicio.actualizarUsuario(id, datos);

            if (usuarioActualizado == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(usuarioActualizado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Eliminación lógica
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {

        boolean eliminado = usuarioServicio.eliminarUsuario(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}