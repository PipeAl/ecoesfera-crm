
package com.sena.adso.crm.repository;

import com.sena.adso.crm.entidades.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {

    List<DetalleVenta> findByIdVenta(Integer idVenta);

    List<DetalleVenta> findByIdProducto(Integer idProducto);

}
