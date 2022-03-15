package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.TipoResiduo;

public interface ResiduosDao {

	List<TipoResiduo> findAll() throws NullException, JdbcException;

	public List<Residuo> findAllNoLevantados(int numeroOrden) throws  JdbcException, NullException;

	void updateResiduosPeso(List<Residuo> residuos) throws JdbcException;

}