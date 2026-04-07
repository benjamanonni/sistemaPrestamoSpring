package SISTEMA_PRESTAMO.demo.SERVICIO;

import SISTEMA_PRESTAMO.demo.DATOS.IRecursoDatos;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.EstadoRecurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.TipoRecurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.Recurso;
import SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES.IRecursoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecursoServicio implements IRecursoServicio {
    @Autowired
    IRecursoDatos recursoDatos;
    @Override
    public void registrarNuevoRecurso(String nombre, String codigo, TipoRecurso tipoRecurso) {
        //validar que no se repita codigo
        if(recursoDatos.existsById(codigo))throw new RuntimeException("codigo repetido");
        Recurso recurso=new Recurso(nombre,codigo,tipoRecurso,EstadoRecurso.DISPONIBLE, LocalDateTime.now());
        try {
            recursoDatos.save(recurso);
        } catch (Exception e) {
            if (e.getMessage().contains("codigo")) {
                throw new RuntimeException("El codigo ya está registrado");
            }
            throw new RuntimeException("Error al registrar recurso");
        }
    }
    @Override
    public List<Recurso> listarRecursoEstado(EstadoRecurso estado) {
        if (estado == null) {
            throw  new IllegalArgumentException("estado de parametro vacio");
        }
        //implementamos metodo desde IrecursoDatos
        return recursoDatos.findByEstado(estado);
    }

    @Override
    public Recurso listarRecursoCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("codigo vacio error");
        }
        return recursoDatos.findById(codigo).orElse(null);
    }
    @Override
    public List<Recurso> listarRecursoTipo(TipoRecurso tipoRecurso) {
        if (tipoRecurso == null) {
            throw new IllegalArgumentException("tipo recurso vacio");
        }
        return recursoDatos.findByTipoRecurso(tipoRecurso);
    }

    @Override
    public void cambiarEstadoRecurso(String codigo, EstadoRecurso tipoestado) {
        if (tipoestado == null|| codigo.isBlank()) {
            throw  new IllegalArgumentException("parametros vacios");
        }
        //traemos el objeto para verificarlo aca
        Recurso r = recursoDatos.findById(codigo).orElse(null);

        //verificar que exista el recurso
        if(r==null){
            throw new IllegalArgumentException("recurso con codigo: "+ codigo + " no existe");
        }
        if (r.getEstado() == EstadoRecurso.PRESTADO) {
            throw new IllegalStateException("no se puede modificar el estado de un recurso que esta prestado");
        }
        if (r.getEstado() == EstadoRecurso.DADOBAJA) {
            throw new IllegalStateException("no se puede modificar el estado de un recurso que esta dado de baja");
        }

        //controlar de que no se pueda cambiar al mismo estado que ya tiene
        if (r.getEstado() == tipoestado) {
            throw new IllegalStateException("estado repetido este ya lo posee");
        }
        //editamos en memoria
        r.setEstadoRecurso(tipoestado);
        //lo volvemos a guardar editado
        recursoDatos.save(r);
    }

    @Override
    public List<Recurso> listarRecursosActivos() {
        return recursoDatos.findByEstado(EstadoRecurso.DISPONIBLE);
    }
    @Override
    public List<Recurso> listarRecursoNombre(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("descripcion invalida");
        }
         return recursoDatos.findByNombreContaining(descripcion);
    }
}
