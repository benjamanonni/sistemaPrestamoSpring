package SISTEMA_PRESTAMO.demo.DATOS;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.EstadoPrestamo;
import SISTEMA_PRESTAMO.demo.DOMINIO.Prestamo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPrestamoDatos extends JpaRepository<Prestamo,Integer> {
    @Modifying
    @Transactional
    @Query("""
        UPDATE Prestamo p
        SET p.estadoPrestamo = 'VENCIDO'
        WHERE p.estadoPrestamo = 'ACTIVO'
        AND p.fechaVencimiento < CURRENT_TIMESTAMP
    """)void pasarPrestamosVencidos();


    List<Prestamo> findByEstadoPrestamo(EstadoPrestamo estadoPrestamo);

    List<Prestamo> findByUsuarioLegajo(String legajo);

    List<Prestamo> findByRecursoCodigo(String codigo);
}
