package SISTEMA_PRESTAMO.demo.DOMINIO;

import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
@ToString
@Getter
@Entity
@Table(name = "Usuarios")
public class Usuario {
    @NotBlank
    @Id
    @Column(name="U_Legajo")
    private  String  legajo;
    @NotBlank
    @Column(name="U_Nombre")
    private String nombreCompleto;
    @NotNull
    @Column(name="U_Tipo_Usuario")
    @Enumerated(EnumType.STRING)//es importante que trabaje como String ya que en mysql no trabaja como int
    private TipoUsuario tipoUsuario;
    @NotBlank
    @Email
    @Column(name="U_Correo")
    private String correo;
    @Column(name = "u_bloqueado_hasta")
    private LocalDateTime bloqueadoHasta;

    //hibernate lo que hace es crear objetos de una forma automatizada para eso es necesario agregarle el contructor vacio
    public Usuario(String legajo, String nombreCompleto, TipoUsuario tipoUsuario, String correo,LocalDateTime bloqueadoHasta){
        //le pasamos validaciones
        this.legajo = legajo;
        this.nombreCompleto = nombreCompleto;
        this.tipoUsuario = tipoUsuario;
        this.correo = correo;
        this.bloqueadoHasta=bloqueadoHasta;
    }
    //contructor con parametros y validaciones lo usamos al registrarUsuario antes de pasarlo a spring
    public Usuario(){
    }

    public boolean isbloqueado(){
        //si existe fecha de bloqueado y todavia falta para que llegue a esa fecha
        if(bloqueadoHasta!=null && bloqueadoHasta.isAfter(LocalDateTime.now())){
            return true;//esta bloqueado
        }
        return false;//no esta bloqueado
    }

    public void setBloqueadoHasta(LocalDateTime bloqueadoHasta) {
        this.bloqueadoHasta = bloqueadoHasta;
    }
}
