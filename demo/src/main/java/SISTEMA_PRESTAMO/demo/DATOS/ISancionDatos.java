package SISTEMA_PRESTAMO.demo.DATOS;

import SISTEMA_PRESTAMO.demo.DOMINIO.Sancion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISancionDatos extends JpaRepository<Sancion,Integer> {
}
