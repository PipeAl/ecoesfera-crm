
package com.sena.adso.crm.dto;

import java.util.List;

public class VentaDTO {

    private Integer idCliente;
    private List<DetalleVentaDTO> detalles;

    // Constructor vacío
    public VentaDTO() {
    }

    // Constructor con parámetros
    public VentaDTO(Integer idCliente, List<DetalleVentaDTO> detalles) {
        this.idCliente = idCliente;
        this.detalles = detalles;
    }

    // Getters y Setters
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public List<DetalleVentaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaDTO> detalles) {
        this.detalles = detalles;
    }
}
