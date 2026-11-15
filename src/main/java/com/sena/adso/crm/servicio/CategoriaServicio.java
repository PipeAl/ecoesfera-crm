
package com.sena.adso.crm.servicio;

import com.sena.adso.crm.entidades.Categoria;
import com.sena.adso.crm.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class CategoriaServicio {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServicio(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findByEstadoTrue();
    }

    public Categoria buscarCategoria(Integer id) {
        return categoriaRepository
                .findByIdCategoriaAndEstadoTrue(id)
                .orElse(null);
    }

    public Categoria guardarCategoria(Categoria categoria) {

        validarNombre(categoria.getNombre());

        String nombre = categoria.getNombre().trim();

        var categoriaExistente = categoriaRepository
                .findByNombreIgnoreCase(nombre);

        if (categoriaExistente.isPresent()) {

            Categoria existente = categoriaExistente.get();

            if (Boolean.FALSE.equals(existente.getEstado())) {

                existente.setNombre(nombre);
                existente.setDescripcion(categoria.getDescripcion());
                existente.setEstado(true);

                return categoriaRepository.save(existente);
            }

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe una categoría activa con ese nombre"
            );
        }

        categoria.setNombre(nombre);
        categoria.setEstado(true);

        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategoria(Integer id, Categoria categoria) {

        Categoria existente = buscarCategoria(id);

        if (existente == null) {
            return null;
        }

        validarNombre(categoria.getNombre());

        String nombre = categoria.getNombre().trim();

        if (categoriaRepository.existsByNombreIgnoreCaseAndIdCategoriaNot(
                nombre, id)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe otra categoría con ese nombre"
            );
        }

        existente.setNombre(nombre);
        existente.setDescripcion(categoria.getDescripcion());

        if (categoria.getEstado() != null) {
            existente.setEstado(categoria.getEstado());
        }

        return categoriaRepository.save(existente);
    }

    private void validarNombre(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre de la categoría es obligatorio"
            );
        }
    }

    public void eliminarCategoria(Integer id) {

        Categoria categoria = buscarCategoria(id);

        if (categoria != null) {
            categoria.setEstado(false);
            categoriaRepository.save(categoria);
        }
    }
}
