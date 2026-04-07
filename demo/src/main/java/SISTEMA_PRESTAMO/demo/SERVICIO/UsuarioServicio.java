package SISTEMA_PRESTAMO.demo.SERVICIO;

import SISTEMA_PRESTAMO.demo.DATOS.IUsuarioDatos;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.TipoUsuario;
import SISTEMA_PRESTAMO.demo.DOMINIO.Usuario;
import SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServicio implements IUsuarioServicio {
    @Autowired
    private IUsuarioDatos usuarioDato;

    @Override
    //1-registrar nuevo usuario
    public void registrarNuevoUsuario(String legajo, String nombreCompleto, TipoUsuario tipoUsuario, String correo) {
        if (usuarioDato.existsById(legajo)) {
            throw new RuntimeException("Ya existe un usuario con ese legajo");
        }
        if (usuarioDato.existsByCorreo(correo)) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }

        Usuario usuario= new Usuario(legajo,nombreCompleto,tipoUsuario,correo,null);
        try {
            usuarioDato.save(usuario);
        } catch (Exception e) {
            if (e.getMessage().contains("correo")) {
                throw new RuntimeException("El correo ya está registrado");
            }
            throw new RuntimeException("Error al registrar usuario");
        }
    }
    //2-listar usuario
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioDato.findAll();
    }
    //3-listar usuario por legajo
    @Override
    public Usuario listarUsuarioPorLegajo(String legajo) {
        if (legajo == null|| legajo.isBlank()) {
            throw new IllegalArgumentException("legajo de parametro vacio");
        }
        return usuarioDato.findById(legajo).orElse(null);
    }


}
