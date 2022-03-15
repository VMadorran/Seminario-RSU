package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.NullException;

public class Beneficio {
	private String descripcion;
	private Integer puntaje;
	private Integer idBeneficio;


	public Beneficio(String descripcion, Integer puntaje) throws NullException {

		if (esDatoNulo(descripcion))
			throw new NullException("Descripcion vacia");
		if (esDatoNulo(puntaje))
			throw new NullException("Puntaje vacio");
		this.descripcion = descripcion;
		this.puntaje = puntaje;
	}
	
	public Beneficio(String descripcion, Integer puntaje, int idBeneficio) throws NullException {

		if (esDatoNulo(descripcion))
			throw new NullException("Descripcion vacia");
		if (esDatoNulo(puntaje))
			throw new NullException("Puntaje vacio");
		this.descripcion = descripcion;
		this.puntaje = puntaje;
		this.idBeneficio = idBeneficio;
	}
	

	private boolean esDatoNulo(String dato) {
		return dato == null | dato.isEmpty();
	}

	private boolean esDatoNulo(Integer dato) {
		if (dato == null)
			return true;
		else
			return false;

	}
	
	public String obtenerDescripcion() {
		return this.descripcion;
	}

	public Integer obtenerPuntaje() {
		return this.puntaje;
	}
	public Integer obtenerIdBeneficio() {
		return this.idBeneficio;
	}
}
