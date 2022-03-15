package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;

import ar.edu.unrn.seminario.modelo.Usuario;

public interface UsuarioDao {
	void create(Usuario Usuario) throws JdbcException;

	void update(Usuario Usuario) throws JdbcException;

	void remove(Long id);

	void remove(Usuario Usuario);

	Usuario find(String username) throws JdbcException;

	List<Usuario> findAll() throws JdbcException;
	

}
