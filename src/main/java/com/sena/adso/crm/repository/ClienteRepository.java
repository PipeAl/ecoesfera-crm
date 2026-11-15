
package com.sena.adso.crm.repository;

import com.sena.adso.crm.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // Consultar clientes activos
    List<Cliente> findByEstadoTrue();

    // Buscar un cliente activo por su ID
    Optional<Cliente> findByIdClienteAndEstadoTrue(Integer idCliente);

    // Verificar si el documento ya está registrado
    boolean existsByDocumento(String documento);

    // Verificar documento duplicado al actualizar
    boolean existsByDocumentoAndIdClienteNot(
            String documento, Integer idCliente
    );

    // Verificar si el correo ya está registrado
    boolean existsByCorreoIgnoreCase(String correo);

    // Verificar correo duplicado al actualizar
    boolean existsByCorreoIgnoreCaseAndIdClienteNot(
            String correo, Integer idCliente
    );
}
