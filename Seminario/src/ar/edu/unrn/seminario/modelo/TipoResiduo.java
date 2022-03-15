package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.NullException;

public class TipoResiduo {

	private String nombre;

	private int idTipo;
	private int puntosResiduos;

	public TipoResiduo(String nombre) throws NullException {

		

		this.nombre = nombre;

	}

	public TipoResiduo(int idTipo, String nombre, int puntosResiduos) throws NullException {

		this.puntosResiduos = puntosResiduos;
		this.idTipo = idTipo;

		this.nombre = nombre;

	}

	private boolean esVacio(String dato) {
		return dato.isEmpty() || dato == "";
	}

	private boolean esVacio(int dato) {
		return dato == 0;
	}

	public int getId() {
		return this.idTipo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getPuntosResiduos() {
		return puntosResiduos;
	}

	public void setPuntosResiduos(int puntosResiduos) {
		this.puntosResiduos = puntosResiduos;
	}

}
