package SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES;

import SISTEMA_PRESTAMO.demo.DOMINIO.Prestamo;
import SISTEMA_PRESTAMO.demo.DOMINIO.Sancion;
import SISTEMA_PRESTAMO.demo.DOMINIO.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface ISancionServicio {
    boolean aplicarSancion(Prestamo prestamo);
    List<Sancion> listarSanciones();
    List<Sancion> listarUsuariosbloqueados();

}
