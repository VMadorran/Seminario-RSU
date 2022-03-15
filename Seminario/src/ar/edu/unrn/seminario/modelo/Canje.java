package ar.edu.unrn.seminario.modelo;

import java.util.Date;

import ar.edu.unrn.seminario.exception.NullException;

public class Canje {
	private Date fechaCanje;
	private Beneficio beneficioCanjeado;

	
	public Canje(Date fechaCanje,Beneficio beneficioCanjeado ) throws NullException {
		
		if (this.esDatoVacio(fechaCanje))
			throw new NullException("Fecha vacia");
		if (this.esDatoVacio(beneficioCanjeado))
			throw new NullException("Beneficio vacio");
		this.beneficioCanjeado=beneficioCanjeado;
		this.fechaCanje= fechaCanje;
		
	}
	
	private boolean esDatoVacio(Object dato) {
		return dato == null;
	}
	

}
