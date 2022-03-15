package ar.edu.unrn.seminario.modelo;

import java.util.List;

import ar.edu.unrn.seminario.exception.NullException;

public class Propietario {

	private String nombre;
	private String apellido;
	private String dni;
	private Usuario usuario;
	private int puntosAcumulados;
	private List<Canje> canjes;
	// private PedidoRetiro pedidoRetiro;

	public Propietario(String nombre, String apellido, String dni) throws NullException {
		// agregar expeciones
		if (esDatoNulo(nombre))
			throw new NullException("nombre");
		if (esDatoNulo(apellido))
			throw new NullException("apellido");
		if (esDatoNulo(dni))
			throw new NullException("dni");
		this.apellido = apellido;
		this.dni = dni;
		this.nombre = nombre;

	}

	private boolean esDatoNulo(String dato) {
		return dato == null | dato.isEmpty();
	}
	public boolean esDatoNulo(Object dato) {
		return dato == null;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getDni() {
		return dni;
	}
	
	public boolean tienePuntosSuficientes(int puntosBeneficio) {
		if(this.puntosAcumulados >= puntosBeneficio)
			return true;
		else
			return false;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getPuntosAcumulados() {
		return puntosAcumulados;
	}

	public void setPuntosAcumulados(int puntosAcumulados) {
		this.puntosAcumulados = puntosAcumulados;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public List<Canje> canjes(){
		return this.canjes;
	}

}
