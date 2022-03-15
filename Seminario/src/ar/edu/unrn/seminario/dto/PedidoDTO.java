package ar.edu.unrn.seminario.dto;

import java.util.Date;
import java.util.List;


public class PedidoDTO {

	private ViviendaDTO vivienda; // se obtiene mediante la direccion
	private Date fecha;

	private boolean vehiculo; // true: se requiere vehículo de carga pesa, tratar con checkbox en vista
	private String observacion;
	private int nroPedido;
	private List<ResiduoDTO> residuoDto;

	public PedidoDTO(ViviendaDTO vivienda, Date fecha, List<ResiduoDTO> residuoDto, boolean vehiculo,
			String observacion){



		this.vivienda = vivienda;
		this.fecha = fecha;
		this.residuoDto = residuoDto;
		this.vehiculo = vehiculo;
		this.observacion = observacion;

	}

	public PedidoDTO(ViviendaDTO vivienda, Date fecha, List<ResiduoDTO> residuoDto, boolean vehiculo,
			String observacion, int nroPedido) {


		this.vivienda = vivienda;
		this.fecha = fecha;
		this.residuoDto = residuoDto;
		this.vehiculo = vehiculo;
		this.observacion = observacion;
		this.nroPedido = nroPedido;
	}
	public PedidoDTO( Date fecha, List<ResiduoDTO> residuoDto, boolean vehiculo,
			String observacion, int nroPedido) {


	
		this.fecha = fecha;
		this.residuoDto = residuoDto;
		this.vehiculo = vehiculo;
		this.observacion = observacion;
		this.nroPedido = nroPedido;
	}
	
	public PedidoDTO(int nroPedido) {
		this.nroPedido = nroPedido;
	}
	public DireccionDTO getDireccion() {
		return this.vivienda.getDireccion();
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public PropietarioDTO getPropietario() {
		return this.vivienda.getPropietario();
	}



	public Date getFecha() {
		return fecha;
	}

	public String getObservacion() {
		return observacion;
	}

	public int getNumeroPedido() {
		return this.nroPedido;
	}

	public ViviendaDTO obtenerVivienda() {
		return vivienda;
	}

	public boolean getVehiculo() {
		return this.vehiculo;
	}

	public List<ResiduoDTO> getResiduoDto() {
		return residuoDto;
	}

	public void setResiduoDto(List<ResiduoDTO> residuoDto) {
		this.residuoDto = residuoDto;
	}

}