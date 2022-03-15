package ar.edu.unrn.seminario.modelo;

import java.util.Date;
import java.util.List;

import ar.edu.unrn.seminario.exception.NullException;

public class Visita {
	private Date diaConcurrido;
	private String observacion;
	private List<Residuo> residuos;//ResiduoRetirado

	public Visita(Date diaConcurrido, String observacion, List<Residuo> residuos) throws NullException{
		if(esDatoVacio(diaConcurrido))
			throw new NullException("dia es vacio");
		if(esDatoVacio(observacion))
			throw new NullException("observacion es vacio");
		if(esDatoVacio(residuos))
			throw new NullException("residuos es vacio");
		this.diaConcurrido = diaConcurrido;
		this.observacion = observacion;
		this.residuos = residuos;
	}

	public void agregarObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getDiaConcurrido() {
		return diaConcurrido;
	}

	public void setDiaConcurrido(Date diaConcurrido) {
		this.diaConcurrido = diaConcurrido;
	}

	public String getObservacion() {
		return observacion;
	}

	public List<Residuo> getResiduos() {
		return residuos;
	}

	public void setResiduos(List<Residuo> residuos) {
		this.residuos = residuos;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

}
