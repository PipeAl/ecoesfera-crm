package com.sena.adso.crm.servicio;

import com.sena.adso.crm.entidades.Rol;
import com.sena.adso.crm.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServicio {

    private final RolRepository rolRepository;

    public RolServicio(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // Listar roles activos
    public List<Rol> listarRoles() {
        return rolRepository.findByEstadoTrue();
    }

    // Buscar un rol activo por ID
    public Rol buscarRol(Integer id) {
        return rolRepository.findByIdRolAndEstadoTrue(id).orElse(null);
    }

    // Registrar un rol
    public Rol registrarRol(Rol rol) {

        if (rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol es obligatorio");
        }

        String nombre = rol.getNombre().trim();

        if (rolRepository.existsByNombreIgnoreCase(nombre)) {
            throw new IllegalArgumentException("Ya existe un rol con ese nombre");
        }

        rol.setNombre(nombre);
        rol.setEstado(true);

        return rolRepository.save(rol);
    }

    // Actualizar un rol
    public Rol actualizarRol(Integer id, Rol datos) {

        Rol rolExistente = buscarRol(id);

        if (rolExistente == null) {
            return null;
        }

        if (datos.getNombre() == null || datos.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol es obligatorio");
        }

        String nombre = datos.getNombre().trim();

        if (rolRepository.existsByNombreIgnoreCaseAndIdRolNot(nombre, id)) {
            throw new IllegalArgumentException("Ya existe otro rol con ese nombre");
        }

        rolExistente.setNombre(nombre);
        rolExistente.setDescripcion(datos.getDescripcion());

        return rolRepository.save(rolExistente);
    }

    // Eliminación lógica
    public boolean eliminarRol(Integer id) {

        Rol rol = buscarRol(id);

        if (rol == null) {
            return false;
        }

        rol.setEstado(false);
        rolRepository.save(rol);

        return true;
    }
}