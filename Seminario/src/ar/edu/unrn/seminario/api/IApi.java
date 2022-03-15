package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ar.edu.unrn.seminario.exception.ApiException;
import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.ExisteException;
import ar.edu.unrn.seminario.exception.FechaException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.exception.PuntajeException;
import ar.edu.unrn.seminario.dto.BeneficioDTO;
import ar.edu.unrn.seminario.dto.CampaniaDTO;
import ar.edu.unrn.seminario.dto.OrdenDePedidoDTO;
import ar.edu.unrn.seminario.dto.PedidoDTO;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.dto.ResiduoDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.TipoResiduoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.dto.PropietarioDTO;
import ar.edu.unrn.seminario.exception.StateException;
import ar.edu.unrn.seminario.modelo.Beneficio;
import ar.edu.unrn.seminario.modelo.Residuo;

public interface IApi {

	//-------------------------------VIVIENDA-----------------------------//
	void registrarVivienda(String nombre, String apellido, String dni, String calle, int numeroCalle, String barrio)
			throws ExisteException, ApiException;

	public ViviendaDTO obtenerVivienda(int numeroVivienda)  throws ApiException; 

	void emilinarVivienda();

	public List<ViviendaDTO> obtenerViviendas() throws ApiException; // recupera todas las viviendas

	List<ViviendaDTO> obtenerFiltradoApellido(String apellido) throws ApiException, JdbcException, NullException;

	public ViviendaDTO obtenerVivienda();
	
	List<ViviendaDTO> obtenerViviendaPorFiltrado(String filtrar) throws ApiException;
	
	public  List<ViviendaDTO> obtenerViviendas(Comparator<ViviendaDTO> comparador) throws ApiException;
	

	void modificarDireccion(String calle, int numero, String barrio, int id) throws ApiException; 
    
  //-------------------------------PROPIETARIO-----------------------------//
    
	 
    List<PropietarioDTO> obtenerPropietarios()throws ApiException;
   
    PropietarioDTO obtenerPropietario(String numeroDNI) throws ApiException;
   
    void modificarPropietario(String nombre, String apellido, String dni) throws ApiException;



  //-------------------------------USUARIO-----------------------------//
    void registrarUsuario(String username, String password, String email, Integer codigoRol)
			throws ApiException, ExisteException;
    

    UsuarioDTO obtenerUsuario(String username) throws ApiException ;

	void eliminarUsuario(String username)throws ApiException;

	public List<UsuarioDTO> obtenerUsuarios() throws ApiException; // recuperar todos los usuarios

	void activarUsuario(String username) throws ApiException;  // recuperar el objeto Usuario, implementar el
																// comportamiento de estado.

	void desactivarUsuario(String username) throws StateException,ApiException, JdbcException; // recuperar el objeto Usuario, implementar el
																	// comportamiento de estado.

	boolean ingresarUsuario(String username, String password) throws ExisteException, ApiException; 
	
	String usuarioTieneRol(String username) throws ApiException; 
	
	
	
	//-------------------------------ROLES-----------------------------//
	List<RolDTO> obtenerRoles() throws ApiException; 

	List<RolDTO> obtenerRolesActivos()throws ApiException;

	void guardarRol(String nombre, boolean estado) throws ApiException; // crear el objeto de dominio â€œRolâ€�

	RolDTO obtenerRolPorCodigo(Integer codigo) throws ApiException ;  // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp
	

	
	//-------------------------------PEDIDO-----------------------------//
	
	List<PedidoDTO> obtenerPedidos() throws ApiException; 

	PedidoDTO obtenerPedido( int numeroPedido) throws ApiException ;

	void registrarPedido(ViviendaDTO viviendaDto, Date fecha, List<ResiduoDTO> residuosDto, boolean vehiculo,
			String observacion) throws ApiException; ///

	//-------------------------------RECOLECTORES-----------------------------//
	
	void registrarRecolector(String nombre, String apellido, String email, String dni, String legajo)
			throws ApiException, ExisteException;

	List<RecolectorDTO> obtenerRecolectores() throws ApiException;

	List<RecolectorDTO> obtenerFiltradoLegajo(String legajo) throws ApiException ; 
	
	RecolectorDTO obtenerRecolector(String legajo) throws ApiException; 

	//-------------------------------BENEFICIOS-----------------------------//

	void registrarBeneficio(String descripcion, Integer puntaje) throws ApiException, ExisteException; 

	BeneficioDTO obtenerBeneficio(int id) throws ApiException;

	 List<BeneficioDTO> obtenerBeneficios() throws ApiException;

	 List<BeneficioDTO> obtenerFiltradoPorId(Integer id) throws ApiException ;
	 
	 

	//-------------------------------CAMPANIA-----------------------------//
	 ArrayList<Beneficio> dtoAModelo(ArrayList<BeneficioDTO> beneficiosDtos) throws ApiException ; 
	
	 List<CampaniaDTO> obtenerCampanias() throws ApiException;

	List<BeneficioDTO> beneficiosDeCampania(int idCampania) throws ApiException ;
	
	void canjearPuntos(String idPropietario, int idBeneficio, Date fechaCanje) throws ApiException, PuntajeException;  
	
	List<CampaniaDTO> obtenerCampaniasVigentes() throws ApiException;
	
	 List<BeneficioDTO> beneficiosDeCampaniaPorPuntos(int idCampania, int puntosPropietario) throws ApiException, PuntajeException;

	 void registrarCampania(String nombre, Date fechaI, Date fechaF, ArrayList<Beneficio> catalogo)
	            throws ApiException, ExisteException, FechaException; 
	
	
	//-------------------------------ORDENES-----------------------------//


	void registrarOrden(int numPedido, Date fechaDelPedido, String estado, int numeroVivienda)
			throws ApiException, ExisteException; 

	void asignarRecolectorAOrden(RecolectorDTO recolector) throws ApiException ; 

	List<OrdenDePedidoDTO> obtenerListadoOrdenes() throws ApiException ;

	void cambiarEstadoOrden(int numeroOrden, String estado) throws  ApiException;


	OrdenDePedidoDTO obtenerOrdenDePedido(int numeroOrden) throws ApiException ;

	void registrarVisita(Date diaConcurrido, String observacion, List<ResiduoDTO> residuosDto,
			OrdenDePedidoDTO ordenDto) throws ApiException; ///
	
	void registrarVisitaYconcretar(Date diaConcurrido, String observacion, List<ResiduoDTO> residuosDto,
			OrdenDePedidoDTO ordenDto) throws ApiException; 
	
	boolean pesoRecolectorEsMenor(List<ResiduoDTO> residuosRecolector); 

	//-------------------------------PUNTAJE-----------------------------//
	
	

	void agregarPuntajePropietario(int numeroPedido, List<Residuo> residuos) throws ApiException; 

	int consultarPuntosCliente(String dni) throws ApiException, PuntajeException; 
	
	//-------------------------------RESIDUO-----------------------------//
	
	ArrayList<TipoResiduoDTO> obtenerResiduos() throws ApiException; 
	
	List<ResiduoDTO> obtenerResiduosNoLevantados(int numeroOrden) throws  ApiException ; 
	
	


   

}
