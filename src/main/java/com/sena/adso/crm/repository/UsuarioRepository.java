package com.sena.adso.crm.repository;

import com.sena.adso.crm.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Listar usuarios activos
    List<Usuario> findByEstadoTrue();

    // Buscar usuario activo por ID
    Optional<Usuario> findByIdUsuarioAndEstadoTrue(Integer idUsuario);

    // Verificar si un correo ya existe
    boolean existsByCorreoIgnoreCase(String correo);

    // Verificar correo al actualizar, excluyendo el usuario actual
    boolean existsByCorreoIgnoreCaseAndIdUsuarioNot(String correo, Integer idUsuario);
}