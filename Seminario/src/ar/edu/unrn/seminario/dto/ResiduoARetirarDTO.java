package ar.edu.unrn.seminario.dto;

public class ResiduoARetirarDTO {
	
	private TipoResiduoDTO tipo;
	private float cantidad;
	
	public ResiduoARetirarDTO(TipoResiduoDTO tipo, float cantidad) {
		
		this.tipo= tipo;
		this.cantidad= cantidad;
	}
	
}
