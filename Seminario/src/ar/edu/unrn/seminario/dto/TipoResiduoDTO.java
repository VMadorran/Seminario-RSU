package ar.edu.unrn.seminario.dto;


public class TipoResiduoDTO {

	String nombre;

	int idTipo;
	int puntosResiduo;

	public TipoResiduoDTO(String nombre) {

		this.nombre = nombre;
	}

	public TipoResiduoDTO(int idTipo, String nombre, int puntosResiduos) {
	
		this.idTipo = idTipo;
		this.puntosResiduo = puntosResiduos;
		this.nombre = nombre;
	}



	public int getId() {
		return this.idTipo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String toString() {
		return " tipo: " + this.nombre + " puntos: " + this.puntosResiduo;
	}

	public int getPuntosResiduo() {
		return puntosResiduo;
	}

	public void setPuntosResiduo(int puntosResiduo) {
		this.puntosResiduo = puntosResiduo;
	}

}
