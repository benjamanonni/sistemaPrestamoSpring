package SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES;

import SISTEMA_PRESTAMO.demo.DATOS.IUsuarioDatos;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.TipoUsuario;
import SISTEMA_PRESTAMO.demo.DOMINIO.Usuario;

import java.util.ArrayList;
import java.util.List;

public interface IUsuarioServicio {
    void registrarNuevoUsuario(String legajo, String nombreCompleto, TipoUsuario tipoUsuario, String correo);
    List<Usuario> listarUsuarios();
    Usuario listarUsuarioPorLegajo(String legajo);
}
