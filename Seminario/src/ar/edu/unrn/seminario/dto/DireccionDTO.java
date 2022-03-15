package ar.edu.unrn.seminario.dto;


public class DireccionDTO {
	private String calle;
	private int numero;
	private String barrio;
	private int idDireccion;

	public DireccionDTO(String calle, int numero, String barrio, int idDireccion)  {

	
		this.calle = calle;
		this.numero = numero;
		this.barrio = barrio;
		this.idDireccion = idDireccion;
	}

	public DireccionDTO(String calle, int numero, String barrio)  {

	
		this.calle = calle;
		this.numero = numero;
		this.barrio = barrio;
	}

	public String getCalle() {
		return calle;
	}

	public int getNumero() {
		return numero;
	}

	

	public String getBarrio() {
		return barrio;
	}


	public int getIdDireccion() {
		return this.idDireccion;
	}
	
	public void setCalle(String calle) {
        this.calle= calle;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
}
