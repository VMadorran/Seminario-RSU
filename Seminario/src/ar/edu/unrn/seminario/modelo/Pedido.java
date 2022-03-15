package ar.edu.unrn.seminario.modelo;

import java.util.Date;
import java.util.List;

import ar.edu.unrn.seminario.exception.NullException;

public class Pedido {

	private Vivienda vivienda; // se obtiene mediante el numero de vivienda
	private Date fecha;
	private List<Residuo> residuo;//ResiduoARetirar
	private boolean vehiculo; // true: se requiere vehículo de carga pesa, tratar con checkbox en vista
	private String observacion;
	private int idPedidoRetiro;

	public Pedido(Vivienda vivienda, Date fecha, List<Residuo> residuo, boolean vehiculo, String observacion)
			throws NullException {

		if (esDatoVacio(vivienda))
			throw new NullException("vivienda");

		if (esDatoVacio(fecha))
			throw new NullException("fecha");

		if (esDatoVacio(residuo))
			throw new NullException("residuo");

		if (esDatoVacio(vehiculo))
			throw new NullException("vehiculo");

		if (esDatoVacio(observacion))
			throw new NullException("observacion vacia");
		this.vivienda = vivienda;
		this.observacion = observacion;
		this.vehiculo = vehiculo;
		this.fecha = fecha;
		this.residuo = residuo;
	}

	public Pedido(int idPedido, Vivienda vivienda) throws NullException {

		if (esDatoVacio(idPedido))
			throw new NullException("id pedido");
		this.idPedidoRetiro = idPedido;
	}

	public Pedido(int idPedidoRetiro, Vivienda vivienda, Date fecha, List<Residuo> residuo, boolean vehiculo,
			String observacion) throws NullException {
		if (esDatoVacio(idPedidoRetiro))
			throw new NullException("idpedido");
		if (esDatoVacio(vivienda))
			throw new NullException("vivienda");

		if (esDatoVacio(fecha))
			throw new NullException("fecha");

		if (esDatoVacio(residuo))
			throw new NullException("residuo");

		if (esDatoVacio(vehiculo))
			throw new NullException("vehiculo");

		if (esDatoVacio(observacion))
			throw new NullException("observacion vacia");
		this.vehiculo = vehiculo;
		this.observacion = observacion;
		this.vivienda = vivienda;
		this.residuo = residuo;
		this.fecha = fecha;
		this.idPedidoRetiro = idPedidoRetiro;
	}

	public Pedido(int idPedidoRetiro, Date fecha, List<Residuo> residuo, boolean vehiculo, String observacion)
			throws NullException {
		if (esDatoVacio(idPedidoRetiro))
			throw new NullException("idpedido");

		if (esDatoVacio(fecha))
			throw new NullException("fecha");

		if (esDatoVacio(residuo))
			throw new NullException("residuo");

		if (esDatoVacio(vehiculo))
			throw new NullException("vehiculo");

		if (esDatoVacio(observacion))
			throw new NullException("observacion");
		this.vehiculo = vehiculo;
		this.observacion = observacion;

		this.residuo = residuo;
		this.fecha = fecha;
		this.idPedidoRetiro = idPedidoRetiro;
	}
	public Pedido(int nroPedido) {
		this.idPedidoRetiro = nroPedido;
	}

	private boolean esDatoVacio(String dato) {
		return dato.equals("");
	}

	private boolean esDatoVacio(Object dato) {
		return dato == null;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public List<Residuo> getResiduo() {
		return this.residuo;
	}

	public String getObservacion() {
		return observacion;
	}

	public boolean getVehiculo() {
		return this.vehiculo;
	}

	public Vivienda getVivienda() {
		// TODO Auto-generated method stub
		return this.vivienda;
	}

	public int getidPedidoRetiro() {
		// TODO Auto-generated method stub
		return this.idPedidoRetiro;
	}

	public void setVivienda(Vivienda vivienda) {
		this.vivienda = vivienda;
	}

}
