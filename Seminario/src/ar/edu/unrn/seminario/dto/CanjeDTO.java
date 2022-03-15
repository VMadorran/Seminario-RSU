package ar.edu.unrn.seminario.dto;

import java.util.Date;

import ar.edu.unrn.seminario.modelo.Beneficio;

public class CanjeDTO {

	private Date fechaCanje;
	private Beneficio beneficioCanjeado;
	
	public CanjeDTO(Date fechaCanje, Beneficio beneficioCanjeado) {;
	
		this.beneficioCanjeado= beneficioCanjeado;
		this.fechaCanje = fechaCanje;
	}
	
	
	

}
