package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.modelo.OrdenDePedido;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.Visita;

public class VisitaDAOJDBC implements VisitaDao {

	@Override
	public void create(Visita visita, OrdenDePedido orden) throws JdbcException {
        Connection conn = ConnectionManager.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO visita (dia_concurrido, observacion,legajo_recolector,id_orden)" + " values(?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setObject(1, visita.getDiaConcurrido());
            statement.setString(2, visita.getObservacion());
            statement.setString(3, orden.getRecolectorBasura().obtenerLegajo());
            statement.setInt(4, orden.getId());
            int cantidad = statement.executeUpdate();
            ResultSet rsVisita = statement.getGeneratedKeys();
            rsVisita.next();
            int idVisita = rsVisita.getInt(1);
            if (cantidad > 0) {
                // System.out.println("Modificando " + cantidad + " registros");
            } else {
            	throw new JdbcException("error de consulta");
              
            }
            updateResiduo(visita);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	private void updateResiduo(Visita visita) throws JdbcException {//Ver y reformular
        Connection conn = ConnectionManager.getConnection();

        try {
            conn.setAutoCommit(false);
            PreparedStatement updateResiduos = conn.prepareStatement("UPDATE residuo r SET peso=? where r.id =?");
            for (Residuo r : visita.getResiduos()) {
                updateResiduos.setFloat(1, r.getPeso());
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
    }


}
