package SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES;

import SISTEMA_PRESTAMO.demo.DOMINIO.Prestamo;
import SISTEMA_PRESTAMO.demo.DOMINIO.Recurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface IPrestamoServicio {
    void registrarNuevoPrestamo(String usuario,String recurso, LocalDateTime fechaVencimientoPrestamo);
    List<Prestamo>listarPrestamos();
    List<Prestamo>listarPrestamoPorUsuario(String legajoUsuario);
    List<Prestamo>listarPrestamoPorRecurso(String codigoRecurso);
    Prestamo listarPrestamoPorId(Integer id);
    void registrarDevolucion(Integer id);
    List<Prestamo> listarPrestamosVencidos();
    List<Prestamo>listarHistorialRecurso(String idRecurso);
}
