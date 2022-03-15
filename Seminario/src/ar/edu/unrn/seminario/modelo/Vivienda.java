package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;

import ar.edu.unrn.seminario.exception.NullException;

public class Vivienda {

	private Propietario propietario;
	private LocalDateTime fechaRegistro;
	private Direccion direccion;
	private int numeroVivienda;

	public Vivienda(int numeroVivienda, Propietario propietario, Direccion direccion) throws NullException {
		if(esDatoVacio(numeroVivienda))
			throw new NullException("el numero de vivienda es vacio");
		if(esDatoVacio(direccion))
			throw new NullException("la direccion es vacio");
		if(esDatoVacio(propietario))
			throw new NullException("el propietario es vacio");
		this.propietario = propietario;
		
		this.direccion = direccion;
		
		this.numeroVivienda = numeroVivienda;
	}

	public Vivienda(Propietario propietario, Direccion direccion) throws NullException {
		if(esDatoVacio(direccion))
			throw new NullException("la direccion es vacio");
		if(esDatoVacio(propietario))
			throw new NullException("el propietario es vacio");
		this.propietario = propietario;

		this.direccion = direccion;

	}

	public Vivienda(int numeroVivienda) {
		this.numeroVivienda = numeroVivienda;
	}

	public Propietario getPropietario() {
		return propietario;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public int getNumeroVivienda() {
		return this.numeroVivienda;
	}
	
	private boolean esDatoVacio(String dato) {
		return dato == null | dato.isEmpty();
	}

	private boolean esDatoVacio(Object dato) {
		return dato == null;
	}

	private boolean esDatoVacio(int dato) {
		return dato == 0;
	}

	private boolean esDatoVacio(float dato) {
		return dato == 0;
	}


	// to string? equals?
}
