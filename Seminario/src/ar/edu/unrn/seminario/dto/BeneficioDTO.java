package ar.edu.unrn.seminario.dto;


public class BeneficioDTO {
	private String descripcion;
	private Integer puntaje;
	private Integer idBeneficio;

	
	public BeneficioDTO(String descripcion, Integer puntaje, int idBeneficio)  {
	
		
		this.descripcion = descripcion;
		this.puntaje = puntaje;
		this.idBeneficio= idBeneficio;
	}
	public BeneficioDTO(String descripcion, Integer puntaje) {
  
        this.descripcion = descripcion;
        this.puntaje = puntaje;

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
