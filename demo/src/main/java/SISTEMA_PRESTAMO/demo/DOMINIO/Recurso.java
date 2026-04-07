package SISTEMA_PRESTAMO.demo.DOMINIO;

import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.EstadoRecurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.TipoRecurso;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;
@Entity
@Getter
@ToString

@Table(name="Recursos")
public class Recurso {
    @NotBlank
    @Column(name="R_Nombre")
    private String nombre;
    @Id
    @NotBlank
    @Column(name = "R_codigo")
    private String codigo;
    @Column(name = "R_Tipo")
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoRecurso tipoRecurso;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="R_Estado")
    private EstadoRecurso estado;
    @NotNull
    @Column(name = "R_Fecha_alta")
    private LocalDateTime fechaAlta;

    public Recurso(String nombre, String codigo, TipoRecurso tipoRecurso,EstadoRecurso estado,LocalDateTime fechaAlta) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.tipoRecurso = tipoRecurso;
        this.estado=estado;
        this.fechaAlta = fechaAlta;
    }
    //contructor vacio asi spring crea los objetos
    public Recurso(){
    }
    //editar estado recurso
    public void setEstadoRecurso(EstadoRecurso estado){
        this.estado=estado;
    }

}
