package ar.edu.unrn.seminario.dto;

import java.util.Date;
import java.util.List;

public class VisitaDTO {
	private Date diaConcurrido;
	private String observacion;
	private List<ResiduoDTO> residuos;

	public VisitaDTO(Date diaConcurrido, String observacion, List<ResiduoDTO> residuos) {

		this.diaConcurrido = diaConcurrido;
		this.observacion = observacion;
		this.residuos = residuos;
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

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public List<ResiduoDTO> getResiduos() {
		return residuos;
	}

	public void setResiduos(List<ResiduoDTO> residuos) {
		this.residuos = residuos;
	}

}
