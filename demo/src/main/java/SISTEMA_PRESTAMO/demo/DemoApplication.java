package SISTEMA_PRESTAMO.demo;

import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.EstadoRecurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.TipoRecurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.ENUM.TipoUsuario;
import SISTEMA_PRESTAMO.demo.DOMINIO.Prestamo;
import SISTEMA_PRESTAMO.demo.DOMINIO.Recurso;
import SISTEMA_PRESTAMO.demo.DOMINIO.Sancion;
import SISTEMA_PRESTAMO.demo.DOMINIO.Usuario;
import SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES.IPrestamoServicio;
import SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES.IRecursoServicio;
import SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES.ISancionServicio;
import SISTEMA_PRESTAMO.demo.SERVICIO.INTERFACES.IUsuarioServicio;
import SISTEMA_PRESTAMO.demo.SERVICIO.PrestamoServicio;
import SISTEMA_PRESTAMO.demo.SERVICIO.RecursoServicio;
import SISTEMA_PRESTAMO.demo.SERVICIO.SancionServicio;
import SISTEMA_PRESTAMO.demo.SERVICIO.UsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@Autowired
	//declaramos las variables para usar metodos de servicio,la declaramos como su interfaces
	private IUsuarioServicio usuarioServicio;
	@Autowired
	private ISancionServicio sancionServicio;
	@Autowired
	private IRecursoServicio recursoServicio;
	@Autowired
	private IPrestamoServicio prestamoServicio;

	//creamos el objeto logger para imprimir mensajes
	private static final Logger logger= LoggerFactory.getLogger(DemoApplication.class);

	//1-registrar nuevo usuario
	public void registrarNuevoUsuario(){
		usuarioServicio.registrarNuevoUsuario("a3","gusty",TipoUsuario.ALUMNO,"benjaAndres@gmail.com");
	}
	//2-listar Usuarios
	public void listarUsuarios(){
		List<Usuario> lista = usuarioServicio.listarUsuarios();
		//1-primero para no entrar en un for inecesario
		if(lista.isEmpty()){
			logger.info("no hay usuarios registrados");
		}
		for (Usuario u : lista) {
			logger.info("{}",u);
		}
	}
	//3-listar usuario por legajo
	public void listarUsuarioPorLegajo(String legajo){
		Usuario u = usuarioServicio.listarUsuarioPorLegajo(legajo);
		if (u == null) {
			throw new IllegalStateException("no se encontro usuario");
		}
		logger.info("{}",u);
	}

	//--------Recurso-------------
	//1-registrar nuevo recurso
	public void registrarNuevoRecurso(){
		recursoServicio.registrarNuevoRecurso("libro","111", TipoRecurso.LIBRO);
	}

	//2-listar recursos por estado
	public void listarRecursosPorEstado(EstadoRecurso estado){
		List<Recurso>recursos=recursoServicio.listarRecursoEstado(estado);
		if (recursos.isEmpty()) {
			logger.info("no existen recurso con ese estado");
			return;
		}
		for (Recurso r : recursos) {
			logger.info("{}",r);
		}
	}

	//3-listar recurso por codigo
	public void listarRecursoPorCodigo(String codigo){
		Recurso r=recursoServicio.listarRecursoCodigo(codigo);
		if (r==null) {
			throw new IllegalStateException("no se encontro recurso con codigo ");
		}
		logger.info("{}",r);
	}

	//4-listar recurso por tipo
	public void listarRecursosTipo(TipoRecurso tipoRecurso){
		List<Recurso>recursos=recursoServicio.listarRecursoTipo(tipoRecurso);
		if (recursos.isEmpty()) {
			logger.info("no se encontraron recurso de este tipo");
			return;
		}
		for(Recurso r:recursos) {
			logger.info("{}",r);
		}
	}

	//5-cambiar estado de recurso
	public void cambiarEstadoRecurso(){
		recursoServicio.cambiarEstadoRecurso("1",EstadoRecurso.DISPONIBLE);
	}

	//6-listar recursos activos
	public void listarRecursosActivos(){
		List<Recurso>recursos=recursoServicio.listarRecursosActivos();
		if (recursos.isEmpty()) {
			logger.info("no hay recursos disponibles");
			return;
		}
		for (Recurso r : recursos) {
			logger.info("{}",r);
		}
	}

	//7-listar recursos por descripcion
	public void listarRecursosDescripcion(String descripcion){
		List<Recurso>recursos=recursoServicio.listarRecursoNombre(descripcion);
		if (recursos.isEmpty()) {
			logger.info("no hay recursos que contengan esa descripcion");
			return;
		}
		for (Recurso r : recursos) {
			logger.info("{}",r);
		}
	}

	//----------PRESTAMO------------
	//1-registrar nuevo prestamo
	LocalDateTime fecha = LocalDateTime.of(2026, 4, 1, 15, 30, 45);
	public void registrarNuevoPrestamo(){
		prestamoServicio.registrarNuevoPrestamo("a","12",fecha);
	}
	//2-listar prestamos
	public void listarPrestamos(){
		List<Prestamo>prestamos=prestamoServicio.listarPrestamos();
		if (prestamos.isEmpty()) {
			logger.info("no hay prestamos activos");
		}
		for (Prestamo p : prestamos) {
			logger.info("{}",p);
		}
	}

	//3-listar prestamos por usuario
	public void listarPrestamosPorUsuario(String legajo){
		List<Prestamo>prestamos = prestamoServicio.listarPrestamoPorUsuario(legajo);
		if(prestamos.isEmpty()){
			logger.info("no tiene ningun prestamo ese usuario");
			return;
		}
		for(Prestamo p:prestamos){
			logger.info("{}",p);
		}
	}

	//4-listar prestamos por recurso
	public void listarPrestamosPorRecurso(String codigoRecurso){
		List<Prestamo>prestamos = prestamoServicio.listarPrestamoPorRecurso(codigoRecurso);
		if(prestamos.isEmpty()){
			logger.info("no tiene ningun prestamo ese recurso");
			return;
		}
		for(Prestamo p:prestamos){
			logger.info("{}",p);
		}
	}

	//5-listar prestamo por id
	public void listarPrestamoPorId(Integer id){
		Prestamo p=prestamoServicio.listarPrestamoPorId(id);

		if(p==null){
			logger.info("no existe prestamo con ese id");
			return;
		}
		logger.info("{}",p);
	}

	//6-listar prestamos por vencido
	public void listarPrestamosPorVencido(){
		List<Prestamo>prestamos=prestamoServicio.listarPrestamosVencidos();
		if (prestamos.isEmpty()) {
			logger.info("no hay prestamos vencidos");
			return;
		}
		for (Prestamo p : prestamos) {
			logger.info("{}",p);
		}
	}

	//7-registrar devolucion de prestamo
	public void registrarDevolucionDePrestamo(Integer id) {
		prestamoServicio.registrarDevolucion(id);
		logger.info("prestamo devuelto con exito");
	}

	//8-listar historial de recurso
	public void listarHistorialDeRecurso(String idRecurso){
		List<Prestamo>prestamos=prestamoServicio.listarHistorialRecurso(idRecurso);
		if(prestamos.isEmpty()){
			logger.info("este recurso nunca fue usado en un prestamo");
			return;
		}
		for (Prestamo p:prestamos){
			logger.info("{}",p);
		}
	}

	//-----------SANCIONES----------
	//1-listar sanciones
	public void listarSanciones(){
		List<Sancion>sanciones=sancionServicio.listarSanciones();
		if(sanciones.isEmpty()){
			logger.info("no existen sanciones");
			return;
		}
		for(Sancion s:sanciones){
			logger.info("{}",s);
		}
	}
	//2-listar sanciones por usuario
	public void listarSancionesPorUsuarios() {
		List<Sancion> sanciones = sancionServicio.listarUsuariosbloqueados();
		if (sanciones.isEmpty()) {
			logger.info("no existen sanciones");
			return;
		}
		for (Sancion s : sanciones) {
                logger.info("legajo {} ,fecha de fin de bloqueo {}", s.getUsuario().getLegajo(), s.getFechaFinBloqueo());
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("------APLICACION INCIALIZADA-------");
	}

	//nuestro metodo que comienza corriendo spring
	@Override
	public void run(String... args) throws Exception {
		listarPrestamosPorRecurso("1");
	}
}
