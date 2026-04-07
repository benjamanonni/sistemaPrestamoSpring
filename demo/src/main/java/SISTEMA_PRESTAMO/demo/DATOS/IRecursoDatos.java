package SISTEMA_PRESTAMO.demo.DATOS;

import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.EstadoRecurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.TipoRecurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRecursoDatos extends JpaRepository<Recurso,String> {
    List<Recurso> findByEstado(EstadoRecurso estado);

    List<Recurso> findByTipoRecurso(TipoRecurso tipoRecurso);

    List<Recurso> findByNombreContaining(String descripcion);
}
