
package com.sena.adso.crm.repository;

import com.sena.adso.crm.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByEstadoTrue();

    Optional<Categoria> findByIdCategoriaAndEstadoTrue(Integer idCategoria);

    Optional<Categoria> findByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdCategoriaNot(
            String nombre,
            Integer idCategoria
    );
}
