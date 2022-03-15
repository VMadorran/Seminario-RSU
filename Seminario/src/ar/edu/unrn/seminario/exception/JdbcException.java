package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class JdbcException extends Exception{

	public JdbcException(String mensaje) {
		super(mensaje);
	}

	
}
