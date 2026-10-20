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

    public ProductoControlador(ProductoServicio productoServicio){
        this.productoServicio = productoServicio;
    }

@GetMapping
public List<Producto> listarPorductos() {
    return productoServicio.listarProductos();
}

@GetMapping("/{id}")
public ResponseEntity<Producto> buscarProducto(@PathVariable Integer id) {
    Producto producto = productoServicio.buscarProducto(id);

    if (producto == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(producto);
}

@PostMapping 
public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
    producto.setIdProducto(null);

    Producto guardado = productoServicio.guardarProducto(producto);

    return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
}

@PutMapping("/{id}")
public ResponseEntity<Producto> actualizarProducto(
    @PathVariable Integer id,
    @RequestBody Producto datos) {

    Producto productoExistente = productoServicio.buscarProducto(id);
   if (productoExistente == null) {
        return ResponseEntity.notFound().build();
    }

    datos.setIdProducto(id);

    return ResponseEntity.ok(productoServicio.guardarProducto(datos));

}
@DeleteMapping("/{id}")
public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
    if (productoServicio.buscarProducto(id) == null) {
        return ResponseEntity.notFound().build();
    }

    productoServicio.eliminarProducto(id);
    
    return ResponseEntity.noContent().build();
}
}




