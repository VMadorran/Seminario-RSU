package ar.edu.unrn.seminario.accesos;


import java.util.List;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.modelo.Beneficio;
import ar.edu.unrn.seminario.modelo.Campania;


public interface CampaniaDao {

	void create(Campania campania, List<Beneficio> beneficios);
	
	void update (Campania campania);
	
	Campania find(int id) throws JdbcException, NullException;
	
	List<Campania> findAll()throws  NullException, JdbcException;
	
	List<Beneficio> beneficiosDeCampania(int id) throws JdbcException, NullException;
	
	List<Campania> campaniasVigentes() throws  NullException, JdbcException ;

}
