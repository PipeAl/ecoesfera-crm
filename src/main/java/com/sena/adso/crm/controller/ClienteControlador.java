
package com.sena.adso.crm.controller;

import com.sena.adso.crm.entidades.Cliente;
import com.sena.adso.crm.servicio.ClienteServicio;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteControlador {

    private final ClienteServicio clienteServicio;

    public ClienteControlador(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    // Listar clientes activos
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteServicio.listarClientes();
    }

    // Buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Integer id) {

        Cliente cliente = clienteServicio.buscarCliente(id);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

    // Registrar cliente
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {

        cliente.setIdCliente(null);

        Cliente guardado = clienteServicio.guardarCliente(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable Integer id,
            @RequestBody Cliente datos) {

        Cliente actualizado = clienteServicio.actualizarCliente(id, datos);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }

    // Eliminación lógica del cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {

        if (clienteServicio.buscarCliente(id) == null) {
            return ResponseEntity.notFound().build();
        }

        clienteServicio.eliminarCliente(id);

        return ResponseEntity.noContent().build();
    }
}
