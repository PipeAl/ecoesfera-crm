package com.sena.adso.crm.repository;

import com.sena.adso.crm.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    // Consultar solamente proveedores activos
    List<Proveedor> findByEstadoTrue();

    // Buscar un proveedor por ID si está activo
    Optional<Proveedor> findByIdProveedorAndEstadoTrue(Integer idProveedor);

    // Verificar si ya existe un correo
    boolean existsByCorreoIgnoreCase(String correo);

    // Verificar si otro proveedor tiene el mismo correo
    boolean existsByCorreoIgnoreCaseAndIdProveedorNot(String correo, Integer idProveedor);
}
