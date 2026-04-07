package SISTEMA_PRESTAMO.demo.DATOS;

import SISTEMA_PRESTAMO.demo.DOMINIO.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDatos extends JpaRepository<Usuario,String> {
    boolean existsByCorreo(String correo);
}
