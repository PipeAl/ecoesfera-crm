
package com.sena.adso.crm.repository;

import com.sena.adso.crm.entidades.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Listar productos activos
    List<Producto> findByEstadoTrue();

    // Buscar producto activo por ID
    Optional<Producto> findByIdProductoAndEstadoTrue(Integer idProducto);

    // Verificar si existe un producto con el mismo nombre
    boolean existsByNombreIgnoreCase(String nombre);

    // Verificar nombres duplicados al actualizar
    boolean existsByNombreIgnoreCaseAndIdProductoNot(
            String nombre, Integer idProducto
    );

    // Buscar producto activo para procesar una venta
    @Query("SELECT p FROM Producto p WHERE p.idProducto = :id AND p.estado = true")
    Optional<Producto> buscarProductoParaVenta(@Param("id") Integer idProducto);

}
