package SISTEMA_PRESTAMO.demo.DOMINIO;

import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.EstadoPrestamo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
@Entity
@Getter
@ToString
@Table(name = "prestamos")
public class Prestamo {
    @ManyToOne //especificamos cardinalidad
    @JoinColumn(name="U_Legajo_Hace") //especificamos la fk
    @NotNull
    private  Usuario usuario;//pasamos el objeto

    @ManyToOne//especificamos cardinalidad
    @JoinColumn(name="R_Codigo")//especificamos la fk
    @NotNull
    private Recurso recurso;

    @Column(name="P_Fecha_Inicio")
    @NotNull
    private  LocalDateTime fechaInicio;
    @Column(name="P_Fecha_Vencimiento")
    @NotNull
    private  LocalDateTime fechaVencimiento;
    @Column(name="P_Fecha_Devolucion")
    private  LocalDateTime fechaDevolucion;//fecha cuando se devuelve
    @Column(name="P_Estado")
    @NotNull
    //el enum es de tipo String lo queremos trabajar porque esta como varchar en la base de datos para eso usamos:
    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estadoPrestamo;
    @Id
    @Column(name="P_Id")
    //avisar que es autoIncrement lo obtenemos de mysql
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrestamo;

    //contructor completo lo usamos al registrar prestamo
    public Prestamo(Usuario usuario,Recurso recurso,LocalDateTime fechaInicio,LocalDateTime fechaVencimiento
            ,LocalDateTime fechaDevolucion,EstadoPrestamo estadoPrestamo){
        if(fechaDevolucion==null){
            estadoPrestamo= EstadoPrestamo.ACTIVO;
        }
        this.usuario=usuario;
        this.recurso=recurso;
        this.fechaInicio=fechaInicio;
        this.fechaVencimiento=fechaVencimiento;
        this.fechaDevolucion=fechaDevolucion;
        this.estadoPrestamo=estadoPrestamo;
    }
    //contructor vacio para spring
    public Prestamo(){
    }
    public void setIdPrestamo(Integer id){
        this.idPrestamo=id;
    }
    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    public void setEstadoPrestamo(EstadoPrestamo estado){
        this.estadoPrestamo=estado;
    }
}
