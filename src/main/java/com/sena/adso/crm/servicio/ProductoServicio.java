package com.sena.adso.crm.servicio;

import com.sena.adso.crm.entidades.Producto;
import com.sena.adso.crm.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service 
public class ProductoServicio {

    private final ProductoRepository productoRepository;
 
    public ProductoServicio(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto buscarProducto(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }
}
