
package com.sena.adso.crm.servicio;

import com.sena.adso.crm.dto.VentaDTO;
import com.sena.adso.crm.dto.DetalleVentaDTO;

import com.sena.adso.crm.entidades.Venta;
import com.sena.adso.crm.entidades.DetalleVenta;
import com.sena.adso.crm.entidades.Producto;

import com.sena.adso.crm.repository.VentaRepository;
import com.sena.adso.crm.repository.DetalleVentaRepository;
import com.sena.adso.crm.repository.ProductoRepository;
import com.sena.adso.crm.repository.ClienteRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VentaServicio {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;

    public VentaServicio(
            VentaRepository ventaRepository,
            DetalleVentaRepository detalleVentaRepository,
            ProductoRepository productoRepository,
            ClienteRepository clienteRepository) {

        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
    }

    // Listar todas las ventas
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    // Buscar una venta por su ID
    public Venta buscarVenta(Integer id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "La venta no existe"
                ));
    }

    // Consultar las ventas de un cliente
    public List<Venta> listarVentasPorCliente(Integer idCliente) {
        return ventaRepository.findByIdCliente(idCliente);
    }

    // Registrar una venta completa
    @Transactional
    public Venta registrarVenta(VentaDTO ventaDTO) {

        // Validar que se haya enviado la información
        if (ventaDTO == null || ventaDTO.getIdCliente() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe seleccionar un cliente"
            );
        }

        if (ventaDTO.getDetalles() == null ||
                ventaDTO.getDetalles().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La venta debe contener al menos un producto"
            );
        }

        // Validar que el cliente exista y esté activo
        if (clienteRepository.findByIdClienteAndEstadoTrue(
                ventaDTO.getIdCliente()).isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El cliente no existe o está inactivo"
            );
        }

        // Crear la venta
        Venta venta = new Venta();
        venta.setIdCliente(ventaDTO.getIdCliente());
        venta.setTotal(BigDecimal.ZERO);

        // Guardar inicialmente la venta para obtener su ID
        venta = ventaRepository.save(venta);

        BigDecimal total = BigDecimal.ZERO;

        // Procesar cada producto de la venta
        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {

            if (detalleDTO == null ||
                    detalleDTO.getIdProducto() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Debe indicar un producto válido"
                );
            }

            if (detalleDTO.getCantidad() == null ||
                    detalleDTO.getCantidad() <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La cantidad debe ser mayor que cero"
                );
            }

            // Buscar y bloquear el producto activo
            Producto producto = productoRepository
                    .buscarProductoParaVenta(
                            detalleDTO.getIdProducto())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "El producto no existe o está inactivo"
                    ));

            // Validar inventario disponible
            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Stock insuficiente para el producto: "
                                + producto.getNombre()
                );
            }

            // Obtener el precio real desde la base de datos
            BigDecimal precio = producto.getPrecioVenta();

            // Calcular subtotal
            BigDecimal subtotal = precio.multiply(
                    BigDecimal.valueOf(detalleDTO.getCantidad())
            );

            // Crear el detalle de la venta
            DetalleVenta detalle = new DetalleVenta();

            detalle.setIdVenta(venta.getIdVenta());
            detalle.setIdProducto(producto.getIdProducto());
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(precio);
            detalle.setSubtotal(subtotal);

            detalleVentaRepository.save(detalle);

            // Descontar inventario
            producto.setStock(
                    producto.getStock() - detalleDTO.getCantidad()
            );

            productoRepository.save(producto);

            // Acumular el total
            total = total.add(subtotal);
        }

        // Actualizar el total definitivo de la venta
        venta.setTotal(total);

        return ventaRepository.save(venta);
    }
}
