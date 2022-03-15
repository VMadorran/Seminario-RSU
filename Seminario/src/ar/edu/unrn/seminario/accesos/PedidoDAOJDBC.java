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
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Pedido;
import ar.edu.unrn.seminario.modelo.Propietario;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class PedidoDAOJDBC implements PedidoDao {

	public void create(Vivienda vivienda, Pedido pedido) throws JdbcException {
		Connection conn = ConnectionManager.getConnection();
		try {
			/*
			 * PreparedStatement statement2 = conn.prepareStatement(null,
			 * PreparedStatement.RETURN_GENERATED_KEYS); conn.setAutoCommit(false); if
			 * (pedido.getResiduo() != null) {
			 * 
			 * statement2.setInt(1, pedido.getResiduo().getPuntosKilo());
			 * 
			 * statement2.addBatch(); } int[] updateCounts = statement2.executeBatch(); if
			 * (updateCounts.length >= 1) { System.out.println("se a creado correctamente");
			 * } ResultSet rsResiduo = statement2.getGeneratedKeys(); rsResiduo.next(); int
			 * idResiduo = rsResiduo.getInt(1);
			 * 
			 * rsResiduo.close();
			 * 
			 * if (updateCounts.length > 0) {
			 * System.out.println("se a creado correctamente el resiudo"); } else {
			 * System.out.println("Error al actualizar"); // TODO: disparar Exception propia
			 * }
			 */

			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO pedido_retiro(fecha_del_pedido,observacion,vehiculo_pesado,numero_vivienda) "// falta
							// id_residuos,numero_vivienda
							+ "VALUES (?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			statement.setObject(1, pedido.getFecha());
			statement.setObject(2, pedido.getObservacion());
			statement.setObject(3, pedido.getVehiculo());
			statement.setObject(4, vivienda.getNumeroVivienda());
			int cantidad = statement.executeUpdate();
			ResultSet rsPedido = statement.getGeneratedKeys();
			rsPedido.next();
			int idPedido = rsPedido.getInt(1);
			if (cantidad == 1) {
				System.out.println("se a creado correctamente");
			}

			PreparedStatement statement3 = conn
					.prepareStatement("INSERT INTO residuo(id_pedido,id_tipo_residuo,peso) " + "VALUES (?,?,?)");
			conn.setAutoCommit(false);
			for (Residuo pedidoTipo : pedido.getResiduo()) {

				statement3.setInt(1, idPedido);
				statement3.setInt(2, pedidoTipo.getTipoResiduo().getId());
				statement3.setFloat(3, pedidoTipo.getPeso());
				statement3.addBatch();
			}
			int[] updateCountsPedidoResiduoTipo = statement3.executeBatch();
			if (updateCountsPedidoResiduoTipo.length == 1) {
				System.out.println("se a creado correctamente el tipoResiduo");
			}

			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new JdbcException("error de consulta");}
		
	 finally {
			ConnectionManager.disconnect();
		}

	}

	@Override
	public List<Pedido> findAll() throws NullException,JdbcException {
		List<Pedido> pedidos = new ArrayList<Pedido>();
		Statement sentencia = null;
		ResultSet rs = null;
		try {
			sentencia = ConnectionManager.getConnection().createStatement();

			rs = sentencia.executeQuery(
					"SELECT  vivienda.numero_vivienda,pedido_retiro.fecha_del_pedido, pedido_retiro.observacion,pedido_retiro.id_pedido,  pedido_retiro.vehiculo_pesado,\n"
							+ " direccion.barrio,direccion.calle,direccion.numero,\n"
							+ "propietario.nombre,propietario.apellido,propietario.dni\n"
							+ " from vivienda join pedido_retiro  on vivienda.numero_vivienda=pedido_retiro.numero_vivienda  \n"
							+ " join direccion on vivienda.id_direccion=direccion.id\n"
							+ " join propietario on propietario.dni=vivienda.dni_propietario");

			while (rs.next()) {
				Direccion direccion = new Direccion(rs.getString("barrio"), rs.getInt("numero"), rs.getString("calle"));

				// String nombre, String apellido, String dni
				Propietario propietario = new Propietario(rs.getString("nombre"), rs.getString("apellido"),
						rs.getString("dni"));

				// int numeroVivienda,Propietario propietario, Direccion direccion
				Vivienda vivienda = new Vivienda(rs.getInt("numero_vivienda"), propietario, direccion);

				// String tipoResiduo,int puntosKilo

				// Vivienda vivienda, LocalDate fecha, ArrayList<Residuo> residuos, boolean
				// vehiculo, String observacion

				List<Residuo> residuos = new ArrayList<>();
				Statement sentenciaResiduos = ConnectionManager.getConnection().createStatement();
				ResultSet rsResiduos = sentenciaResiduos.executeQuery(
						"SELECT residuo.peso,pedido_retiro.id_pedido,pedido_retiro.fecha_del_pedido,residuo.id,tipo_residuo.id,tipo_residuo.tipo_residuo,tipo_residuo.puntos_kilo\n"
								+ "from residuo join pedido_retiro on residuo.id_pedido=pedido_retiro.id_pedido join\n"
								+ " tipo_residuo on residuo.id_tipo_residuo=tipo_residuo.id\n"
								+ "where residuo.id_pedido=" + rs.getInt("pedido_retiro.id_pedido"));

				while (rsResiduos.next()) {
					TipoResiduo tipoResiduo = new TipoResiduo(rsResiduos.getInt("tipo_residuo.id"),
							rsResiduos.getString("tipo_residuo.tipo_residuo"),
							rsResiduos.getInt("tipo_residuo.puntos_kilo"));
					Residuo residuo = new Residuo(rsResiduos.getInt("residuo.id"), tipoResiduo,
							rsResiduos.getFloat("peso"));
					residuos.add(residuo);
				}

				rsResiduos.close();

				// Vivienda vivienda, LocalDate fecha, ArrayList<Residuo> residuos, boolean
				// vehiculo, String observacion
				// LocalTime por Date en fecha
				Pedido pedido = new Pedido(rs.getInt("pedido_retiro.id_pedido"), vivienda,
						rs.getDate("fecha_del_pedido"), residuos, rs.getBoolean("vehiculo_pesado"),
						rs.getString("observacion"));

				pedidos.add(pedido);

			}

		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.disconnect();
		}

		try {
			rs.close();
		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		}
		return pedidos;
	}

	@Override
	public Pedido find(int idPedido) throws JdbcException, NullException {// fecha_del_pedido,observacion,vehiculo_pesado,numero_vivienda,id_residuos

		Statement sentencia = null;
		ResultSet rs = null;
		Pedido pedido = null;
		try {
			sentencia = ConnectionManager.getConnection().createStatement();

			rs = sentencia.executeQuery("SELECT * FROM `pedido_retiro` WHERE pedido_retiro.id_pedido =" + idPedido);

			if (rs.next()) {

				List<Residuo> residuos = new ArrayList<>();
				Statement sentenciaResiduos = ConnectionManager.getConnection().createStatement();
				ResultSet rsResiduos = sentenciaResiduos.executeQuery(
						"SELECT residuo.id, residuo.peso,tipo_residuo.id,tipo_residuo.tipo_residuo,tipo_residuo.puntos_kilo\n"
								+ "from residuo join pedido_retiro on residuo.id_pedido=pedido_retiro.id_pedido join\n"
								+ " tipo_residuo on residuo.id_tipo_residuo=tipo_residuo.id\n"
								+ " where residuo.id_pedido=" + idPedido);
				while (rsResiduos.next()) {
					TipoResiduo tipoResiduo = new TipoResiduo(rsResiduos.getInt("tipo_residuo.id"),
							rsResiduos.getString("tipo_residuo.tipo_residuo"),
							rsResiduos.getInt("tipo_residuo.puntos_kilo"));
					Residuo residuo = new Residuo(rsResiduos.getInt("residuo.id"), tipoResiduo,
							rsResiduos.getFloat("peso"));
					residuos.add(residuo);
				}
				rsResiduos.close();
				// Vivienda vivienda, LocalDate fecha, ArrayList<Residuo> residuos, boolean
				// vehiculo, String observacion
				// LocalTime por Date en fecha
				pedido = new Pedido(rs.getInt("pedido_retiro.id_pedido"), rs.getDate("fecha_del_pedido"), residuos,
						rs.getBoolean("vehiculo_pesado"), rs.getString("observacion"));

			}

		} catch (SQLException e) {
			throw new JdbcException("error de consulta");

		} finally {
			ConnectionManager.disconnect();
		}
		return pedido;
	}

	public List<Residuo> obtenerResiduos(int numeroPedido) throws JdbcException, NullException {
		Statement sentencia = null;
		ResultSet rsResiduos = null;
		List<Residuo> residuos = new ArrayList<>();
		try {
			sentencia = ConnectionManager.getConnection().createStatement();

			rsResiduos = sentencia.executeQuery(
					"SELECT * FROM `residuo` join tipo_residuo on residuo.id_tipo_residuo=tipo_residuo.id WHERE residuo.id_pedido ="
							+ numeroPedido);
			while (rsResiduos.next()) {
				TipoResiduo tipoResiduo = new TipoResiduo(rsResiduos.getInt("tipo_residuo.id"),
						rsResiduos.getString("tipo_residuo.tipo_residuo"),
						rsResiduos.getInt("tipo_residuo.puntos_kilo"));
				Residuo residuo = new Residuo(rsResiduos.getInt("residuo.id"), tipoResiduo,
						rsResiduos.getFloat("peso"));
				residuos.add(residuo);
			}
		} catch (SQLException e) {
			throw new JdbcException("error de consulta");

		}finally {
			ConnectionManager.disconnect();
		}
		return residuos;
	}
}