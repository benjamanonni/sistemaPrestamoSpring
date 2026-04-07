package SISTEMA_PRESTAMO.demo.SERVICIO;

import SISTEMA_PRESTAMO.demo.DATOS.ISancionDatos;
import SISTEMA_PRESTAMO.demo.DOMINIO.Prestamo;
import SISTEMA_PRESTAMO.demo.DOMINIO.Sancion;
import SISTEMA_PRESTAMO.demo.DOMINIO.Usuario;
import SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES.ISancionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SancionServicio implements ISancionServicio {

    @Autowired
    private ISancionDatos sancionDatos;
    //1-calcular atraso
    private long calcularAtraso(Prestamo prestamo) {
        //revisamos que exista prestamo
        if (prestamo == null) {
            throw new IllegalArgumentException("Prestamo inexistente");
        }
        //revisamos que exista fecha de devolucion
        if (prestamo.getFechaDevolucion() == null) {
            throw new IllegalArgumentException("este prestamo no ha sido devuelto");
        }
        //si fue entregado a tiempo
        if (prestamo.getFechaDevolucion().isBefore(prestamo.getFechaVencimiento())) {
            return 0;
        }
        //calculamos dia de atraso
        long diasAtraso = ChronoUnit.DAYS.between(prestamo.getFechaVencimiento(), prestamo.getFechaDevolucion());
        return diasAtraso;
    }

    //2-aplicar sancion al devolver el prestamo
    public boolean aplicarSancion(Prestamo prestamo) {
        long diasAtraso = calcularAtraso(prestamo);
        //si no hay atraso
        if (diasAtraso <= 0) {
            return false;
        }
        LocalDateTime ahora = LocalDateTime.now();
        //obtenemos usuario
        Usuario usuario = prestamo.getUsuario();
        //se bloquea desde el dia de la sancion mas los dias de atraso
        LocalDateTime fechaFinBloqueo = ahora.plusDays(diasAtraso);
        Sancion sancion=new Sancion(prestamo,usuario,diasAtraso,LocalDateTime.now(),fechaFinBloqueo);

        //aplicamos sancion a usuario
        sancion.getUsuario().setBloqueadoHasta(fechaFinBloqueo);
        sancionDatos.save(sancion);
        return true;
    }

    @Override
    public List<Sancion> listarSanciones() {
        List<Sancion>sanciones=sancionDatos.findAll();
        return sanciones;
    }

    @Override
    public List<Sancion> listarUsuariosbloqueados() {
       List<Sancion> sanciones = sancionDatos.findAll();
        return sanciones;
        }
}
