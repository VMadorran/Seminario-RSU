package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.modelo.OrdenDePedido;
import ar.edu.unrn.seminario.modelo.Recolector;

public interface OrdenDao {
	public void create(OrdenDePedido ordenPedido) throws JdbcException;

	boolean seEncuentraOrden(int numeroPedido) throws JdbcException;

	void asignarRecolector(Recolector recolector) throws JdbcException;

	public List<OrdenDePedido> findAll() throws JdbcException, NullException;

	public OrdenDePedido find(int numeroOrden) throws JdbcException, NullException;



	public void cambiarEstadoOrden(int numeroOrden,String estado) throws JdbcException;

	boolean estaInCompleto(int orden) throws JdbcException;

	OrdenDePedido findPorAdministrador(int numeroPedido) throws JdbcException, NullException;

}