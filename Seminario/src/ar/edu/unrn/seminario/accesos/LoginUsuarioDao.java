package ar.edu.unrn.seminario.accesos;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;

public interface LoginUsuarioDao {

	boolean iniciarSesion(String username, String password) throws NullException, JdbcException;

	public void cambiarEstadoSesion(boolean estado,int idCuenta) throws JdbcException;
}
