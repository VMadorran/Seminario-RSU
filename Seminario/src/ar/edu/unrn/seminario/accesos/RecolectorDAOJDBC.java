package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.modelo.Recolector;

public class RecolectorDAOJDBC implements RecolectorDao {

	@Override
	public void create(Recolector recolector) throws JdbcException {
		// TODO Auto-generated method stub
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO recolector(nombre, apellido, email, dni, legajo)" + "VALUES(?,?,?,?,?)");

			statement.setString(1, recolector.obtenerNombre());
			statement.setString(2, recolector.obtenerApellido());
			statement.setString(3, recolector.obtenerEmail());
			statement.setString(4, recolector.obtenerDni());
			statement.setString(5, recolector.obtenerLegajo());
			//statement.setString(6, recolector.obtenerTurno());

			int cantidad = statement.executeUpdate();

			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				throw new JdbcException("error de consulta");
			}

		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
			// TODO: disparar Exception propia
		} catch (Exception e) {
			System.out.println("Error al insertar un recolector");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}

	}

	@Override
	public void update(Recolector recolector) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Recolector recolector) {
		// TODO Auto-generated method stub

	}

	@Override
	public Recolector find(String legajo) throws JdbcException, NullException {
		Recolector recolector = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT * from recolector" + " WHERE recolector.legajo = ?");

			statement.setString(1, legajo);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				recolector = new Recolector(rs.getString("recolector.nombre"), rs.getString("recolector.apellido"),
						rs.getString("recolector.email"), rs.getString("recolector.dni"),
						rs.getString("recolector.legajo"));
			}

		} catch (SQLException e) {
			

			throw new JdbcException("Error al procesar consulta");
		} 

			
		 finally {
			ConnectionManager.disconnect();
		}

		return recolector;
	}

	@Override
	public List<Recolector> findAll() throws NullException,JdbcException {
		// TODO Auto-generated method stub
		List<Recolector> recolectores = new ArrayList<Recolector>();
		Statement sentencia = null;
		ResultSet resultado = null;

		try {
			sentencia = ConnectionManager.getConnection().createStatement();
			resultado = sentencia
					.executeQuery("select r.nombre, r.apellido, r.email, r.dni, r.legajo from recolector r");
			while (resultado.next()) {
				Recolector recolector = new Recolector(resultado.getString(1), resultado.getString(2),
						resultado.getString(3), resultado.getString(4), resultado.getString(5));

				recolectores.add(recolector);

			}

		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.disconnect();
		}

		return recolectores;

	}

}
