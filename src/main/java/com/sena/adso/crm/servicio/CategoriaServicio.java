package com.sena.adso.crm.servicio;

import com.sena.adso.crm.entidades.Categoria;
import com.sena.adso.crm.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServicio {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServicio(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarCategoria(Integer id) {
        return categoriaRepository.findById(id).orElse(null);

    }

    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void eliminarCategoria(Integer id) {
        categoriaRepository.deleteById(id);
    }
}
