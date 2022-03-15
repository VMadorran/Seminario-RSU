package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.modelo.Recolector;

public interface RecolectorDao {

	void create(Recolector recolector) throws JdbcException;

	void update(Recolector recolector);

	void remove(Long id);

	void remove(Recolector recolector);

	List<Recolector> findAll() throws NullException, JdbcException;

	Recolector find(String legajo) throws JdbcException, NullException;
}
