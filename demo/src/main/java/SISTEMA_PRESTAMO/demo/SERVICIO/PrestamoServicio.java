package SISTEMA_PRESTAMO.demo.SERVICIO;

import SISTEMA_PRESTAMO.demo.DATOS.IPrestamoDatos;
import SISTEMA_PRESTAMO.demo.DATOS.IRecursoDatos;
import SISTEMA_PRESTAMO.demo.DATOS.IUsuarioDatos;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.EstadoPrestamo;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.EstadoRecurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.TipoRecurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.TipoUsuario;
import SISTEMA_PRESTAMO.demo.DOMINIO.Prestamo;
import SISTEMA_PRESTAMO.demo.DOMINIO.Recurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.Usuario;
import SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES.IPrestamoServicio;
import SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES.IRecursoServicio;
import SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES.ISancionServicio;
import SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES.IUsuarioServicio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrestamoServicio implements IPrestamoServicio {
    @Autowired
    private IPrestamoDatos prestamoDatos;
    @Autowired
    private IRecursoServicio recursoServicio;
    @Autowired
    private IUsuarioServicio usuarioServicio;
    @Autowired
    private ISancionServicio sancionServicio;
    @Autowired
    private IUsuarioDatos usuariosDatos;

    //1,1-Verificacion de usuario de nuevo prestamo
    private Usuario devolverUsuarioVerificado(String legajoUsuario) {
        Usuario u = usuarioServicio.listarUsuarioPorLegajo(legajoUsuario);
        //si u esta vacio
        if (u == null) {
            throw  new IllegalArgumentException("usuario vacio,no se encontro legajo");
        }
        //usuario bloqueado
        if (u.isbloqueado()) {
            throw new IllegalStateException("usuario bloqueado no puede hacer prestamos");
        }
        //cumple limite por usuario,llamamos a funcion
        if (!cumpleLimitesPorUsuario(u)) {
            throw new IllegalStateException("se alcanzo el limite por usuario");
        }
        return u;
    }

    //1.2-cumple limites por usuario
    private boolean cumpleLimitesPorUsuario(Usuario usuario) {
        List<Prestamo> prestamos = prestamoDatos.findAll();
        int contador = 0;
        for (Prestamo p : prestamos) {
            //sincronizamos usuario con prestamo
            if (!p.getUsuario().equals(usuario)){
                continue;//si es diferente pasamos al siguiente indice del for
            }
            //usamos contador
            if (p.getEstadoPrestamo() == EstadoPrestamo.ACTIVO || p.getEstadoPrestamo() ==EstadoPrestamo.VENCIDO) {
                contador++;
            }
            //validadores dentro del for ya que queremos que se corte antes del limite
            if (usuario.getTipoUsuario() == TipoUsuario.ALUMNO && contador >= 1) {
                throw new IllegalStateException("alumno puede tener solo un prestamo");
            }
            if (usuario.getTipoUsuario() == TipoUsuario.DOCENTE && contador >= 3) {
                throw new IllegalStateException("docente puede tener hasta 3 prestamos");
            }
        }
        return true;
    }

    //1.3-Verificacion de recurso para nuevo prestamo
    private Recurso devolverRecursoVerificado(String codigo) {
        Recurso r = recursoServicio.listarRecursoCodigo(codigo);
        //si no se encuentra legajo
        if (r == null) {
            throw new IllegalArgumentException("recurso vacio,no se encontro codigo");
        }
        //solo se puede prestar un recurso disponible
        if (r.getEstado() != EstadoRecurso.DISPONIBLE) {
            throw new IllegalStateException("no se puede prestar un recurso que no este disponible");
        }
        return r;
    }

    //1.4-validar recursos restringidos
    private boolean validarRecursosRestringidos(String legajoUsuario, String codigoRecurso) {
        Usuario usuario = usuarioServicio.listarUsuarioPorLegajo(legajoUsuario);
        Recurso recurso = recursoServicio.listarRecursoCodigo(codigoRecurso);
        if (recurso == null ) {
            throw new IllegalArgumentException("no se encontro recurso");
        }
        if(usuario==null){
            throw new IllegalArgumentException("no se encontro usuario");
        }
        //Recursos restringidos,kitLaboratio solo docente
        if (usuario.getTipoUsuario() == TipoUsuario.ALUMNO
                && recurso.getTipoRecurso() == TipoRecurso.KITLABORATORIO) {
            throw new IllegalStateException("kit laboratorio solo lo puede usar docente");
        }
        //Recursos restringidos ,notebook solo alumnos
        if (usuario.getTipoUsuario() == TipoUsuario.DOCENTE
                && recurso.getTipoRecurso() == TipoRecurso.NOTEBOOK) {
            throw new IllegalStateException("notebook solo la pueden pedir alumnos");
        }
        return true;
    }

    //1.5-pasar prestamos a vencidos
    private void pasarPrestamosaVencidos(){
        //una query seria muy poco eficiente para eso la hacemos manual
        prestamoDatos.pasarPrestamosVencidos();
    }

    @Override
    //1.6-registrar nuevo prestamo
    public void registrarNuevoPrestamo(String legajoUsuario,String codigoRecurso, LocalDateTime fechaVencimientoPrestamo) {

        //actualizamos estados de prestamos
        pasarPrestamosaVencidos();
        Usuario usuario = devolverUsuarioVerificado(legajoUsuario);
        Recurso recurso = devolverRecursoVerificado(codigoRecurso);
        Prestamo prestamo=new Prestamo(usuario,recurso,LocalDateTime.now(),fechaVencimientoPrestamo,null
                ,EstadoPrestamo.ACTIVO);

        //validar recursos restringidos
        if (!validarRecursosRestringidos(legajoUsuario, codigoRecurso)) {
            return;
        }
        //añadimos prestamo
        prestamoDatos.save(prestamo);
        //cambiar estado del recurso
        recursoServicio.cambiarEstadoRecurso(codigoRecurso,EstadoRecurso.PRESTADO);
    }

    @Override
    public List<Prestamo> listarPrestamos() {
        List<Prestamo> prestamos = prestamoDatos.findAll();
        return prestamos;
    }

    @Override
    public List<Prestamo> listarPrestamoPorUsuario(String legajo) {
        Usuario u = usuarioServicio.listarUsuarioPorLegajo(legajo);
        List<Prestamo> prestamos=prestamoDatos.findByUsuarioLegajo(legajo);
        if(u==null){
            throw new IllegalArgumentException("no existe usuario con ese legajo");
        }
        return prestamos;
    }

    @Override
    public List<Prestamo> listarPrestamoPorRecurso(String codigo) {
        Recurso recurso = recursoServicio.listarRecursoCodigo(codigo);
        if (recurso == null) {
            throw new IllegalArgumentException("no existe RECURSO con codigo: " + codigo);
        }
        List<Prestamo> prestamos = prestamoDatos.findByRecursoCodigo(codigo);

        return prestamos;
    }

    @Override
    public Prestamo listarPrestamoPorId(Integer id) {
        if(id==null){
            throw new IllegalArgumentException("el id no puede estar vacio");
        }
        return prestamoDatos.findById(id).orElse(null);
    }
    @Override
    public void registrarDevolucion(Integer id) {
        if(id==null){
            throw new IllegalArgumentException("el id no puede estar vacio");
        }
        //obtenemos el prestamo
        Prestamo prestamo = prestamoDatos.findById(id).orElse(null);
        //no se encuentra prestamo
        if (prestamo == null) {
            throw new IllegalArgumentException("no se encontro el prestamo con id:" + id);
        }
        //VERIFICAR QUE ESTADO SEA ACTIVO O VENCIDO
        if (prestamo.getEstadoPrestamo() != EstadoPrestamo.ACTIVO && prestamo.getEstadoPrestamo() != EstadoPrestamo.VENCIDO) {
            throw new IllegalStateException ("error,el estado del prestamo para poder devolver debe ser activo o vencido");
        }
        //verificar que devolucion sea despues de prestamo inicio
        LocalDateTime fechaDevolucion = LocalDateTime.now();
        if (fechaDevolucion.isBefore(prestamo.getFechaInicio())) {
            throw new IllegalStateException("la fecha de devolucion no puede ser antes que la creacion del prestamo");
        }
        //setiamos los atributos y despues guardamos este objeto de nuevo,usamos uso  de save entonces de paso nos queda
       // el set en la memoria cache
        prestamo.setEstadoPrestamo(EstadoPrestamo.DEVUELTO);
        prestamo.setFechaDevolucion(LocalDateTime.now());
        prestamo.getRecurso().setEstadoRecurso(EstadoRecurso.DISPONIBLE);
        //guardamos el objeto en mysql
        prestamoDatos.save(prestamo);

        //se aplica sancion al usuario si es que hay dias de atraso
        sancionServicio.aplicarSancion(prestamo);
    }
    @Override
    public List<Prestamo> listarPrestamosVencidos() {
        List<Prestamo> prestamos = prestamoDatos.findByEstadoPrestamo(EstadoPrestamo.VENCIDO);
        return prestamos;
    }

    @Override
    public List<Prestamo> listarHistorialRecurso(String codigoRecurso) {
        Recurso recurso = recursoServicio.listarRecursoCodigo(codigoRecurso);
        if (recurso == null) {
            throw new IllegalArgumentException("no existe ese recurso con id:"+ codigoRecurso);
        }
        return prestamoDatos.findByRecursoCodigo(codigoRecurso);
    }
}
