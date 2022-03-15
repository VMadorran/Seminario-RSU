package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.NullException;


public class Residuo {// ver si hace falta o borrar
	private TipoResiduo tipoResiduo;//ResiduoARetirar
	private int id;
	private float peso;//
	private float pesoRetirado;//
	public Residuo(TipoResiduo tipoResiduo, float peso) throws NullException {
		if (esDatoVacio(tipoResiduo))
			throw new NullException("es nulo tipo residuo");

		this.tipoResiduo = tipoResiduo;

		this.peso = peso;

	}

	public Residuo(int id, TipoResiduo tipoResiduo, float peso) throws NullException {
		if (esDatoVacio(tipoResiduo))
			throw new NullException("es nulo tipo residuo");

		if (esDatoVacio(id))
			throw new NullException("el numero es 0");
		this.tipoResiduo = tipoResiduo;
		this.id = id;
		this.peso = peso;

	}
	public Residuo(int id, TipoResiduo tipoResiduo, float peso, float pesoRetirado) throws NullException {
		if (esDatoVacio(tipoResiduo))
			throw new NullException("es nulo tipo residuo");

		if (esDatoVacio(id))
			throw new NullException("el numero es 0");
		this.tipoResiduo = tipoResiduo;
		this.id = id;
		this.peso = peso;
		this.pesoRetirado = pesoRetirado;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoResiduo getTipoResiduo() {
		return tipoResiduo;
	}

	public void setTipoResiduo(TipoResiduo tipoResiduo) {
		this.tipoResiduo = tipoResiduo;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
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

	public float getPesoRetirado() {
		return pesoRetirado;
	}

	public void setPesoRetirado(float pesoRetirado) {
		this.pesoRetirado = pesoRetirado;
	}
	

}