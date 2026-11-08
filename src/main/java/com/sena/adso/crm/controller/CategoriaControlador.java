
package com.sena.adso.crm.controller;

import com.sena.adso.crm.entidades.Categoria;
import com.sena.adso.crm.servicio.CategoriaServicio;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaControlador {

    private final CategoriaServicio categoriaServicio;

    public CategoriaControlador(CategoriaServicio categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        return ResponseEntity.ok(categoriaServicio.listarCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoria(@PathVariable Integer id) {

        Categoria categoria = categoriaServicio.buscarCategoria(id);

        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<Categoria> guardarCategoria(@RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaServicio.guardarCategoria(categoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(
            @PathVariable Integer id,
            @RequestBody Categoria categoria) {

        Categoria categoriaExistente = categoriaServicio.buscarCategoria(id);

        if (categoriaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        categoria.setIdCategoria(id);

        Categoria categoriaActualizada = categoriaServicio.guardarCategoria(categoria);

        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {

        Categoria categoria = categoriaServicio.buscarCategoria(id);

        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }

        categoriaServicio.eliminarCategoria(id);

        return ResponseEntity.noContent().build();
    }
}

