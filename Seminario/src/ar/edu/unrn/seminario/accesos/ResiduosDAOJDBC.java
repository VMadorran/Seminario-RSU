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
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.TipoResiduo;

public class ResiduosDAOJDBC implements ResiduosDao {

	public ArrayList<TipoResiduo> findAll() throws NullException, JdbcException {
		ArrayList<TipoResiduo> tipoResiduo = new ArrayList<TipoResiduo>();
		Statement sentencia = null;
		ResultSet resultado = null;
		try {

			sentencia = ConnectionManager.getConnection().createStatement();
			resultado = sentencia.executeQuery("select id,tipo_residuo,puntos_kilo from tipo_residuo");

			while (resultado.next()) {

				TipoResiduo residuo = new TipoResiduo(resultado.getInt("id"), resultado.getString("tipo_residuo"),
						resultado.getInt("puntos_kilo"));
				tipoResiduo.add(residuo);
			}
		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.disconnect();
		}

		return tipoResiduo;
	}

	public List<Residuo> findAllNoLevantados(int numeroOrden) throws JdbcException,NullException {

		List<Residuo> residuosNoLevantados = new ArrayList<>();
		Statement sentencia = null;
		ResultSet resultado = null;
		
			try {
				sentencia = ConnectionManager.getConnection().createStatement();
			
			
				resultado = sentencia
						.executeQuery("SELECT * from residuo r join pedido_retiro pr on  r.id_pedido=pr.id_pedido "
								+ "join orden_retiro ord on pr.id_orden =ord.id "
								+ "join tipo_residuo t on t.id=r.id_tipo_residuo \n" + "WHERE ord.id =" + numeroOrden
								+ " and r.peso>0");
		

			
				while (resultado.next()) {
					
						Residuo residuo = new Residuo(
								resultado.getInt("r.id"), new TipoResiduo(resultado.getInt("t.id"),
										resultado.getString("t.tipo_residuo"), resultado.getInt("t.puntos_kilo")),
								resultado.getInt("r.peso"));
						residuosNoLevantados.add(residuo);
					
				}
			} catch (SQLException e) {
				throw new JdbcException("no se hizo correctamente la consulta");
			}
			finally {
				ConnectionManager.disconnect();
			}
		
			
		
		return residuosNoLevantados;

	}
	@Override
	public void updateResiduosPeso(List<Residuo> residuos) throws JdbcException {

		Connection conn = ConnectionManager.getConnection();

		try {
			conn.setAutoCommit(false);
			PreparedStatement updateResiduos = conn
					.prepareStatement("UPDATE residuo r SET peso_retirado=? where r.id =?");
			for (Residuo r : residuos) {
				updateResiduos.setFloat(1, r.getPesoRetirado());
				updateResiduos.setInt(2, r.getId());

				updateResiduos.addBatch();
			}
			int[] updateCountsResiduosLevantados = updateResiduos.executeBatch();
			if (updateCountsResiduosLevantados.length > 0) {
				System.out.println("se a creado correctamente el levantado");
			}

			conn.commit();
			conn.setAutoCommit(true);

			// Array array = conn.createArrayOf("integer", ids.toArray());
			// updateResiduos.setArray(1, array);

		} catch (

		SQLException e) {
			throw new JdbcException("error de consulta");
		}
		finally {
			ConnectionManager.disconnect();
		}
		

	}

}