
package com.sena.adso.crm.controller;

import com.sena.adso.crm.entidades.Producto;
import com.sena.adso.crm.servicio.ProductoServicio;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoControlador {

    private final ProductoServicio productoServicio;

    public ProductoControlador(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }

    // Listar todos los productos activos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoServicio.listarProductos();
    }

    // Buscar un producto por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProducto(@PathVariable Integer id) {
        Producto producto = productoServicio.buscarProducto(id);

        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(producto);
    }

    // Registrar un nuevo producto
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        producto.setIdProducto(null);

        Producto guardado = productoServicio.guardarProducto(producto);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Integer id,
            @RequestBody Producto datos) {

        Producto actualizado = productoServicio.actualizarProducto(id, datos);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }

    // Realizar eliminación lógica de un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        if (productoServicio.buscarProducto(id) == null) {
            return ResponseEntity.notFound().build();
        }

        productoServicio.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }
}
