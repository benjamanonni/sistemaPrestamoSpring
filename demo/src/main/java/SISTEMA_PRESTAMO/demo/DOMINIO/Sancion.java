package SISTEMA_PRESTAMO.demo.DOMINIO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Entity
@ToString
@Table(name = "Sanciones")
public class Sancion {
    @Column(name = "S_Id")
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer sancionId;

    @NotNull
    @OneToOne
    @JoinColumn(name="P_Id")
    private  Prestamo prestamo;

    @NotNull
    @ManyToOne
    @JoinColumn(name="U_Legajo")
    private Usuario usuario;

    @NotNull
    @Column(name ="S_Dia_Atraso")
    private  long diaAtraso;

    @NotNull
    @Column(name = "S_Fecha_Inicio_Bloqueo")
    private LocalDateTime fechaInicioBloqueo;

    @NotNull
    @Column(name = " S_Fecha_Fin_Bloqueo")
    private LocalDateTime fechaFinBloqueo;

    //contructor para registrar sancion
    public Sancion(Prestamo prestamo,Usuario usuario, long diaAtraso, LocalDateTime fechaInicioBloqueo, LocalDateTime fechaFinBloqueo){

        this.prestamo = prestamo;
        this.usuario=usuario;
        this.diaAtraso=diaAtraso;
        this.fechaInicioBloqueo=fechaInicioBloqueo;
        this.fechaFinBloqueo=fechaFinBloqueo;
    }
    public Sancion(){
    }
    public void SetIdSancion(Integer id){
        this.sancionId=id;
    }
}
