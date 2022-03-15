package ar.edu.unrn.seminario.accesos;


import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.modelo.Beneficio;

public interface BeneficioDao {
	
	void create(Beneficio beneficio) throws JdbcException;
	
	void update (Beneficio beneficio);
	
	Beneficio find(int id)throws JdbcException, NullException;
	
	List<Beneficio> findAll()throws  NullException, JdbcException;

}
