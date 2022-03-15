package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;

public class LoginUsuarioDAOJDBC implements LoginUsuarioDao {

	public void cambiarEstadoSesion(boolean estado,int idCuenta) throws JdbcException{
		Connection conn = ConnectionManager.getConnection();
		
		try {
			PreparedStatement statementupdateUsuario = conn
					.prepareStatement("UPDATE cuenta SET cuenta.activo= ? where cuenta.id_cuenta=?");

			statementupdateUsuario.setBoolean(1, estado);
			statementupdateUsuario.setInt(2, idCuenta);
			

			int cantidad2 = statementupdateUsuario.executeUpdate();
			if (cantidad2 == 1) {
				System.out.println("se a creado correctamente");
			}

		} catch (SQLException e1) {
			throw new JdbcException("error de consulta");
		} finally {
			ConnectionManager.disconnect();
		}
	}
	@Override
	public boolean iniciarSesion(String username, String password) throws NullException, JdbcException {
		int idCuenta=0;
		boolean seEncuentra = false;
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT * FROM cuenta " + " WHERE (usuario=? AND contrasena =?)",PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			
			
			if (rs.next()) {
				if (rs.getString("usuario").equals(username) && (rs.getString("contrasena").equals(password))) {
					seEncuentra=true;
					idCuenta=rs.getInt("id_cuenta");
					
				}

			}
			if(seEncuentra==true)
			cambiarEstadoSesion(seEncuentra,idCuenta);
			
		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		}

		finally {
			ConnectionManager.disconnect();
		}
		
		return seEncuentra;
	}



}



