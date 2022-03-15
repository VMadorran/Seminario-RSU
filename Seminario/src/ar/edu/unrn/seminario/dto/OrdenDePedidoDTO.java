package ar.edu.unrn.seminario.dto;

import java.util.Date;
import java.util.List;

import ar.edu.unrn.seminario.modelo.OrdenDePedido;

public class OrdenDePedidoDTO {
	private Date fechaPedido;
	private int id;
	private RecolectorDTO recolectorBasura;
	private List<VisitaDTO> visitaVivienda;
	private static final String PENDIENTE = "PENDIENTE";
	private static final String EJECUCION = "EJECUCION";
	private static final String CONCRETADO = "CONCRETADO";
	private static final String CANCELADO = "CANCELADO";
	private PedidoDTO pedido;
	private String estado;

	public OrdenDePedidoDTO(Date fechaPedido, RecolectorDTO recolector, PedidoDTO pedido)  {
	
		this.fechaPedido = fechaPedido;
		this.recolectorBasura = recolector;
		this.pedido = pedido;

	}

	public OrdenDePedidoDTO(Date fechaPedido, PedidoDTO pedido)  {

	
		this.estado=this.PENDIENTE;
		this.fechaPedido = fechaPedido;

		this.pedido = pedido;

	}

	public OrdenDePedidoDTO(Date fechaPedido, String estado, int id, RecolectorDTO recolector) {
	
		this.fechaPedido = fechaPedido;
		this.id = id;
		this.recolectorBasura = recolector;
		this.estado = estado;
	}

	public OrdenDePedidoDTO(Date fechaPedido, String estado, int id, PedidoDTO pedidoDto)  {
	
		this.fechaPedido = fechaPedido;
		this.id = id;

		this.estado = estado;
		this.pedido = pedidoDto;
	}

	public OrdenDePedidoDTO(Date fechaPedido, String estado, int id, RecolectorDTO recolector, PedidoDTO pedido)
			 {
	
		this.fechaPedido = fechaPedido;
		this.id = id;
		this.recolectorBasura = recolector;
		this.estado = estado;
		this.pedido = pedido;
	}

	public boolean isEmpty(Object dato) {
		return dato == null;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public RecolectorDTO getRecolectorBasura() {
		return recolectorBasura;
	}

	public void setRecolectorBasura(RecolectorDTO recolectorBasura) {
		this.recolectorBasura = recolectorBasura;
	}

	public List<VisitaDTO> getVisitaVivienda() {
		return visitaVivienda;
	}

public boolean ordenSeEncuentraConcretadaOCancelada()  {
		return ((this.estado.compareTo(this.CONCRETADO)==0) || (this.estado.compareTo(this.CANCELADO)==0) );
	}
public void cambiarEstadoCancelado() {
	
		if ((this.estado.compareTo(this.EJECUCION)==0) || (this.estado.compareTo(this.PENDIENTE)==0) ) {
			this.estado=this.CANCELADO;
		}
	

	
}

	public void setVisitaVivienda(List<VisitaDTO> visitaVivienda) {
		this.visitaVivienda = visitaVivienda;
	}

	public PedidoDTO getPedido() {
		return pedido;
	}

	public void setPedido(PedidoDTO pedido) {
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
		return estado;
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

}
