package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.modelo.Rol;

public interface RolDao {
	void create(Rol rol) throws JdbcException;

	void update(Rol rol);

	void remove(Long id);

	void remove(Rol rol);

	Rol find(Integer codigo) throws JdbcException, NullException;

	List<Rol> findAll() throws JdbcException, NullException;

}
