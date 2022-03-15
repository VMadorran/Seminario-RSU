package ar.edu.unrn.seminario.herramienta;


import java.util.Calendar;
import java.util.Date;

public class Fecha {
	
	public Fecha() {
		
	}

	

	public static Date fechaHoy() {
		Date fechaHoy=new Date();
		return fechaHoy;
	}
	
	public static Date textoAFecha(int d, int m, int a) {
		Calendar h = Calendar.getInstance();
		h.set(a, m, d);
		Date hoy = h.getTime();
		return hoy;
	}
	
	// compara dos fechas y devuelve true
	// En caso de ser la primera mayor o igual
	// a la menor //reser //fechaEn
	public static boolean esMenorOIgual(Date mayor, Date menor) {
						// antes //igual
		if (mayor.before(menor) || (mayor.equals(menor)))
			return true;
		return false;
	}

	public static boolean esMayorOIgual(Date una, Date otra) {

		if (una.after(otra) || (otra.equals(una)))
			return true;
		return false;

	}
	public static boolean fechasInvertidas(Date inicio, Date fin) {
		if(inicio.after(fin)) {
			return true;
		}
		return false;
	}

	

}
