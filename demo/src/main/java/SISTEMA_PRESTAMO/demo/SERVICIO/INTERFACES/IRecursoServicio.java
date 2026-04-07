package SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES;

import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.EstadoRecurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.TipoRecurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.Recurso;

import java.util.List;

public interface IRecursoServicio {
    void registrarNuevoRecurso(String nombre, String codigo, TipoRecurso tipoRecurso);
    List<Recurso>listarRecursoEstado(EstadoRecurso estado);
    Recurso listarRecursoCodigo(String codigo);
    List<Recurso>listarRecursoTipo(TipoRecurso tipoRecurso);
    void cambiarEstadoRecurso(String codigo,EstadoRecurso estadoRecurso);
    List<Recurso>listarRecursosActivos();
    List<Recurso>listarRecursoNombre(String descripcion);
}
