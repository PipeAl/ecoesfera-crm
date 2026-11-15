
package com.sena.adso.crm.servicio;

import com.sena.adso.crm.entidades.Producto;
import com.sena.adso.crm.entidades.Categoria;
import com.sena.adso.crm.repository.ProveedorRepository;
import com.sena.adso.crm.repository.ProductoRepository;
import com.sena.adso.crm.repository.CategoriaRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class ProductoServicio {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProveedorRepository proveedorRepository;

    public ProductoServicio(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            ProveedorRepository proveedorRepository) {

        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.proveedorRepository = proveedorRepository;
    }

    // Listar productos activos
    public List<Producto> listarProductos() {
        return productoRepository.findByEstadoTrue();
    }

    // Buscar producto por ID
    public Producto buscarProducto(Integer id) {
        return productoRepository
                .findByIdProductoAndEstadoTrue(id)
                .orElse(null);
    }

    // Registrar producto
    public Producto guardarProducto(Producto producto) {

        validarDatos(producto);
        validarCategoria(producto.getIdCategoria());
        validarProveedor(producto.getIdProveedor());

        producto.setNombre(producto.getNombre().trim());

        if (productoRepository.existsByNombreIgnoreCase(
                producto.getNombre())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe un producto con ese nombre"
            );
        }

        if (producto.getEstado() == null) {
            producto.setEstado(true);
        }

        return productoRepository.save(producto);
    }

    // Actualizar producto
    public Producto actualizarProducto(Integer id, Producto producto) {

        Producto existente = buscarProducto(id);

        if (existente == null) {
            return null;
        }

        validarDatos(producto);
        validarCategoria(producto.getIdCategoria());
        validarProveedor(producto.getIdProveedor());

        String nombre = producto.getNombre().trim();

        if (productoRepository.existsByNombreIgnoreCaseAndIdProductoNot(
                nombre, id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe otro producto con ese nombre"
            );
        }

        existente.setNombre(nombre);
        existente.setDescripcion(producto.getDescripcion());
        existente.setImagen(producto.getImagen());
        existente.setPrecioVenta(producto.getPrecioVenta());
        existente.setStock(producto.getStock());
        existente.setStockMinimo(producto.getStockMinimo());
        existente.setIdCategoria(producto.getIdCategoria());
        existente.setIdProveedor(producto.getIdProveedor());

        if (producto.getEstado() != null) {
            existente.setEstado(producto.getEstado());
        }

        return productoRepository.save(existente);
    }

    // Validar datos del producto
    private void validarDatos(Producto producto) {

        if (producto.getNombre() == null ||
                producto.getNombre().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre del producto es obligatorio"
            );
        }

        if (producto.getPrecioVenta() == null ||
                producto.getPrecioVenta().signum() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El precio debe ser mayor que cero"
            );
        }

        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El stock no puede ser negativo"
            );
        }

        if (producto.getStockMinimo() == null ||
                producto.getStockMinimo() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El stock mínimo no puede ser negativo"
            );
        }

        if (producto.getIdCategoria() == null ||
                producto.getIdProveedor() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La categoría y el proveedor son obligatorios"
            );
        }
    }

    // Validar que la categoría exista y esté activa
    private void validarCategoria(Integer idCategoria) {

        Categoria categoria = categoriaRepository
                .findByIdCategoriaAndEstadoTrue(idCategoria)
                .orElse(null);

        if (categoria == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La categoría no existe o está inactiva"
            );
        }
    }

    // Validar que el proveedor exista y esté activo
    private void validarProveedor(Integer idProveedor) {

        if (proveedorRepository
                .findByIdProveedorAndEstadoTrue(idProveedor)
                .isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El proveedor no existe o está inactivo"
            );
        }
    }

    // Eliminación lógica del producto
    public void eliminarProducto(Integer id) {

        Producto producto = buscarProducto(id);

        if (producto != null) {
            producto.setEstado(false);
            productoRepository.save(producto);
        }
    }
}
