
package com.sena.adso.crm.dto;

public class DetalleVentaDTO {

    private Integer idProducto;
    private Integer cantidad;

    // Constructor vacío
    public DetalleVentaDTO() {
    }

    // Constructor con parámetros
    public DetalleVentaDTO(Integer idProducto, Integer cantidad) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
