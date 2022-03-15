package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.modelo.Direccion;


public class DireccionDAOJDBC implements DireccionDao {

	public void create(Direccion direccion)throws JdbcException {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO direccion(calle, numero, barrio) "
							+ "VALUES (?, ?, ?)");

			statement.setString(1, direccion.getCalle());
			statement.setInt(2, direccion.getNumero());
			statement.setString(3, direccion.getBarrio());
			
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
			// TODO: disparar Exception propia
		}  finally {
			ConnectionManager.disconnect();
		}

	}

	@Override
	public Direccion find(int id)throws JdbcException,NullException {
		Direccion direccion=null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT * FROM direccion AS d WHERE d.id=?");

			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				
				direccion=new Direccion(rs.getString("d.calle"),rs.getInt("d.numero"),rs.getString("d.barrio"), rs.getInt("d.id"));
			}

		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		}  finally {
			ConnectionManager.disconnect();
		}

		return direccion;
	}
	  
	
	
	@Override
	    public void update(Direccion direccion) throws JdbcException {
 
		  
	        try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement consulta=conn.prepareStatement("UPDATE direccion SET calle = ?, numero = ?, barrio = ? WHERE id = ?");
	            consulta.setString(1, direccion.getCalle());
	            consulta.setInt(2, direccion.getNumero());
	            consulta.setString(3, direccion.getBarrio());
	            consulta.setLong(4, direccion.getIdDireccion());
	            if(consulta.executeUpdate()>0) {

	            }
	            consulta.close();
	            conn.close();

	        } catch (SQLException e) {
	        	throw new JdbcException("error de consulta");
	        }  finally {
	            ConnectionManager.disconnect();
	        }


	    }

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Direccion direccion) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public List<Direccion> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
