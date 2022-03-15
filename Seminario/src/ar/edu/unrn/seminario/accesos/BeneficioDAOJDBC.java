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
import ar.edu.unrn.seminario.modelo.Beneficio;


public class BeneficioDAOJDBC implements BeneficioDao {

	@Override
	public void create(Beneficio beneficio)throws JdbcException {
		// TODO Auto-generated method stub
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO beneficio (descripcion, puntaje)" + "VALUES(?,?)", 
							Statement.RETURN_GENERATED_KEYS );

			statement.setString(1, beneficio.obtenerDescripcion());
			statement.setInt(2, beneficio.obtenerPuntaje());
			
			int cantidad = statement.executeUpdate();
			
			ResultSet miResult = statement.getGeneratedKeys();
			miResult.next();
			
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				throw new JdbcException("Consulta invalida");
				
			}
	


		} catch (SQLException e) {
			throw new JdbcException("Consulta invalida");
		} finally {
			ConnectionManager.disconnect();
		}

	}

	@Override
	public void update(Beneficio beneficio) {
		// TODO Auto-generated method stub

	}

	@Override
	public Beneficio find(int id) throws JdbcException,NullException{
		// TODO Auto-generated method stu

		Beneficio beneficio= null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement resultado = conn.prepareStatement("SELECT descripcion, puntaje, id_beneficio FROM beneficio where id_beneficio=?");
			
			resultado.setInt(1, id);
			ResultSet rs = resultado.executeQuery();
			if (rs.next()) {
				beneficio = new Beneficio(rs.getString("descripcion"), rs.getInt("puntaje"), rs.getInt("id_beneficio"));

			}
		} catch (SQLException e) {
			
			 throw new JdbcException("error consulta");
		} finally {
			ConnectionManager.disconnect();
			}
		System.out.println("Busqueda del beneficio:" +beneficio.obtenerDescripcion()+beneficio.obtenerPuntaje()+beneficio.obtenerIdBeneficio());
		
					
		return beneficio;
	}

	@Override
	public List<Beneficio> findAll() throws JdbcException, NullException{
		// TODO Auto-generated method stub
		List<Beneficio> beneficios = new ArrayList<Beneficio>();
	
		Statement sentencia= null;
		ResultSet resultado= null;	
		
		try {
			sentencia = ConnectionManager.getConnection().createStatement();
			resultado = sentencia.executeQuery("SELECT descripcion, puntaje, id_beneficio FROM beneficio");
			
			
			
			while(resultado.next()) {
				Beneficio beneficio= new Beneficio (resultado.getString("descripcion"), resultado.getInt("puntaje"),resultado.getInt(3));
				System.out.println("Salida:"+"Descripcion: "+beneficio.obtenerDescripcion()+"Puntaje: " + beneficio.obtenerPuntaje() +"ID: "+ beneficio.obtenerIdBeneficio());
				beneficios.add(beneficio);
				
			}
			
			} catch (SQLException e) {
				throw new JdbcException("error consulta");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} finally {
				ConnectionManager.disconnect();
		}
		return beneficios;
	}

}
