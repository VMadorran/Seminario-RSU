package ar.edu.unrn.seminario.accesos;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.modelo.OrdenDePedido;
import ar.edu.unrn.seminario.modelo.Visita;

public interface VisitaDao {
	public void create(Visita visita, OrdenDePedido orden) throws JdbcException;
}
