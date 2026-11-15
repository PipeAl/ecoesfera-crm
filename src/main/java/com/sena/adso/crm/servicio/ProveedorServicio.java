package com.sena.adso.crm.servicio;

import com.sena.adso.crm.entidades.Proveedor;
import com.sena.adso.crm.repository.ProveedorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProveedorServicio {

    private final ProveedorRepository proveedorRepository;

    public ProveedorServicio(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    // Listar proveedores activos
    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findByEstadoTrue();
    }

    // Buscar proveedor activo por ID
    public Proveedor buscarProveedor(Integer id) {
        return proveedorRepository
                .findByIdProveedorAndEstadoTrue(id)
                .orElse(null);
    }

    // Registrar proveedor
    public Proveedor guardarProveedor(Proveedor proveedor) {

        validarDatos(proveedor);

        proveedor.setNombreEmpresa(proveedor.getNombreEmpresa().trim());
        proveedor.setContacto(proveedor.getContacto().trim());

        validarCorreo(proveedor.getCorreo());

        if (proveedor.getCorreo() != null &&
                !proveedor.getCorreo().isBlank() &&
                proveedorRepository.existsByCorreoIgnoreCase(proveedor.getCorreo().trim())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe un proveedor con ese correo"
            );
        }

        proveedor.setEstado(true);

        return proveedorRepository.save(proveedor);
    }

    // Actualizar proveedor
    public Proveedor actualizarProveedor(Integer id, Proveedor datos) {

        Proveedor existente = buscarProveedor(id);

        if (existente == null) {
            return null;
        }

        validarDatos(datos);

        String nombre = datos.getNombreEmpresa().trim();
        String contacto = datos.getContacto().trim();
        String correo = datos.getCorreo();

        validarCorreo(correo);

        if (correo != null && !correo.isBlank()) {
            correo = correo.trim();

            if (proveedorRepository.existsByCorreoIgnoreCaseAndIdProveedorNot(correo, id)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ya existe otro proveedor con ese correo"
                );
            }
        }

        existente.setNombreEmpresa(nombre);
        existente.setContacto(contacto);
        existente.setTelefono(datos.getTelefono());
        existente.setCorreo(correo);
        existente.setDireccion(datos.getDireccion());

        if (datos.getEstado() != null) {
            existente.setEstado(datos.getEstado());
        }

        return proveedorRepository.save(existente);
    }

    // Validar datos obligatorios
    private void validarDatos(Proveedor proveedor) {

        if (proveedor.getNombreEmpresa() == null ||
                proveedor.getNombreEmpresa().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre de la empresa es obligatorio"
            );
        }

        if (proveedor.getContacto() == null ||
                proveedor.getContacto().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El contacto del proveedor es obligatorio"
            );
        }
    }

    // Validar formato básico del correo
    private void validarCorreo(String correo) {

        if (correo != null && !correo.isBlank() &&
                !correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El formato del correo electrónico no es válido"
            );
        }
    }

    // Eliminación lógica
    public void eliminarProveedor(Integer id) {

        Proveedor proveedor = buscarProveedor(id);

        if (proveedor != null) {
            proveedor.setEstado(false);
            proveedorRepository.save(proveedor);
        }
    }
}