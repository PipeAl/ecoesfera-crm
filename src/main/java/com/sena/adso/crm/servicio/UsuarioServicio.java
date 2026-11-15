package com.sena.adso.crm.servicio;

import com.sena.adso.crm.entidades.Usuario;
import com.sena.adso.crm.repository.UsuarioRepository;
import com.sena.adso.crm.repository.RolRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicio {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioServicio(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository) {

        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Listar usuarios activos
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findByEstadoTrue();
    }

    // Buscar usuario activo por ID
    public Usuario buscarUsuario(Integer id) {
        return usuarioRepository.findByIdUsuarioAndEstadoTrue(id)
                .orElse(null);
    }

    // Registrar usuario
    public Usuario registrarUsuario(Usuario usuario) {

        validarDatos(usuario);

        String correo = usuario.getCorreo().trim().toLowerCase();

        if (usuarioRepository.existsByCorreoIgnoreCase(correo)) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario con ese correo electrónico");
        }

        if (usuario.getPassword() == null ||
                usuario.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La contraseña es obligatoria");
        }

        validarRol(usuario.getIdRol());

        usuario.setNombres(usuario.getNombres().trim());
        usuario.setApellidos(usuario.getApellidos().trim());
        usuario.setCorreo(correo);

        // Cifrar contraseña antes de guardarla
        usuario.setPassword(
                passwordEncoder.encode(usuario.getPassword()));

        usuario.setEstado(true);

        return usuarioRepository.save(usuario);
    }

    // Actualizar usuario
    public Usuario actualizarUsuario(Integer id, Usuario datos) {

        Usuario usuarioExistente = buscarUsuario(id);

        if (usuarioExistente == null) {
            return null;
        }

        validarDatos(datos);

        String correo = datos.getCorreo().trim().toLowerCase();

        if (usuarioRepository.existsByCorreoIgnoreCaseAndIdUsuarioNot(
                correo, id)) {
            throw new IllegalArgumentException(
                    "Ya existe otro usuario con ese correo electrónico");
        }

        validarRol(datos.getIdRol());

        usuarioExistente.setNombres(datos.getNombres().trim());
        usuarioExistente.setApellidos(datos.getApellidos().trim());
        usuarioExistente.setCorreo(correo);
        usuarioExistente.setTelefono(datos.getTelefono());
        usuarioExistente.setDireccion(datos.getDireccion());
        usuarioExistente.setIdRol(datos.getIdRol());

        // Solo cambiar la contraseña si se envía una nueva
        if (datos.getPassword() != null &&
                !datos.getPassword().trim().isEmpty()) {

            usuarioExistente.setPassword(
                    passwordEncoder.encode(datos.getPassword()));
        }

        return usuarioRepository.save(usuarioExistente);
    }

    // Eliminación lógica
    public boolean eliminarUsuario(Integer id) {

        Usuario usuario = buscarUsuario(id);

        if (usuario == null) {
            return false;
        }

        usuario.setEstado(false);
        usuarioRepository.save(usuario);

        return true;
    }

    // Validar datos obligatorios
    private void validarDatos(Usuario usuario) {

        if (usuario.getNombres() == null ||
                usuario.getNombres().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Los nombres son obligatorios");
        }

        if (usuario.getApellidos() == null ||
                usuario.getApellidos().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Los apellidos son obligatorios");
        }

        if (usuario.getCorreo() == null ||
                usuario.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El correo electrónico es obligatorio");
        }

        if (!usuario.getCorreo().trim().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException(
                    "El correo electrónico no tiene un formato válido");
        }
    }

    // Validar que el rol exista y esté activo
    private void validarRol(Integer idRol) {

        if (idRol == null ||
                rolRepository.findByIdRolAndEstadoTrue(idRol).isEmpty()) {
            throw new IllegalArgumentException(
                    "El rol seleccionado no existe o está inactivo");
        }
    }
}