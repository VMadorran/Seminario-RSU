package ar.edu.unrn.seminario.modelo;
import ar.edu.unrn.seminario.herramienta.Fecha;
import java.util.ArrayList;
import java.util.Date;
import ar.edu.unrn.seminario.exception.NullException;

public class Campania {
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;
	private ArrayList<Beneficio> catalogo = new ArrayList<Beneficio>();
	private int idCampania;

	public Campania(String nombre, Date fechaInicio, Date fechaFin) throws NullException {

		if (esDatoNulo(nombre))
			throw new NullException("Nombre vacio");
		if (esDatoNulo(fechaInicio))
			throw new NullException("Fecha de inicio vacia");
		if (esDatoNulo(fechaFin)) {
			throw new NullException("Fecha de finalizacion vacia");
		}
		this.fechaFin = fechaFin;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
	}
	
	public Campania(String nombre, Date fechaInicio, Date fechaFin, int idCampania) throws NullException {

		if (esDatoNulo(nombre))
			throw new NullException("Nombre vacio");
		if (esDatoNulo(fechaInicio))
			throw new NullException("Fecha de inicio vacia");
		if (esDatoNulo(fechaFin)) {
			throw new NullException("Fecha de finalizacion vacia");
		}
		this.fechaFin = fechaFin;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.idCampania = idCampania;
	}
	
	public Campania(String nombre, Date fechaInicio, Date fechaFin, ArrayList<Beneficio> catalogo) throws NullException {

		if (esDatoNulo(nombre))
			throw new NullException("Nombre vacio");
		if (esDatoNulo(fechaInicio))
			throw new NullException("Fecha de inicio vacia");
		if (esDatoNulo(fechaFin)) {
			throw new NullException("Fecha de finalizacion vacia");
		}
		this.fechaFin = fechaFin;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.catalogo = catalogo;
	}
	
	public Campania(String nombre, Date fechaInicio, Date fechaFin, ArrayList<Beneficio> catalogo, Integer idCampania) throws NullException {

		if (esDatoNulo(nombre))
			throw new NullException("Nombre vacio");
		if (esDatoNulo(fechaInicio))
			throw new NullException("Fecha de inicio vacia");
		if (esDatoNulo(fechaFin)) {
			throw new NullException("Fecha de finalizacion vacia");
		}
		this.fechaFin = fechaFin;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.catalogo = catalogo;
		this.idCampania = idCampania;
	}

	private boolean esDatoNulo(String dato) {
		return dato == null | dato.isEmpty();
	}

	private boolean esDatoNulo(Date dato) {
		if (dato == null)
			return true;
		else
			return false;
	}
	
	public Date obtenerFechaFin() {
		return this.fechaFin;
	}
	
	public Date obtenerFechaInicio() {
		return this.fechaInicio;
	}
	
	public String obtenerNombre() {
		return this.nombre;
	}
	
	public Integer obtenerId() {
		return this.idCampania;
	}
	
	public ArrayList<Beneficio> obtenerBeneficios(){
		return this.catalogo;
	}
	
	public boolean estaVigente() {
		Date hoy=Fecha.fechaHoy();

		if(Fecha.esMayorOIgual(hoy, this.fechaInicio) & Fecha.esMenorOIgual(hoy, fechaFin))
			return true;
		else
			return false;
			
		
		
	}
	
	
}
