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
import ar.edu.unrn.seminario.modelo.OrdenDePedido;
import ar.edu.unrn.seminario.modelo.Pedido;
import ar.edu.unrn.seminario.modelo.Recolector;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class OrdenDAOJDBC implements OrdenDao {

	private void Update(int idOrdenPedido, int ordenPedido) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement("UPDATE `pedido_retiro` SET `id_orden`= ? where id_pedido=?");

			statement.setInt(1, idOrdenPedido);
			statement.setInt(2, ordenPedido);
			int cantidad2 = statement.executeUpdate();
			if (cantidad2 == 1) {
				System.out.println("se updateo correctamente el orden");
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public boolean seEncuentraOrden(int numeroPedido) throws JdbcException {
		Connection conn = ConnectionManager.getConnection();

		Statement sentencia = null;
		ResultSet rs = null;
		boolean seEncuentra = false;
		try {
			conn.setAutoCommit(false);
			sentencia = ConnectionManager.getConnection().createStatement();
			rs = sentencia.executeQuery(
					"SELECT * from pedido_retiro join orden_retiro on pedido_retiro.id_orden=orden_retiro.id\n"
							+ "where pedido_retiro.id_pedido=" + numeroPedido);

			if (rs.next()) {
				int idOrden = rs.getInt("orden_retiro.id");
				int idordenPedido1 = rs.getInt("pedido_retiro.id_orden");
				if (idOrden == idordenPedido1) {
					seEncuentra = true;
				}
			}
			rs.close();
			return seEncuentra == true;

		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		}  finally {

			ConnectionManager.disconnect();
		}
	}

	public List<OrdenDePedido> findAll()throws JdbcException,NullException {
		List<OrdenDePedido> ordenesDePedido = new ArrayList<>();
		Statement sentencia = null;
		ResultSet resultado = null;
		try {
			sentencia = ConnectionManager.getConnection().createStatement();
			resultado = sentencia.executeQuery(
					"select * from orden_retiro join pedido_retiro on pedido_retiro.id_orden=orden_retiro.id join recolector on recolector.legajo = orden_retiro.legajo_recolector");
			while (resultado.next()) {
				
				ordenesDePedido.add(new OrdenDePedido(resultado.getDate("fecha_pedido"),
						resultado.getString("estado_orden"), resultado.getInt("id"),
						new Recolector(resultado.getString("nombre"), resultado.getString("apellido"),
								resultado.getString("email"), resultado.getString("dni"),
								resultado.getString("recolector.legajo")),
						new Pedido(resultado.getInt("pedido_retiro.id_pedido"))));
			}
		}  catch (SQLException e) {
			throw new JdbcException("error de consulta");
		}
		 finally {

				ConnectionManager.disconnect();
			}
		

		return ordenesDePedido;

	}
	public void create(OrdenDePedido ordenPedido) throws JdbcException {

		Connection conn = ConnectionManager.getConnection();

		try {

			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO orden_retiro(fecha_pedido, estado_orden) VALUES (?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setObject(1, ordenPedido.getFechaPedido());
			statement.setString(2, ordenPedido.getEstado());
			int cantidad = statement.executeUpdate();
			ResultSet rsOrden = statement.getGeneratedKeys();
			rsOrden.next();
			int idOrdenPedido = rsOrden.getInt(1);
			rsOrden.close();
			if (cantidad == 1) {
				System.out.println("se a creado correctamente el orden");
			}

			Update(idOrdenPedido, ordenPedido.getPedido().getidPedidoRetiro());

		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
			// TODO: disparar Exception propia
		}  finally {

			ConnectionManager.disconnect();
		}
	}

	public void asignarRecolector(Recolector recolector) throws JdbcException {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement("UPDATE `orden_retiro` SET `legajo_recolector`= ?");

			statement.setString(1, recolector.obtenerLegajo());

			int cantidad2 = statement.executeUpdate();
			if (cantidad2 == 1) {
				System.out.println("se a creado correctamente");
			}

		} catch (SQLException e1) {
			throw new JdbcException("error de consulta");
		}finally {
			ConnectionManager.disconnect();
		}
		
	}

	public void cambiarEstadoOrden(int numeroOrden,String estado) throws JdbcException {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement statement;
		try {
			statement = conn
					.prepareStatement("UPDATE orden_retiro SET orden_retiro.estado_orden= ? where orden_retiro.id=?");

			statement.setString(1, estado);
			statement.setInt(2, numeroOrden);

			int cantidad2 = statement.executeUpdate();
			if (cantidad2 == 1) {
				System.out.println("se a creado correctamente");
			}

		} catch (SQLException e1) {
			throw new JdbcException("error de consulta");
		} finally {
			ConnectionManager.disconnect();
		}
	}

	
	public boolean estaInCompleto(int orden) throws JdbcException {
		int count = 0;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT count(ord.id) as cantidad from orden_retiro ord join pedido_retiro pr on ord.id=pr.id_orden join residuo r on pr.id_pedido=r.id_pedido \n"
							+ " WHERE ord.id =?  and r.peso>0");
			statement.setInt(1, orden);

			ResultSet completo = statement.executeQuery();
			if (completo.next()) {
				count = completo.getInt("cantidad");
			}

		} catch (

		SQLException e) {
			throw new JdbcException("error de consulta");
		} finally {
			ConnectionManager.disconnect();
		}
		return count > 0;
	}
	
	
