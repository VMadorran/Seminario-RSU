package ar.edu.unrn.seminario.dto;


public class ResiduoDTO {
	private TipoResiduoDTO tipoResiduoDto;

	private int id;
	private float peso;
	private float pesoRetirado;

	public ResiduoDTO(TipoResiduoDTO tipoResiduoDto, float peso)  {
	
		this.tipoResiduoDto = tipoResiduoDto;

		this.peso = peso;

	}

	public ResiduoDTO(int id, TipoResiduoDTO tipoResiduoDto, float peso)  {


		this.tipoResiduoDto = tipoResiduoDto;
		this.id = id;
		this.peso = peso;

	}
	public ResiduoDTO(int id, TipoResiduoDTO tipoResiduo, float peso, float pesoRetirado)  {
	
		this.tipoResiduoDto = tipoResiduo;
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

	public ResiduoDTO(int peso) {

		this.peso = peso;

	}

	public TipoResiduoDTO getTipoResiduoDto() {
		return tipoResiduoDto;
	}

	public void setTipoResiduoDto(TipoResiduoDTO tipoResiduoDto) {
		this.tipoResiduoDto = tipoResiduoDto;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return tipoResiduoDto.toString();
	}

	

	



	public float getPesoRetirado() {
		return pesoRetirado;
	}

	public void setPesoRetirado(float pesoRetirado) {
		this.pesoRetirado = pesoRetirado;
	}
	

}