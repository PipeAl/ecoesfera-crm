package com.sena.adso.crm.repository;

import org .springframework.data.jpa.repository.JpaRepository;
import com.sena.adso.crm.entidades.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
}