private Pedido obtenerPedido(int numeroOrden) throws NullException, JdbcException {
	Pedido pedido=null;
	try {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement statement = conn.prepareStatement(
				"SELECT * from pedido_retiro join vivienda on vivienda.numero_vivienda=pedido_retiro.numero_vivienda join propietario on propietario.dni=vivienda.dni_propietario where pedido_retiro.id_orden=?");

		statement.setInt(1, numeroOrden);
		ResultSet rs = statement.executeQuery();
		if (rs.next()) {
		pedido=new Pedido(rs.getInt("pedido_retiro.id_pedido"),
				new Vivienda(rs.getInt("vivienda.numero_vivienda")));
		}

	} catch (SQLException e) {
		throw new JdbcException("error de consulta");
	} finally {
		ConnectionManager.disconnect();
	}
	return pedido;
	
}
	public OrdenDePedido find(int numeroOrden) throws JdbcException, NullException {
		OrdenDePedido orden = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT * from orden_retiro join recolector on orden_retiro.legajo_recolector=recolector.legajo   WHERE orden_retiro.id = ?");

			statement.setInt(1, numeroOrden);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				orden = new OrdenDePedido(rs.getDate("orden_retiro.fecha_pedido"),
						rs.getString("orden_retiro.estado_orden"), rs.getInt("orden_retiro.id"),
						new Recolector(rs.getString("recolector.nombre"), rs.getString("recolector.apellido"),
								rs.getString("recolector.email"), rs.getString("recolector.dni"),
								rs.getString("recolector.legajo"))
					);
			}

		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		} finally {
			ConnectionManager.disconnect();
		}
		Pedido pedido=obtenerPedido(numeroOrden);
		orden.setPedido(pedido);
		
		return orden;

	}

	public OrdenDePedido findPorAdministrador(int numeroPedido) throws JdbcException, NullException {
		OrdenDePedido orden = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT * from  orden_retiro join pedido_retiro on pedido_retiro.id_orden=orden_retiro.id where pedido_retiro.id_pedido=?");

			statement.setInt(1, numeroPedido);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				orden = new OrdenDePedido(rs.getDate("orden_retiro.fecha_pedido"),
						rs.getString("orden_retiro.estado_orden"), rs.getInt("orden_retiro.id"));
			}

		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		} finally {
			ConnectionManager.disconnect();
		}

		return orden;

	}

}