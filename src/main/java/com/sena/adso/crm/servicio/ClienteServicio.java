
package com.sena.adso.crm.servicio;

import com.sena.adso.crm.entidades.Cliente;
import com.sena.adso.crm.repository.ClienteRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClienteServicio {

    private final ClienteRepository clienteRepository;

    public ClienteServicio(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Listar clientes activos
    public List<Cliente> listarClientes() {
        return clienteRepository.findByEstadoTrue();
    }

    // Buscar cliente activo por ID
    public Cliente buscarCliente(Integer id) {
        return clienteRepository
                .findByIdClienteAndEstadoTrue(id)
                .orElse(null);
    }

    // Registrar cliente
    public Cliente guardarCliente(Cliente cliente) {

        validarDatos(cliente);

        String documento = cliente.getDocumento().trim();
        cliente.setDocumento(documento);

        validarCorreo(cliente.getCorreo());

        if (clienteRepository.existsByDocumento(documento)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe un cliente con ese documento"
            );
        }

        String correo = normalizarCorreo(cliente.getCorreo());
        cliente.setCorreo(correo);

        if (correo != null &&
                clienteRepository.existsByCorreoIgnoreCase(correo)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe un cliente con ese correo"
            );
        }

        cliente.setIdCliente(null);
        cliente.setEstado(true);

        return clienteRepository.save(cliente);
    }

    // Actualizar cliente
    public Cliente actualizarCliente(Integer id, Cliente datos) {

        Cliente existente = buscarCliente(id);

        if (existente == null) {
            return null;
        }

        validarDatos(datos);

        String documento = datos.getDocumento().trim();

        if (clienteRepository.existsByDocumentoAndIdClienteNot(
                documento, id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe otro cliente con ese documento"
            );
        }

        validarCorreo(datos.getCorreo());

        String correo = normalizarCorreo(datos.getCorreo());

        if (correo != null &&
                clienteRepository.existsByCorreoIgnoreCaseAndIdClienteNot(
                        correo, id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe otro cliente con ese correo"
            );
        }

        existente.setNombres(datos.getNombres().trim());
        existente.setApellidos(datos.getApellidos().trim());
        existente.setDocumento(documento);
        existente.setCorreo(correo);
        existente.setDireccion(datos.getDireccion());
        existente.setTelefono(datos.getTelefono());

        if (datos.getEstado() != null) {
            existente.setEstado(datos.getEstado());
        }

        return clienteRepository.save(existente);
    }

    // Validar campos obligatorios
    private void validarDatos(Cliente cliente) {

        if (cliente.getNombres() == null ||
                cliente.getNombres().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los nombres del cliente son obligatorios"
            );
        }

        if (cliente.getApellidos() == null ||
                cliente.getApellidos().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los apellidos del cliente son obligatorios"
            );
        }

        if (cliente.getDocumento() == null ||
                cliente.getDocumento().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El documento del cliente es obligatorio"
            );
        }
    }

    // Validar formato del correo
    private void validarCorreo(String correo) {

        if (correo != null && !correo.isBlank() &&
                !correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El formato del correo electrónico no es válido"
            );
        }
    }

    // Convertir correos vacíos en null
    private String normalizarCorreo(String correo) {

        if (correo == null || correo.isBlank()) {
            return null;
        }

        return correo.trim().toLowerCase();
    }

    // Eliminación lógica del cliente
    public void eliminarCliente(Integer id) {

        Cliente cliente = buscarCliente(id);

        if (cliente != null) {
            cliente.setEstado(false);
            clienteRepository.save(cliente);
        }
    }
}
