package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.modelo.Propietario;


public class PropietarioDAOJDBC implements PropietarioDao {
	public void create(Propietario propietario)throws JdbcException {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO propietario(nombre, apellido, dni) " + "VALUES (?, ?, ?)");

			statement.setString(1, propietario.getNombre());
			statement.setString(2, propietario.getApellido());
			statement.setString(3, propietario.getDni());

			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new JdbcException("error en la consulta");
			// TODO: disparar Exception propia
		}  finally {
			ConnectionManager.disconnect();
		}

	}


    @Override
    public void update(Propietario propietario) throws JdbcException {

    	System.out.print("NOMBRE: " + propietario.getNombre() + "APELLIDO:" + propietario.getApellido()
    	+ "DNI:" + propietario.getDni());

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement consulta=conn.prepareStatement(
            		"UPDATE propietario SET nombre = ?,"
            		+ "apellido = ? WHERE dni = ?");
            consulta.setString(1,propietario.getNombre());
            consulta.setString(2,propietario.getApellido());
            consulta.setString(3, propietario.getDni());
           if(consulta.executeUpdate()>0) {

            }
            consulta.close();
            conn.close();

        } catch (SQLException e) {
           

            throw new JdbcException("error de consulta");
        } finally {
            ConnectionManager.disconnect();
        }


    }

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Propietario propietario) {
		// TODO Auto-generated method stub

	}

	@Override
	public Propietario find(String dni) throws JdbcException, NullException {
		Propietario propietario = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT * FROM `propietario`"
							+ "WHERE propietario.dni = ?");

			statement.setString(1, dni);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				propietario = new Propietario(rs.getString("propietario.nombre"), rs.getString("propietario.apellido"),
						rs.getString("propietario.dni"));
			}

		} catch (SQLException e) {
			

			throw new JdbcException("error de consulta");
		} finally {
			ConnectionManager.disconnect();
		}

		return propietario;
	}

	@Override
	public List<Propietario> findAll() throws NullException,JdbcException{

		List<Propietario> propietarios = new ArrayList<Propietario>();
		Statement sentencia = null;
		ResultSet resultado = null;
		try {

			sentencia = ConnectionManager.getConnection().createStatement();
			resultado = sentencia.executeQuery(
					"SELECT * FROM `propietario`" );

			while (resultado.next()) {
				Propietario propietario = new Propietario(resultado.getString("propietario.nombre"),
						resultado.getString("propietario.apellido"), resultado.getString("propietario.dni"));

				propietarios.add(propietario);
			}
		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.disconnect();
		}

		return propietarios;
			

	}

	
	@Override
	public void canjearBeneficio(String dni, int idBeneficio, Date fechaCanje) throws JdbcException {
		// TODO Auto-generated method stub
		ResultSet resultadoBeneficio = null;
		int puntos = 0;
		int puntosBeneficio = 0;

		try {
//__________________________________________________________________________________________________________________________________________________
				Connection connP = ConnectionManager.getConnection();
				PreparedStatement sentenciaPuntos = connP.prepareStatement("SELECT puntaje FROM propietario"
						+ " WHERE dni =?");

				sentenciaPuntos.setString(1,dni);
				resultadoBeneficio = sentenciaPuntos.executeQuery();

			if (resultadoBeneficio.next()) {
				puntos =resultadoBeneficio.getInt("puntaje");
			}
//__________________________________________________________________________________________________________________________________________________
			// conseguir los puntos del beneficio
			Connection connB = ConnectionManager.getConnection();
			PreparedStatement resultadoB = connB.prepareStatement("SELECT puntaje FROM beneficio where id_beneficio=?");

			resultadoB.setInt(1, idBeneficio);
			ResultSet rsB = resultadoB.executeQuery();
			if (rsB.next()) {
				puntosBeneficio = rsB.getInt("puntaje");

			}
			
			puntos = puntos- puntosBeneficio;
			
			System.out.println("Puntos de despues del canje:" + puntos);
			
			// realizar el update
//___________________________________________________________________________________________________________________________________________________________
			Connection connU = ConnectionManager.getConnection();
			String query = "UPDATE propietario SET puntaje = ? where dni = ?";
			PreparedStatement preparedStmt = connU.prepareStatement(query);
			preparedStmt.setInt(1, puntos);
			preparedStmt.setString(2, dni);

			int cantidad = preparedStmt.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				throw new JdbcException("error de consulta");
				
			}
//___________________________________________________________________________________________________________________________________________________________
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = 
					conn.prepareStatement("INSERT INTO beneficio_canjeado (dni_propietario, id_beneficio, fecha)" 
			+ "VALUES (?, ?, ?)");
			statement.setString(1, dni);
			statement.setInt(2, idBeneficio);
			statement.setDate(3,new java.sql.Date(fechaCanje.getTime()));
			int cantidadA = statement.executeUpdate();
			if (cantidadA > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				throw new JdbcException("error de consulta");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e1) {
			throw new JdbcException("error de consulta");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
	}
	
	
	public void updatePuntaje(int puntaje, int numeroPedido) throws JdbcException {
		Statement sentencia = null;
		ResultSet rs = null;
		//Pedido pedido = null;
		String dni = null;
		int puntajeTenia = 0;
		try {
			sentencia = ConnectionManager.getConnection().createStatement();

			rs = sentencia.executeQuery(
					"SELECT * FROM pedido_retiro join vivienda on pedido_retiro.numero_vivienda=vivienda.numero_vivienda join propietario on propietario.dni=vivienda.dni_propietario\n"
							+ "where pedido_retiro.id_pedido=" + numeroPedido);

			if (rs.next()) {
				dni = rs.getString("propietario.dni");
				puntajeTenia = rs.getInt("Propietario.puntaje");
			}
			rs.close();
			sentencia.close();
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE `propietario` SET puntaje=? WHERE propietario.dni=?");
			statement.setInt(1, puntaje + puntajeTenia);
			statement.setString(2, dni);

			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				System.out.println("Modificando " + cantidad + " registros");
			} else {
				throw new JdbcException("error de consulta");
			}

		} catch (

		SQLException e) {
			throw new JdbcException("error de consulta");
		}
		
	}
	
	
	@Override
	public int puntosAcumuladosPropietario(String id) throws JdbcException {
		// TODO Auto-generated method stub
		int puntos = 0;

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement("SELECT puntaje FROM propietario" + " WHERE dni = ?");
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				puntos = rs.getInt("puntaje");
			}
		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		}

		return puntos;
	}
	
	

}
