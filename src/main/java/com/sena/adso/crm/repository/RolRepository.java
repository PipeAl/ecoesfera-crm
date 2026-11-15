package com.sena.adso.crm.repository;

import com.sena.adso.crm.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    List<Rol> findByEstadoTrue();

    Optional<Rol> findByIdRolAndEstadoTrue(Integer idRol);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdRolNot(String nombre, Integer idRol);
}