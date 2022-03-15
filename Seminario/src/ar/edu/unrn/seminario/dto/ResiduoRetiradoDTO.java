package ar.edu.unrn.seminario.dto;

public class ResiduoRetiradoDTO {

	private TipoResiduoDTO tipo;
	private float cantidad;
	
	public ResiduoRetiradoDTO(TipoResiduoDTO tipo, float cantidad) {
		
		this.tipo= tipo;
		this.cantidad= cantidad;
	}
}
