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
import ar.edu.unrn.seminario.modelo.Campania;



public class CampaniaDAOJDBC implements CampaniaDao {

	@Override
	public void create(Campania campania, List<Beneficio> beneficios) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionManager.getConnection();
		ResultSet result = null;
		try {
			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO campania(nombre, fecha_inicio, fecha_fin)" + "VALUES (?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, campania.obtenerNombre());
			statement.setDate(2, new java.sql.Date(campania.obtenerFechaInicio().getTime()));
			statement.setDate(3, new java.sql.Date(campania.obtenerFechaFin().getTime()));

			int cantidad = statement.executeUpdate();

			result = statement.getGeneratedKeys();
			result.next();

			int idCampania = result.getInt(1);

			PreparedStatement statement2 = conn
					.prepareStatement("INSERT INTO campania_beneficio( id_campania, id_beneficio)" + "VALUES(?,?)");

			//int cantidad1 = 0; VER
			for (Beneficio b : beneficios) {
				statement2.setInt(1, idCampania);
				statement2.setInt(2, b.obtenerIdBeneficio());
				//cantidad1 += statement2.executeUpdate();VER

			}
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			e.printStackTrace();
			// TODO: disparar Exception propia
		} catch (Exception e) {
			System.out.println("Error al insertar una campania " + e.getMessage());
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();

		}
	}

	@Override
	public void update(Campania campania) {
		// TODO Auto-generated method stub

	}

	public List<Beneficio> beneficiosDeCampania(int id) throws JdbcException, NullException {

		List<Beneficio> beneficios = new ArrayList<Beneficio>();
		
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement resultado = conn.prepareStatement("SELECT b.descripcion, b.puntaje, b.id_beneficio "
					+ "FROM campania_beneficio cb JOIN beneficio b ON (cb.id_beneficio = b.id_beneficio)"
					+ "WHERE cb.id_campania = ?");
			
			resultado.setInt(1, id);
			ResultSet rs = resultado.executeQuery();

			
			while(rs.next()) {
				Beneficio beneficio= new Beneficio (rs.getString("descripcion"), 
						rs.getInt("puntaje"),rs.getInt("id_beneficio"));
				System.out.println("Salida:"+"Descripcion: "+beneficio.obtenerDescripcion()+"Puntaje: " + beneficio.obtenerPuntaje() +"ID: "+ beneficio.obtenerIdBeneficio());
				beneficios.add(beneficio);
				
			}
			
			} catch (SQLException e) {
				System.out.println("Error de mySql\n" + e.toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} finally {
				ConnectionManager.disconnect();
		}
		return beneficios;
	}


	@Override
	public Campania find(int id) throws JdbcException, NullException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Campania campania = null;
		Statement sentencia = null;
		// Statement sentenciaBeneficio = null;

		ResultSet resultadoCampania = null;
		ResultSet resultadoBeneficio = null;
		try {

			sentencia = ConnectionManager.getConnection().createStatement();
			resultadoCampania = sentencia.executeQuery("SELECT c.nombre, c.fecha_inicio, c.fecha_fin, c.id_campania "
					+ "FROM campania_beneficio cb JOIN campania c ON (cb.id_campania = ?)");

			while (resultadoCampania.next()) {
				int idCampania = resultadoCampania.getInt("c.id_campania");
				ArrayList<Beneficio> beneficios = new ArrayList<Beneficio>();

				Connection conn = ConnectionManager.getConnection();
				PreparedStatement sentenciaBeneficio = conn
						.prepareStatement("SELECT b.descripcion, b.puntaje, b.id_beneficio "
								+ "FROM campania_beneficio cb JOIN beneficio b ON (cb.id_beneficio = b.id_beneficio) "
								+ "WHERE cb.id_campania=?");

				sentenciaBeneficio.setInt(1, idCampania);
				resultadoBeneficio = sentenciaBeneficio.executeQuery();

				while (resultadoBeneficio.next()) {

					Beneficio beneficio = new Beneficio(resultadoBeneficio.getString("descripcion"),
							resultadoBeneficio.getInt("puntaje"), resultadoBeneficio.getInt(3));
					System.out.println("Salida:" + "Descripcion: " + beneficio.obtenerDescripcion() + "Puntaje: "
							+ beneficio.obtenerPuntaje() + "ID: " + beneficio.obtenerIdBeneficio());
					beneficios.add(beneficio);

				}

				campania = new Campania(resultadoCampania.getString("nombre"),
						resultadoCampania.getDate("fecha_inicio"), resultadoCampania.getDate("fecha_fin"), beneficios);

			}

		} catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.disconnect();
		}

		return campania;
	}

	@Override
	public List<Campania> findAll() throws  NullException,JdbcException {
		// TODO Auto-generated method stub
		List<Campania> campanias = new ArrayList<Campania>();
		Statement sentencia = null;
		// Statement sentenciaBeneficio = null;

		ResultSet resultadoCampania = null;
		// ResultSet resultadoBeneficio = null;
		try {

			sentencia = ConnectionManager.getConnection().createStatement();
			resultadoCampania = sentencia
					.executeQuery("SELECT nombre, fecha_inicio, fecha_fin, id_campania FROM campania");

			while (resultadoCampania.next()) {

				Campania campania = new Campania(resultadoCampania.getString("nombre"),
						resultadoCampania.getDate("fecha_inicio"), resultadoCampania.getDate("fecha_fin"),
						resultadoCampania.getInt("id_campania"));

				// System.out.println("Nombre Campaa:"+ campania.obtenerNombre());

				campanias.add(campania);

			}

		} catch (SQLException e) {
			throw new JdbcException("erorr de traer campanias");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.disconnect();
		}

		return campanias;
	}
	
	
	@Override
	public List<Campania> campaniasVigentes() throws  NullException, JdbcException {
		// TODO Auto-generated method stub
		List<Campania> campanias = new ArrayList<Campania>();
		Statement sentencia = null;
		// Statement sentenciaBeneficio = null;

		ResultSet resultadoCampania = null;
		// ResultSet resultadoBeneficio = null;
		try {

			sentencia = ConnectionManager.getConnection().createStatement();
			resultadoCampania = sentencia
					.executeQuery("SELECT nombre, fecha_inicio, fecha_fin, id_campania FROM campania");

			while (resultadoCampania.next()) {

				Campania campania = new Campania(resultadoCampania.getString("nombre"),
						resultadoCampania.getDate("fecha_inicio"), resultadoCampania.getDate("fecha_fin"),
						resultadoCampania.getInt("id_campania"));
				
				if(campania.estaVigente())
					campanias.add(campania);

			}

		} catch (SQLException e) {
			throw new JdbcException("error de consulta");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.disconnect();
		}

		return campanias;
	}

}
