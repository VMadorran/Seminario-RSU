package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class UsuarioDAOJDBC implements UsuarioDao {

	@Override
	public void create(Usuario usuario) throws JdbcException {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO cuenta(usuario, contrasena, email, activo,rol) " + "VALUES (?, ?, ?, ?, ?)");

			statement.setString(1, usuario.getNombreUsuario());
			statement.setString(2, usuario.getContrasenia());
			statement.setString(3, usuario.getEmail());
			statement.setBoolean(4, usuario.estaActivo());
			statement.setInt(5, usuario.getRol().getCodigo());
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
		} finally {
			ConnectionManager.disconnect();
		}

	}

	@Override
	public void update(Usuario usuario) throws JdbcException {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement statement;
		try {
			statement = conn
					.prepareStatement("UPDATE cuenta SET activo= ? where usuario=?");

			statement.setBoolean(1, usuario.estaActivo());
			statement.setString(2, usuario.getNombreUsuario());

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



	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Usuario rol) {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario find(String username) throws JdbcException {
		Usuario usuario = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT u.usuario,  u.contrasena, u.email, r.codigo as codigo_rol, r.nombre as nombre_rol "
							+ " FROM cuenta u JOIN roles r ON (u.rol = r.codigo) " + " WHERE u.usuario = ?");

			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Rol rol = new Rol(rs.getInt("codigo_rol"), rs.getString("nombre_rol"));
				usuario = new Usuario(rs.getString("usuario"), rs.getString("contrasena"), rs.getString("email"), rol);
			}

		} catch (SQLException e) {
			throw new JdbcException("error de consulta");

		
		}  finally {
			ConnectionManager.disconnect();
		}

		return usuario;
	}

	@Override
	public List<Usuario> findAll() throws JdbcException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM `cuenta` join roles on cuenta.rol=roles.codigo");

			while (rs.next()) {

				Rol rol = new Rol(rs.getInt("codigo"), rs.getString("nombre"),rs.getBoolean("roles.activo"));
				Usuario usuario = new Usuario(rs.getString("usuario"), rs.getString("contrasena"),
						rs.getString("email"), rol,rs.getBoolean("cuenta.activo"));

				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}

		return usuarios;
	}


	}

