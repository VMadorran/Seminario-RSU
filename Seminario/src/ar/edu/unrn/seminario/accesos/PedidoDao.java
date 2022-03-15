package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.modelo.Pedido;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.Vivienda;

public interface PedidoDao {
	public void create(Vivienda vivienda, Pedido pedido) throws JdbcException;

	List<Pedido> findAll() throws NullException, JdbcException;

	Pedido find(int id_pedido) throws JdbcException, NullException;

	public List<Residuo> obtenerResiduos(int numeroPedido) throws JdbcException, NullException;

}
