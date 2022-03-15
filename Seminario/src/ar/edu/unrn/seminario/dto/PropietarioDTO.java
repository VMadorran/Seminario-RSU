package ar.edu.unrn.seminario.dto;


import ar.edu.unrn.seminario.modelo.Usuario;

public class PropietarioDTO {
	private String nombre;
	private String apellido;
	private String dni;
	private Usuario usuario;
	private int puntosAcumulados;
	// private PedidoRetiro pedidoRetiro;

	public PropietarioDTO(String nombre, String apellido, String dni) {

		this.apellido = apellido;
		this.dni = dni;
		this.nombre = nombre;

	}



	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getDni() {
		return dni;
	}
	

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

}
