package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.modelo.Direccion;


public interface DireccionDao {

	
	void create(Direccion direccion) throws JdbcException;

	void update(Direccion direccion) throws JdbcException;

	void remove(Long id);

	void remove(Direccion direccion);

	Direccion find(int id) throws JdbcException, NullException;

	List<Direccion> findAll();
}
