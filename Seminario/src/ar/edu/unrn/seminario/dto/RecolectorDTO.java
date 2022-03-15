package ar.edu.unrn.seminario.dto;

import java.util.ArrayList;

import ar.edu.unrn.seminario.modelo.Visita;

public class RecolectorDTO {
	private String nombre;
	private String apellido;
	private String email;
	private String turno;
	private String legajo;
	private String dni;
	private ArrayList<Visita> visitasDelRecolector = new ArrayList<Visita>();
	
	public RecolectorDTO(String nombre, String apellido, String email, String dni, String legajo)  {
		super();
	
		
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.dni = dni;
		this.legajo = legajo;
		//this.turno = turno;
	}
	
	
	public void agregarVisita(Visita nueva) {
		this.visitasDelRecolector.add(nueva);
	}

	public void agregarLegajo(String legajo) {
		this.legajo = legajo;
	}

	public String obtenerLegajo() {
		return this.legajo;
	}

	public void cambiarNombre(String nombre) {
		this.nombre = nombre;
	}

	public String obtenerNombre() {
		return this.nombre;
	}

	public void cambiarApellido(String apellido) {
		this.apellido = apellido;
	}

	public String obtenerApellido() {

		return this.apellido;
	}

	public void cambiarEmail(String email) {
		this.email = email;
	}

	public String obtenerEmail() {
		return this.email;
	}

	public void cambiarTurno(String turno) {
		this.turno = turno;
	}

	public String obtenerTurno() {
		return this.turno;
	}
	
	public String obtenerDni() {
		return this.dni;
	}
}


