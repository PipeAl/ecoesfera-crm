
package com.sena.adso.crm.repository;

import com.sena.adso.crm.entidades.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Integer> {

    List<Venta> findByIdCliente(Integer idCliente);

}
