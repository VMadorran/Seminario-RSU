package ar.edu.unrn.seminario.modelo;

import java.util.Date;
import java.util.List;

import ar.edu.unrn.seminario.exception.EstadoException;
import ar.edu.unrn.seminario.exception.NullException;

public class OrdenDePedido {

	private Date fechaPedido;
	private int id;
	private Recolector recolectorBasura;
	private List<Visita> visitaVivienda;
	private static final String PENDIENTE = "PENDIENTE";
	private static final String EJECUCION = "EJECUCION";
	private static final String CONCRETADO = "CONCRETADO";
	private static final String CANCELADO = "CANCELADO";
	private Pedido pedido;
	private String estado;

	public OrdenDePedido(Date fechaPedido, Recolector recolector, Pedido pedido) throws NullException {
		if (isEmpty(recolector)) {
			throw new NullException("recolector");
		}
		if (isEmpty(pedido)) {
			throw new NullException("pedido");
		}
		this.fechaPedido = fechaPedido;
		this.recolectorBasura = recolector;
		this.pedido = pedido;

	}

	public OrdenDePedido(Date fechaPedido, Recolector recolector, Pedido pedido, String estado) throws NullException {
		if (isEmpty(recolector)) {
			throw new NullException("recolector");
		}
		if (isEmpty(pedido)) {
			throw new NullException("pedido");
		}
		if (isEmpty(estado)) {
			throw new NullException("estado");
		}
		this.fechaPedido = fechaPedido;
		this.recolectorBasura = recolector;
		this.pedido = pedido;
		this.estado = estado;

	}

	public OrdenDePedido(Date fechaPedido, String estado, int id) throws NullException {

		if (isEmpty(estado)) {
			throw new NullException("estado");
		}
		if (isEmpty(id)) {
			throw new NullException("id");
		}
		this.fechaPedido = fechaPedido;
		this.id = id;

		this.estado = estado;

	}

	public OrdenDePedido(Date fechaPedido, Pedido pedido, String estado) throws NullException {

		if (isEmpty(pedido)) {
			throw new NullException("pedido");
		}
		if (isEmpty(estado)) {
			throw new NullException("estado");
		}
		this.fechaPedido = fechaPedido;
		this.pedido = pedido;
		this.estado = estado;

	}

	public OrdenDePedido(Date fechaPedido, String estado, int id, Recolector recolector) throws NullException {
		if (isEmpty(fechaPedido)) {
			throw new NullException("pedido");
		}
		if (isEmpty(estado)) {
			throw new NullException("estado");
		}
		if (isEmpty(id)) {
			throw new NullException("id");
		}
		if (isEmpty(recolector)) {
			throw new NullException("recolector");
		}

		this.fechaPedido = fechaPedido;
		this.id = id;
		this.recolectorBasura = recolector;
		this.estado = estado;
	}

	public OrdenDePedido(Date fechaPedido, String estado, int id, Recolector recolector, Pedido pedido)
			throws NullException {
		if (isEmpty(fechaPedido)) {
			throw new NullException("pedido");
		}
		if (isEmpty(estado)) {
			throw new NullException("estado");
		}
		if (isEmpty(id)) {
			throw new NullException("id");
		}
		if (isEmpty(recolector)) {
			throw new NullException("recolector");
		}
		if (isEmpty(pedido)) {
			throw new NullException("pedido");
		}
		this.fechaPedido = fechaPedido;
		this.id = id;
		this.recolectorBasura = recolector;
		this.estado = estado;
		this.pedido = pedido;
	}

	public Date getFechaPedido() {
		return this.fechaPedido;
	}

	public boolean isEmpty(Object dato) {
		return dato == null;
	}
	public boolean isEmpty(String dato) {
		return dato==null || dato.isEmpty();
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Recolector getRecolectorBasura() {
		return recolectorBasura;
	}

	public void setRecolectorBasura(Recolector recolectorBasura) {
		this.recolectorBasura = recolectorBasura;
	}

	public List<Visita> getVisitaVivienda() {
		return visitaVivienda;
	}

	public void setVisitaVivienda(List<Visita> visitaVivienda) {
		this.visitaVivienda = visitaVivienda;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public static String getPendiente() {
		return PENDIENTE;
	}

	public static String getEjecucion() {
		return EJECUCION;
	}

	public static String getConcretado() {
		return CONCRETADO;
	}

	public static String getCancelado() {
		return CANCELADO;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void cambiarEstadoEjecucion() throws EstadoException {
		if (this.estado.compareTo(this.PENDIENTE) == 0) {
			this.estado=this.EJECUCION;
		}
	
		
	}
	public boolean ordenSeEncuentraConcretadaOCancelada() throws EstadoException {
		
		boolean seEncuentra= false;
		
		if (this.estado.compareTo(this.PENDIENTE) == 0
				|| this.estado.compareTo(this.EJECUCION) == 0) {
			seEncuentra = true;

		} else if (this.estado.compareTo(this.CONCRETADO) == 0) {
			throw new EstadoException("La orden ya esta concretada");
		} else if (this.estado.compareTo(this.CANCELADO) == 0) {

			throw new EstadoException("La orden ya esta cancelada");
		}
		return seEncuentra;
	}
	public void cambiarEstadoConcretado() throws EstadoException {
		if( this.estado.compareTo(this.EJECUCION) == 0 || this.estado.compareTo(this.PENDIENTE) == 0) {
			this.estado=this.CONCRETADO;
		}
		else {
			throw new EstadoException("debe estar en estado ejecucion");
		}
		
		
	}
	public void cambiarEstadoCancelado() throws EstadoException {
		if(this.estado==this.CONCRETADO) {
			throw new EstadoException("no se puede cancelar ya que esta concretada");
		}
		else {
			this.estado=this.CANCELADO;
		}

		
	}
}
