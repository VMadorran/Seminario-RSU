package ar.edu.unrn.seminario.accesos;

import java.util.Date;
import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.modelo.Propietario;

public interface PropietarioDao {

	void create(Propietario propietario) throws JdbcException, NullException;

	void update(Propietario propietario) throws JdbcException;

	void remove(Long id);

	void remove(Propietario propietario);

	Propietario find(String dni) throws JdbcException, NullException;

	List<Propietario> findAll() throws NullException, JdbcException;

	void updatePuntaje(int puntaje, int numeroPedido) throws JdbcException;

	int puntosAcumuladosPropietario(String id) throws JdbcException;// Consulta y devuelve los datos acumulados del
																	// propietario id

	void canjearBeneficio(String dni, int idBeneficio, Date fechaCanje) throws JdbcException;

}
