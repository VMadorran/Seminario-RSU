package ar.edu.unrn.seminario.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.unrn.seminario.exception.NullException;

public class CampaniaDTO {
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;
	private List<BeneficioDTO> catalogo = new ArrayList<BeneficioDTO>();
	private Integer idCampania;

	public CampaniaDTO(String nombre, Date fechaInicio, Date fechaFin, ArrayList<BeneficioDTO> catalogo, Integer idCampania) throws NullException {

		if (esDatoNulo(nombre))
			throw new NullException("Nombre vacio");
		if (esDatoNulo(fechaInicio))
			throw new NullException("Fecha de inicio vacia");
		if (esDatoNulo(fechaFin)) {
			throw new NullException("Fecha de finalizacion vacia");
	
		}
		this.fechaFin = fechaFin;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.catalogo = catalogo;
		this.idCampania = idCampania;
	}

	public CampaniaDTO(String nombre, Date fechaInicio, Date fechaFin)throws NullException {
        if (esDatoNulo(nombre))
            throw new NullException("Nombre vacio");
        if (esDatoNulo(fechaInicio))
            throw new NullException("Fecha de inicio vacia");
        if (esDatoNulo(fechaFin)) 
            throw new NullException("Fecha de finalizacion vacia");

            this.fechaFin = fechaFin;
            this.nombre = nombre;
            this.fechaInicio = fechaInicio;

    }
	private boolean esDatoNulo(String dato) {
		return dato == null | dato.isEmpty();
	}

	private boolean esDatoNulo(Date dato) {
		if (dato == null)
			return true;
		else
			return false;
	}
	
	public Date obtenerFechaFin() {
		return this.fechaFin;
	}
	
	public Date obtenerFechaInicio() {
		return this.fechaInicio;
	}
	
	public String obtenerNombre() {
		return this.nombre;
	}
	
	public Integer obtenerId() {
		return this.idCampania;
	}
	
	public List<BeneficioDTO> obtenerBeneficios(){
		return this.catalogo;
	}
	

}

