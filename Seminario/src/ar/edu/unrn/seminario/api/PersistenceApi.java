package ar.edu.unrn.seminario.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.ApiException;
import ar.edu.unrn.seminario.exception.EstadoException;
import ar.edu.unrn.seminario.exception.ExisteException;
import ar.edu.unrn.seminario.exception.FechaException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.exception.PuntajeException;
import ar.edu.unrn.seminario.accesos.BeneficioDAOJDBC;
import ar.edu.unrn.seminario.accesos.BeneficioDao;
import ar.edu.unrn.seminario.accesos.CampaniaDAOJDBC;
import ar.edu.unrn.seminario.accesos.CampaniaDao;
import ar.edu.unrn.seminario.accesos.DireccionDAOJDBC;
import ar.edu.unrn.seminario.accesos.LoginUsuarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.LoginUsuarioDao;
import ar.edu.unrn.seminario.accesos.OrdenDAOJDBC;
import ar.edu.unrn.seminario.accesos.OrdenDao;
import ar.edu.unrn.seminario.accesos.PedidoDAOJDBC;
import ar.edu.unrn.seminario.accesos.PedidoDao;
import ar.edu.unrn.seminario.accesos.PropietarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.RecolectorDAOJDBC;
import ar.edu.unrn.seminario.accesos.RecolectorDao;
import ar.edu.unrn.seminario.accesos.ResiduosDAOJDBC;
import ar.edu.unrn.seminario.accesos.ResiduosDao;
import ar.edu.unrn.seminario.accesos.RolDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDao;
import ar.edu.unrn.seminario.accesos.UsuarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.UsuarioDao;
import ar.edu.unrn.seminario.accesos.VisitaDAOJDBC;
import ar.edu.unrn.seminario.accesos.VisitaDao;
import ar.edu.unrn.seminario.accesos.ViviendaDAOJDBC;
import ar.edu.unrn.seminario.dto.BeneficioDTO;
import ar.edu.unrn.seminario.dto.CampaniaDTO;
import ar.edu.unrn.seminario.dto.DireccionDTO;
import ar.edu.unrn.seminario.dto.OrdenDePedidoDTO;
import ar.edu.unrn.seminario.dto.PedidoDTO;
import ar.edu.unrn.seminario.dto.PropietarioDTO;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.dto.ResiduoDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.TipoResiduoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.herramienta.Fecha;
import ar.edu.unrn.seminario.herramienta.Filtrar;
import ar.edu.unrn.seminario.modelo.Beneficio;
import ar.edu.unrn.seminario.modelo.Campania;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.OrdenDePedido;
import ar.edu.unrn.seminario.modelo.Pedido;
import ar.edu.unrn.seminario.modelo.Propietario;
import ar.edu.unrn.seminario.modelo.Recolector;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class PersistenceApi implements IApi {// 2.0

	private RolDao rolDao;
	private UsuarioDao usuarioDao;
	private PropietarioDAOJDBC propietarioDao;
	private DireccionDAOJDBC direccionDao;
	private ViviendaDAOJDBC viviendaDao;
	private LoginUsuarioDao loginDao;
	private PedidoDao pedidoDao;
	private ResiduosDao residuoDao;
	private RecolectorDao recolectorDao;
	private BeneficioDao beneficioDao;
	private CampaniaDao campaniaDao;
	private OrdenDao ordenDao;
	private VisitaDao visitaDao;

	public PersistenceApi() {
		propietarioDao = new PropietarioDAOJDBC();
		direccionDao = new DireccionDAOJDBC();
		viviendaDao = new ViviendaDAOJDBC();
		rolDao = new RolDAOJDBC();
		usuarioDao = new UsuarioDAOJDBC();
		loginDao = new LoginUsuarioDAOJDBC();
		pedidoDao = new PedidoDAOJDBC();
		residuoDao = new ResiduosDAOJDBC();
		recolectorDao = new RecolectorDAOJDBC();
		beneficioDao = new BeneficioDAOJDBC();
		campaniaDao = new CampaniaDAOJDBC();
		ordenDao = new OrdenDAOJDBC();
		visitaDao = new VisitaDAOJDBC();

		campaniaDao = new CampaniaDAOJDBC();

	}

	// -------------------------------VIVIENDA-----------------------------//


	@Override
	public void registrarVivienda(String nombre, String apellido, String dni, String calle, int numeroCalle,
			String barrio) throws ApiException {
 
		try {
			Propietario propietario = new Propietario(nombre, apellido, dni);
			Direccion direccion = new Direccion(calle, numeroCalle, barrio);

			Vivienda vivienda = new Vivienda(propietario, direccion);
			this.viviendaDao.create(vivienda, this.propietarioDao, this.direccionDao);
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
		

	}
	public ViviendaDTO obtenerVivienda(int numeroVivienda) throws ApiException {

		ViviendaDTO viviendaDto = null;
		try {
		Vivienda vivienda = this.viviendaDao.find(numeroVivienda);

		PropietarioDTO propietarioDto;
		
			propietarioDto = new PropietarioDTO(vivienda.getPropietario().getNombre(),
					vivienda.getPropietario().getApellido(), vivienda.getPropietario().getDni());

			DireccionDTO direccionDto = new DireccionDTO(vivienda.getDireccion().getCalle(),
					vivienda.getDireccion().getNumero(), vivienda.getDireccion().getBarrio(),
					vivienda.getDireccion().getIdDireccion());

			viviendaDto = new ViviendaDTO(vivienda.getNumeroVivienda(), propietarioDto, direccionDto);
		} catch (NullException e) {
			throw new ApiException(e.getMessage());
			
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}

		return viviendaDto;
	}

	@Override
	public List<ViviendaDTO> obtenerViviendas() throws ApiException {
		List<ViviendaDTO> viviendasDto = new ArrayList<ViviendaDTO>();

		List<Vivienda> viviendas;
	try {	
			viviendas = viviendaDao.findAll();
			for (Vivienda v : viviendas) {
				PropietarioDTO propietarioDto = new PropietarioDTO(v.getPropietario().getNombre(),
						v.getPropietario().getApellido(), v.getPropietario().getDni());
				DireccionDTO direccionDto = new DireccionDTO(v.getDireccion().getCalle(), v.getDireccion().getNumero(),
						v.getDireccion().getBarrio());
				viviendasDto.add(new ViviendaDTO(v.getNumeroVivienda(), propietarioDto, direccionDto));
			}
		

		return viviendasDto;
		
	}catch(NullException e) {
		throw new ApiException(e.getMessage());
	}catch(JdbcException e) {
		throw new ApiException(e.getMessage());
	}
	}
	
	
	
	
	public  List<ViviendaDTO> obtenerViviendas(Comparator<ViviendaDTO> comparador) throws ApiException{
		
		return Filtrar.filtrar(this.obtenerViviendas(), comparador);
		
	}
	

	// Se cambia por parametrizacion
	@Override
	public List<ViviendaDTO> obtenerFiltradoApellido(String apellido) throws ApiException, JdbcException, NullException {

		List<Vivienda> viviendas;
		List<ViviendaDTO> viviendaDto = null;
	
			viviendas = viviendaDao.findAll();
			viviendaDto = viviendas.stream()
					.filter(v -> apellido.isEmpty() || v.getPropietario().getApellido().equals(apellido)).map((v) -> {
						ViviendaDTO viviendaDtoc = null;
						
							viviendaDtoc = new ViviendaDTO(
									new PropietarioDTO(v.getPropietario().getNombre(), v.getPropietario().getApellido(),
											v.getPropietario().getDni()),
									new DireccionDTO(v.getDireccion().getCalle(), v.getDireccion().getNumero(),
											v.getDireccion().getBarrio()));
						
						return viviendaDtoc;

					}

					).collect(Collectors.toList());
		

		return viviendaDto;
	}

	public List<ViviendaDTO> obtenerViviendaPorFiltrado(String filtrar) throws ApiException{
		
		return Filtrar.filtrar(this.obtenerViviendas(),
				(ViviendaDTO -> filtrar.isEmpty() || ViviendaDTO.getBarrio().toLowerCase().contains(filtrar)
						|| ViviendaDTO.getApellidopropietarioDto().toLowerCase().contains(filtrar)
						|| ViviendaDTO.getCalle().toLowerCase().contains(filtrar) || ViviendaDTO.getDnipropietarioDto().toLowerCase().contains(filtrar)
						|| ViviendaDTO.getNombrePropietario().toLowerCase().contains(filtrar)
						|| Integer.toString(ViviendaDTO.getNumero()).toLowerCase().contains(filtrar)
						|| Integer.toString(ViviendaDTO.getNumeroVivienda()).toLowerCase().contains(filtrar)));
		
		}

	public void modificarDireccion(String calle, int numero, String barrio, int id) throws  ApiException {

		try {

			Direccion d = direccionDao.find(id);

			d.setCalle(calle);
			d.setNumero(numero);
			d.setBarrio(barrio);

			direccionDao.update(d);
		} catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}

	}

	// -------------------------------PROPIETARIO-----------------------------//

	public List<PropietarioDTO> obtenerPropietarios() throws ApiException  {
		
		// System.out.print("antes");

		List<Propietario> propietarios;
		List<PropietarioDTO> dtos = null;
	try {
			propietarios = propietarioDao.findAll();
			dtos = new ArrayList<>();
			for (Propietario p : propietarios) {
				dtos.add(new PropietarioDTO(p.getNombre(), p.getApellido(), p.getDni()));
				// System.out.print("nombre: " + p.getNombre() + "apellido: " + p.getApellido()
				// + "dni: " + p.getDni());
			}

	
	}catch(NullException e) {
		throw new ApiException(e.getMessage());
	}catch(JdbcException e) {
		throw new ApiException(e.getMessage());
	}
		return dtos;

	}

	private boolean existePropietario(String dni) throws ApiException, JdbcException, NullException {

		List<Propietario> propietarios;
		boolean existe = false;

		
			propietarios = propietarioDao.findAll();
			for (Propietario p : propietarios) {
				if (p.getDni().equals(dni))
					existe = true;

			}
		

		return existe;

	}

	public PropietarioDTO obtenerPropietario(String numeroDNI) throws ApiException {

		Propietario propietario;
		PropietarioDTO propietarioDto = null;
		try {
			propietario = this.propietarioDao.find(numeroDNI);
			
				propietarioDto = new PropietarioDTO(propietario.getNombre(), propietario.getApellido(),
						propietario.getDni());
		return propietarioDto;
		
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	}

	public void modificarPropietario(String nombre, String apellido, String dni) throws ApiException {

		try {

			Propietario p = propietarioDao.find(dni);
			p.setNombre(nombre);
			p.setApellido(apellido);
			p.setDni(dni);

			propietarioDao.update(p);

		} catch (JdbcException e) {
			throw new ApiException(e.getMessage());

		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}

	}
	// -------------------------------USUARIO-----------------------------//

	@Override
	public boolean ingresarUsuario(String username, String password) throws ApiException, ExisteException{
		
		try {
		boolean existe = loginDao.iniciarSesion(username, password);
		if(existe==false) {
			throw new ExisteException("la cuenta no existe");
		}
		
		return existe;
			}catch(NullException e) {
				throw new ApiException(e.getMessage());
			}catch(JdbcException e) {
				throw new ApiException(e.getMessage());
			}
		

	}

	@Override
	public void registrarUsuario(String username, String password, String email, Integer codigoRol)
			throws ApiException, ExisteException {

		try {
		if (!existeUsuario(username)) {
			Rol rol = rolDao.find(codigoRol);
			
			Usuario usuario = new Usuario(username, password, email, rol);
			this.usuarioDao.create(usuario);
		} else
			throw new ExisteException("El nombre de usuario ya se encuentra en el sistema");
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	

	}

	private boolean existeUsuario(String username) throws ApiException {
		boolean existe = false;

		List<Usuario> usuarios;
		try {
		usuarios = usuarioDao.findAll();
		for (Usuario u : usuarios) {
			if (u.getNombreUsuario().equals(username))
				existe = true;
		}
		return existe;

		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	

	}

	@Override
	public List<UsuarioDTO> obtenerUsuarios() throws ApiException {
		List<UsuarioDTO> dtos = new ArrayList<>();
		try {
		
		List<Usuario> usuarios = usuarioDao.findAll();
		for (Usuario u : usuarios) {
			dtos.add(
					new UsuarioDTO(u.getNombreUsuario(), u.getContrasenia(), u.getEmail(),new RolDTO(u.getRol().getNombre(),u.getRol().getCodigo(),u.getRol().estaActivo()) , u.estaActivo()));
		}
		return dtos;
		
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	


	}
	public String usuarioTieneRol(String username) throws ApiException {
		String nombreRol="";
		
		try {
		Usuario usuario=usuarioDao.find(username);
		List<Rol>roles=rolDao.findAll();
		for(Rol r:roles) {
			if(usuario.getRol().getNombre().equals(r.getNombre())) {
				nombreRol=r.getNombre();
			}
		}
		
		return nombreRol; 
		
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	

				
	}

	@Override
	public UsuarioDTO obtenerUsuario(String username) throws ApiException {
	
		try {
		
		Usuario usuario=usuarioDao.find(username);
	UsuarioDTO usuarioDto=new UsuarioDTO(usuario.getNombreUsuario(),usuario.getContrasenia(),usuario.getEmail(),new RolDTO(usuario.getRol().getNombre(),usuario.getRol().getCodigo()),usuario.estaActivo());
	return usuarioDto;
	
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	

	}

	@Override
	public void eliminarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	// -------------------------------PEDIDOS-----------------------------//

	@Override
	public void registrarPedido(ViviendaDTO viviendaDto, Date fecha, List<ResiduoDTO> residuosDto, boolean vehiculo,
			String observacion) throws ApiException //List<ResiduoARetirarDTO>
	{
		List<Residuo> residuos = new ArrayList<>();
		
		try {
		Propietario propietario = new Propietario(viviendaDto.getNombrePropietario(),
				viviendaDto.getApellidopropietarioDto(), viviendaDto.getDnipropietarioDto());
		Direccion direccion = new Direccion(viviendaDto.getCalle(), viviendaDto.getNumero(), viviendaDto.getBarrio(),
				viviendaDto.getDireccion().getIdDireccion());
		Vivienda vivienda = new Vivienda(viviendaDto.getNumeroVivienda(), propietario, direccion);
		for (ResiduoDTO r : residuosDto) {//ResiduoARetirarDTO
			TipoResiduo tipoResiduo = new TipoResiduo(r.getTipoResiduoDto().getId(), r.getTipoResiduoDto().getNombre(),
					r.getTipoResiduoDto().getPuntosResiduo());
			Residuo residuo = new Residuo(tipoResiduo, r.getPeso());//ResiduoARetirar residuo = new ResiduoARetirar (tipo, r.getPeso();
			residuos.add(residuo);
		}

		Pedido pedido = new Pedido(vivienda, fecha, residuos, vehiculo, observacion);

		this.pedidoDao.create(vivienda, pedido);
		
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	


	}

	public List<PedidoDTO> obtenerPedidos() throws ApiException{
		List<PedidoDTO> dtos = new ArrayList<>();
		
		try {
		List<Pedido> pedidos = pedidoDao.findAll();
		List<ResiduoDTO> residuosDto = new ArrayList<>(); //List <ResiduoARetirarDTO> residuoDto= new ArrayList<>();
		for (Pedido p : pedidos) {
			// String nombre, String apellido, String dni
			PropietarioDTO propietario = new PropietarioDTO(p.getVivienda().getPropietario().getNombre(),
					p.getVivienda().getPropietario().getApellido(), p.getVivienda().getPropietario().getDni());

			// String calle, int numero, String barrio,int idDireccion
			DireccionDTO direccion = new DireccionDTO(p.getVivienda().getDireccion().getCalle(),
					p.getVivienda().getDireccion().getNumero(), p.getVivienda().getDireccion().getBarrio(),
					p.getVivienda().getDireccion().getIdDireccion());

			// int numeroVivienda,PropietarioDTO propietarioDto, DireccionDTO direccionDto
			ViviendaDTO vivienda = new ViviendaDTO(p.getVivienda().getNumeroVivienda(), propietario, direccion);

			// ViviendaDTO vivienda, LocalDate fecha, int qresiduo, boolean vehiculo, String
			// observacion, int nro_pedido

			residuosDto = p.getResiduo().stream().map(r -> {
			
					return new ResiduoDTO(new TipoResiduoDTO(r.getTipoResiduo().getId(), r.getTipoResiduo().getNombre(),//ResiduoARetirarDTO
							r.getTipoResiduo().getPuntosResiduos()), r.getPeso());

			
				
			}).collect(Collectors.toList());

			dtos.add(new PedidoDTO(vivienda, p.getFecha(), residuosDto, p.getVehiculo(), p.getObservacion(),
					p.getidPedidoRetiro()));
		}

		return dtos;
		
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	


	}

	@Override
	
	public PedidoDTO obtenerPedido( int numeroPedido) throws ApiException{
	
		try {
		Pedido pedido = pedidoDao.find(numeroPedido);
		
		
		List<ResiduoDTO> residuosDto = new ArrayList<>();
		for (Residuo r : pedido.getResiduo()) {//ResiduoARetirar
			residuosDto.add(new ResiduoDTO(new TipoResiduoDTO(r.getTipoResiduo().getId(),
					r.getTipoResiduo().getNombre(), r.getTipoResiduo().getPuntosResiduos()), r.getPeso()));
		}
		PedidoDTO pedidoDto = new PedidoDTO(pedido.getFecha(), residuosDto, pedido.getVehiculo(),
				pedido.getObservacion(), pedido.getidPedidoRetiro());

		return pedidoDto;
		
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	

	}

	// -------------------------------RESIDUOS-----------------------------//

	public ArrayList<TipoResiduoDTO> obtenerResiduos() throws ApiException {
		ArrayList<TipoResiduoDTO> residuosDto = new ArrayList<>();
	try {
		
		List<TipoResiduo> residuos = residuoDao.findAll();
		for (TipoResiduo r : residuos) {
	
			residuosDto.add(new TipoResiduoDTO(r.getId(), r.getNombre(), r.getPuntosResiduos()));
		}
		return residuosDto;
		
	}catch(NullException e) {
		throw new ApiException(e.getMessage());
	}catch(JdbcException e) {
		throw new ApiException(e.getMessage());
	}


	}

	public List<ResiduoDTO> obtenerResiduosNoLevantados(int numeroOrden) throws ApiException {///--------> VER
		List<ResiduoDTO> residuosDto = new ArrayList<>();
		
		try {
		
		List<Residuo> residuos = residuoDao.findAllNoLevantados(numeroOrden);

		residuosDto = residuos.stream().map(r -> {
			
			
				return new ResiduoDTO(r.getId(), new TipoResiduoDTO(r.getTipoResiduo().getId(),
						r.getTipoResiduo().getNombre(), r.getTipoResiduo().getPuntosResiduos()), r.getPeso());
			
			

		}).collect(Collectors.toList());
		return residuosDto;
		
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	


	}

	// -------------------------------ROLES-----------------------------//

	@Override
	public List<RolDTO> obtenerRoles() throws ApiException {
		
		try {
		
		List<Rol> roles = rolDao.findAll();
		List<RolDTO> rolesDTO = new ArrayList<>(0);
		for (Rol rol : roles) {
			rolesDTO.add(new RolDTO(rol.getNombre(), rol.getCodigo(), rol.estaActivo()));
		}
		return rolesDTO;
		
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	


	}

	@Override
	public List<RolDTO> obtenerRolesActivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RolDTO obtenerRolPorCodigo(Integer codigo) throws ApiException{
	
		try {
		
		Rol rol = rolDao.find(codigo);
		RolDTO rolDTO = new RolDTO(rol.getNombre(), rol.getCodigo(), rol.estaActivo());
		return rolDTO;
		
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	


	}

	@Override
	public void activarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activarUsuario(String username) throws ApiException {
	
		try {
		
		Usuario usuario=usuarioDao.find(username);
		usuario.activar();
		usuarioDao.update(usuario);
		
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	

	}

	@Override
	public void desactivarUsuario(String username) throws JdbcException {
		Usuario usuario=usuarioDao.find(username);
		usuario.desactivar();
		usuarioDao.update(usuario);

	}

	@Override
	public ViviendaDTO obtenerVivienda() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void emilinarVivienda() {
		// TODO Auto-generated method stub

	}
	

	@Override
	public void guardarRol(String nombre, boolean estado) throws ApiException {
		try {
		
		Rol rol = new Rol(nombre, estado);
		this.rolDao.create(rol);
		
	
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	


	}

	// -------------------------------BENEFICIO-----------------------------//

	@Override
	public void registrarBeneficio(String descripcion, Integer puntaje) throws ApiException, ExisteException {
		// TODO Auto-generated method stub

		try {
		Beneficio beneficio = null;
	
			if (!existeBeneficio(descripcion, puntaje)) {
				beneficio = new Beneficio(descripcion, puntaje);
			} else
				throw new ExisteException("");

		 
		this.beneficioDao.create(beneficio);

		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	

	}

	private boolean existeBeneficio(String nombre, int puntaje) throws ApiException {

		boolean existe = false;

      try {
			List<Beneficio> beneficios = beneficioDao.findAll();
			for (Beneficio b : beneficios) {
				if ((b.obtenerDescripcion().equals(nombre)) && (b.obtenerPuntaje().equals(puntaje)))
					existe = true;
			}
		
      }catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	

		return existe;

	}

	@Override
	public BeneficioDTO obtenerBeneficio(int id) throws ApiException{

		BeneficioDTO beneficioDto = null;

		Beneficio beneficio;
		 try {
			beneficio = this.beneficioDao.find(id);
			
				beneficioDto = new BeneficioDTO(beneficio.obtenerDescripcion(), beneficio.obtenerPuntaje(),
						beneficio.obtenerIdBeneficio());
		

		return beneficioDto;
		
		 }catch(NullException e) {
				throw new ApiException(e.getMessage());
			}catch(JdbcException e) {
				throw new ApiException(e.getMessage());
			}
		

	}

	@Override
	public List<BeneficioDTO> obtenerBeneficios() throws ApiException {
		// TODO Auto-generated method stub

		List<BeneficioDTO> dtos = new ArrayList<>();
		
			List<Beneficio> beneficios;
			
			try {
			
				beneficios = beneficioDao.findAll();
				for (Beneficio b : beneficios) {
					dtos.add(new BeneficioDTO(b.obtenerDescripcion(), b.obtenerPuntaje(), b.obtenerIdBeneficio()));

				}
			 
		return dtos;
			}catch(NullException e) {
				throw new ApiException(e.getMessage());
			}catch(JdbcException e) {
				throw new ApiException(e.getMessage());
			}
		

	}

	public List<BeneficioDTO> obtenerFiltradoPorId(Integer id) throws ApiException{

		List<Beneficio> beneficios;
		List<BeneficioDTO> beneficioDto = null;
		
		try {
			beneficios = beneficioDao.findAll();
			beneficioDto = beneficios.stream().filter(b -> id == null || b.obtenerIdBeneficio().equals(id)).map((b) -> {
				
					return new BeneficioDTO(b.obtenerDescripcion(), b.obtenerPuntaje(), b.obtenerIdBeneficio());
				
				
			}).collect(Collectors.toList());


		return beneficioDto;
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	

	}

	// -------------------------------CAMPANIA-----------------------------//

	public ArrayList<Beneficio> dtoAModelo(ArrayList<BeneficioDTO> beneficiosDtos) {
		ArrayList<Beneficio> beneficios = new ArrayList<Beneficio>();

		try {
			for (BeneficioDTO b : beneficiosDtos) {

				Beneficio beneficio;
				beneficio = new Beneficio(b.obtenerDescripcion(), b.obtenerPuntaje(), b.obtenerIdBeneficio());
				beneficios.add(beneficio);
			}
		} catch (NullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return beneficios;
	}

	public void registrarCampania(String nombre, Date fechaI, Date fechaF, ArrayList<Beneficio> catalogo)
            throws ApiException, ExisteException, FechaException {

		try {
        if (!existeCampania(nombre, fechaI, fechaF)) {
            if (!Fecha.fechasInvertidas(fechaI, fechaF)) {
                Campania camapania;
                    camapania = new Campania(nombre, fechaI, fechaF);
                    this.campaniaDao.create(camapania, catalogo);
                

            } else
                throw new FechaException("");

        } else
            throw new ExisteException("Existe");
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}
    }

    private boolean existeCampania(String nombre, Date fechaI, Date fechaF) throws ApiException {

        boolean existe = false;

        try {
        List<Campania> campanias;


            campanias = campaniaDao.findAll();
            for (Campania c : campanias) {
                if ((c.obtenerNombre().equals(nombre)) & (c.obtenerFechaInicio().equals(fechaI))
                        & (c.obtenerFechaFin().equals(fechaF)))
                    existe = true;
            }

        return existe;
        }catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
    }

	// -------------------------------ORDEN-----------------------------//

	public void registrarOrden(int numPedido, Date fechaDelPedido, String estado, int numeroVivienda)
			throws ApiException, ExisteException{

		
		
		try {
		Vivienda vivienda = viviendaDao.find(numeroVivienda);
		Pedido pedido = pedidoDao.find(numPedido);

		pedido.setVivienda(vivienda);
		OrdenDePedido orden = null;
		
			orden = new OrdenDePedido(fechaDelPedido, pedido, estado);
			if (!ordenDao.seEncuentraOrden(numPedido)) {
				ordenDao.create(orden);
			}

			else {
				throw new ExisteException("Ya existe una orden");
			}
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	


	}

	/*
	 * public void asignarRecolectorAOrden(String recolector) throws
	 * NumberFormatException, AppException { int i = 0; boolean entro = false;
	 * String legajo = ""; while (i < recolector.length() && recolector.charAt(i) !=
	 * '|') { System.out.println(i); if (entro == true) { char copia =
	 * recolector.charAt(i); System.out.println(copia); legajo += copia; } if
	 * (recolector.charAt(i) == ':') {
	 * 
	 * entro = true; } i++;
	 * 
	 * } ordenDao.asignarRecolector(recolectorDao.find(legajo)); }
	 */

	public void asignarRecolectorAOrden(RecolectorDTO recolector) throws ApiException{

		
		try {
		String legajo = recolector.obtenerLegajo();

		
			ordenDao.asignarRecolector(recolectorDao.find(legajo));
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	

		} 
	

	public List<OrdenDePedidoDTO> obtenerListadoOrdenes() throws ApiException {
		List<OrdenDePedido> ordenesDePedido = new ArrayList<>();
		List<OrdenDePedidoDTO> ordenesDePedidoDto = new ArrayList<>();

		try {
		ordenesDePedido = ordenDao.findAll();

		ordenesDePedidoDto = ordenesDePedido.stream().map(r -> {
			
				
				return new OrdenDePedidoDTO(r.getFechaPedido(), r.getEstado(), r.getId(),
						new RecolectorDTO(r.getRecolectorBasura().obtenerNombre(),
								r.getRecolectorBasura().obtenerApellido(), r.getRecolectorBasura().obtenerEmail(),
								r.getRecolectorBasura().obtenerDni(), r.getRecolectorBasura().obtenerLegajo()),
						new PedidoDTO(r.getPedido().getidPedidoRetiro()));

		
		}).collect(Collectors.toList());

		return ordenesDePedidoDto;
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	


	}

	public void cambiarEstadoOrden(int numeroOrden, String estado) throws ApiException {
		try {
		
		ordenDao.cambiarEstadoOrden(numeroOrden, estado);
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	

	}

	public OrdenDePedidoDTO obtenerOrdenDePedido(int numeroOrden) throws ApiException {

		OrdenDePedido orden;
		OrdenDePedidoDTO ordenDePedidoDto = null;
		
		try {
			orden = ordenDao.find(numeroOrden);
			
			
			PedidoDTO pedidoDto = this.obtenerPedido(orden.getPedido().getidPedidoRetiro());
				ordenDePedidoDto = new OrdenDePedidoDTO(orden.getFechaPedido(), orden.getEstado(), orden.getId(),
						new RecolectorDTO(orden.getRecolectorBasura().obtenerNombre(),
								orden.getRecolectorBasura().obtenerApellido(),
								orden.getRecolectorBasura().obtenerEmail(), orden.getRecolectorBasura().obtenerDni(),
								orden.getRecolectorBasura().obtenerLegajo()),
						pedidoDto);
			

		 
		/*
		 * Pedido pedido = pedidoDao.find(orden.getPedido().getidPedidoRetiro());
		 * 
		 * orden.setPedido(pedido);
		 */

		return ordenDePedidoDto;
		
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	

	}

	// -------------------------------VISITA-----------------------------//

	public void registrarVisita(Date diaConcurrido, String observacion, List<ResiduoDTO> residuosDto,//List<ResiduoRetiradoDTO>
			OrdenDePedidoDTO ordenDto) throws ApiException {
		List<Residuo> residuos= new ArrayList<Residuo>();//List<ResiduoRetirado> residuos= new ArrayList<ResiduoRetirado>()
		
		try {
		
		for(ResiduoDTO r:residuosDto) {//ResiduoRetiradoDTO
			Residuo residuo=new Residuo(r.getId(), new TipoResiduo(r.getTipoResiduoDto().getId(),//ResiduoRetirado residuo = new ResiduoRetirado
					r.getTipoResiduoDto().getNombre(), r.getTipoResiduoDto().getPuntosResiduo()), r.getPeso(),
					r.getPesoRetirado());
			residuos.add(residuo);
		}
	
		Pedido pedido = pedidoDao.find(ordenDto.getPedido().getNumeroPedido());
		Visita visita = new Visita(diaConcurrido, observacion, residuos);
		Recolector recolector = new Recolector(ordenDto.getRecolectorBasura().obtenerNombre(),
				ordenDto.getRecolectorBasura().obtenerApellido(), ordenDto.getRecolectorBasura().obtenerEmail(),
				ordenDto.getRecolectorBasura().obtenerDni(), ordenDto.getRecolectorBasura().obtenerLegajo());
		OrdenDePedido orden = new OrdenDePedido(ordenDto.getFechaPedido(), ordenDto.getEstado(), ordenDto.getId(),
				recolector, pedido);
		orden.cambiarEstadoEjecucion();
		visitaDao.create(visita, orden);
		

		if (ordenDao.estaInCompleto(orden.getId()) == false) {
			orden.cambiarEstadoConcretado();
			agregarPuntajePropietario(orden.getPedido().getidPedidoRetiro(), residuos);
			

		}
		this.cambiarEstadoOrden(orden.getId(), orden.getEstado());
		
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}catch(EstadoException e) {
			throw new ApiException(e.getMessage());
		}
	


	}
	
public boolean pesoRecolectorEsMenor(List<ResiduoDTO> residuosRecolector) {
		
		float peso=0; 
	
		for (ResiduoDTO r: residuosRecolector) {
			
			peso= peso + r.getPeso(); 
		}
		
		if(peso > 0) {
			return true; 
		}
		
		return false; 
		
	}
	
	
	
	public void registrarVisitaYconcretar(Date diaConcurrido, String observacion, List<ResiduoDTO> residuosDto,//List<ResiduoRetiradoDTO>
			OrdenDePedidoDTO ordenDto) throws ApiException {    
		
		List<Residuo> residuos= new ArrayList<Residuo>();//List <ResiduoRetirado> residuos= new ArrayList<ResiduoRetirado>();
	
		
		
		try {
		for(ResiduoDTO r:residuosDto) {//ResiduoRetiradoDTO
			Residuo residuo=new Residuo(r.getId(), new TipoResiduo(r.getTipoResiduoDto().getId(),
					r.getTipoResiduoDto().getNombre(), r.getTipoResiduoDto().getPuntosResiduo()), r.getPeso(),
					r.getPesoRetirado());
			residuos.add(residuo);
		}
	
	
		Pedido pedido = pedidoDao.find(ordenDto.getPedido().getNumeroPedido());
		Visita visita = new Visita(diaConcurrido, observacion, residuos);
		Recolector recolector = new Recolector(ordenDto.getRecolectorBasura().obtenerNombre(),
				ordenDto.getRecolectorBasura().obtenerApellido(), ordenDto.getRecolectorBasura().obtenerEmail(),
				ordenDto.getRecolectorBasura().obtenerDni(), ordenDto.getRecolectorBasura().obtenerLegajo());
		OrdenDePedido orden = new OrdenDePedido(ordenDto.getFechaPedido(), ordenDto.getEstado(), ordenDto.getId(),
				recolector, pedido);
		orden.cambiarEstadoConcretado();
		visitaDao.create(visita, orden);
		agregarPuntajePropietario(orden.getPedido().getidPedidoRetiro(), residuos);
		
		this.cambiarEstadoOrden(orden.getId(), orden.getEstado());

		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e){
			throw new ApiException(e.getMessage());
		}catch(EstadoException e) {
			throw new ApiException(e.getMessage());
		}
		
		
//		if (ordenDao.estaInCompleto(orden.getId()) == false) {
//			orden.cambiarEstadoConcretado();
//			agregarPuntajePropietario(orden.getPedido().getidPedidoRetiro(), residuos);
//			
//
//		}
		
	
		
	}


	// -------------------------------PUNTAJE-----------------------------//

	public void agregarPuntajePropietario(int numeroPedido, List<Residuo> residuos) throws ApiException {
		
		try {
		int puntaje = obtenerPuntajeResiduos(residuos);

		
		propietarioDao.updatePuntaje(puntaje, numeroPedido);
		
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	}

	private int obtenerPuntajeResiduos(List<Residuo> residuos) {

		int puntaje = 0;
		for (Residuo r : residuos) {
			puntaje += r.getTipoResiduo().getPuntosResiduos() * r.getPesoRetirado();
		}
		return puntaje;
	}

	@Override
	public int consultarPuntosCliente(String dni) throws ApiException, PuntajeException {

		int puntosCliente = 0;
		
   try {
			puntosCliente = propietarioDao.puntosAcumuladosPropietario(dni);
			if (puntosCliente == 0)
				throw new PuntajeException("puntaje en 0");
		
		System.out.println("Punto del propietario: "+ puntosCliente);
		return puntosCliente;
		

	}catch(JdbcException e) {
		throw new ApiException(e.getMessage());
	}
	}

	// -------------------------------RECOLECTOR-----------------------------//

	@Override
	public void registrarRecolector(String nombre, String apellido, String email, String dni, String legajo)
			throws ApiException, ExisteException {
		try {
		
			if (!this.existeRecolector(legajo)) {

				Recolector recolector = new Recolector(nombre, apellido, email, dni, legajo);
				this.recolectorDao.create(recolector);
			} else {
				throw new ExisteException("El recolector ya se encuentra en el sistema");
			}
					
		  }catch(NullException e) {
				throw new ApiException(e.getMessage());
			}catch(JdbcException e) {
				throw new ApiException(e.getMessage());
			}
		}

	

	private boolean existeRecolector(String legajo) throws ApiException {
		List<Recolector> recolectores;
		boolean existe = false;
          
		try {
		
			recolectores = recolectorDao.findAll();
			for (Recolector r : recolectores) {
				if (r.obtenerLegajo().equals(legajo))
					existe = true;
			}
		
		return existe;
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}

	}

	public RecolectorDTO obtenerRecolector(String legajo) throws ApiException {

		System.out.print("numero legajo:" + legajo);
		Recolector recolector;
		RecolectorDTO recolectorDTO = null;
		
		try {
			recolector = this.recolectorDao.find(legajo);
			System.out.print("recolector dao:" + recolector.obtenerNombre());

			
				recolectorDTO = new RecolectorDTO(recolector.obtenerNombre(), recolector.obtenerApellido(),
						recolector.obtenerEmail(), recolector.obtenerDni(), recolector.obtenerLegajo());
		
			System.out.print("recolector:" + recolectorDTO.obtenerNombre());

		return recolectorDTO;
		
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	}

	@Override
	public List<RecolectorDTO> obtenerFiltradoLegajo(String legajo) throws ApiException {

		List<Recolector> recolectores;
		List<RecolectorDTO> recolectorDto = null;
		
		try {
			recolectores = recolectorDao.findAll();
			recolectorDto = recolectores.stream().filter(r -> legajo.isEmpty() || r.obtenerLegajo().equals(legajo))
					.map((r) -> {
						
							return new RecolectorDTO(r.obtenerNombre(), r.obtenerApellido(), r.obtenerEmail(),
									r.obtenerDni(), r.obtenerLegajo());
						
						
					}).collect(Collectors.toList());

	

		return recolectorDto;
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	}

	@Override
	public List<BeneficioDTO> beneficiosDeCampania(int idCampania) throws ApiException{
		List<BeneficioDTO> beneficiosDtos = new ArrayList<>();
		List<Beneficio> beneficios;
		
		try {
			beneficios = campaniaDao.beneficiosDeCampania(idCampania);
			for (Beneficio b : beneficios) {
				BeneficioDTO beneficio = new BeneficioDTO(b.obtenerDescripcion(), b.obtenerPuntaje(),
						b.obtenerIdBeneficio());
				beneficiosDtos.add(beneficio);
			}
	

		return beneficiosDtos;
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}

	}

	@Override
	public void canjearPuntos(String idPropietario, int idBeneficio, Date fechaCanje) throws ApiException, PuntajeException  {
		
		
		try {
			BeneficioDTO beneficio= this.obtenerBeneficio(idBeneficio);
			
			if(this.consultarPuntosCliente(idPropietario) >= beneficio.obtenerPuntaje()) {
				propietarioDao.canjearBeneficio(idPropietario, idBeneficio, fechaCanje);}
			else throw new PuntajeException("");
			}catch(JdbcException e) {
				throw new ApiException(e.getMessage());
			}
		 

	}

	@Override
	public List<CampaniaDTO> obtenerCampaniasVigentes() throws ApiException {
		// TODO Auto-generated method stub
		
		System.out.print("entra obtener vigente");

		List<Campania> campanias;
		List<CampaniaDTO> dtos =new ArrayList <CampaniaDTO>();
		
		try {
			campanias = campaniaDao.campaniasVigentes();
			for (Campania c : campanias) {
				
				System.out.print("nombre campania:" + c.obtenerNombre()); 
				ArrayList<BeneficioDTO> beneficiosDtos = new ArrayList<>();
				List<Beneficio> beneficios = c.obtenerBeneficios();
				for (Beneficio b : beneficios) {
					beneficiosDtos
							.add(new BeneficioDTO(b.obtenerDescripcion(), b.obtenerPuntaje(), b.obtenerIdBeneficio()));				

				}
				// String nombre, Date fechaInicio, Date fechaFin, ArrayList<BeneficioDTO>
				// catalogo, Integer idCampania

					dtos.add(new CampaniaDTO(c.obtenerNombre(), c.obtenerFechaInicio(), c.obtenerFechaFin(), beneficiosDtos,
						c.obtenerId()));
			}
	
		return dtos;
		
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}

	}

	@Override
	public List<RecolectorDTO> obtenerRecolectores() throws ApiException {
		List<Recolector> recolectores;
		List<RecolectorDTO> dtos = new ArrayList<RecolectorDTO>();
		
		try {
			recolectores = recolectorDao.findAll();
			
				for (Recolector r : recolectores) {
					dtos.add(new RecolectorDTO(r.obtenerNombre(), r.obtenerApellido(), r.obtenerEmail(), r.obtenerDni(),
							r.obtenerLegajo()));

				}
		

		return dtos;
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}

	}

	@Override
	public List<CampaniaDTO> obtenerCampanias() throws ApiException {
		List<CampaniaDTO> dtos = new ArrayList <CampaniaDTO>();
		List<Campania> campanias;
		
		try {
			
				campanias = campaniaDao.findAll();
			
			
			for (Campania c : campanias) {
				ArrayList<BeneficioDTO> beneficiosDtos = new ArrayList<>();
				List<Beneficio> beneficios = c.obtenerBeneficios();
				for (Beneficio b : beneficios) {
					beneficiosDtos
							.add(new BeneficioDTO(b.obtenerDescripcion(), b.obtenerPuntaje(), b.obtenerIdBeneficio()));
				}
				dtos.add(new CampaniaDTO(c.obtenerNombre(), c.obtenerFechaInicio(), c.obtenerFechaFin(), beneficiosDtos,
						c.obtenerId()));
			}
	

		return dtos;
		}catch(NullException e) {
			throw new ApiException(e.getMessage());
		}catch(JdbcException e) {
			throw new ApiException(e.getMessage());
		}
	}

	@Override
	public List<BeneficioDTO> beneficiosDeCampaniaPorPuntos(int idCampania, int puntosPropietario) throws ApiException, PuntajeException{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stubList <BeneficioDTO> beneficiosDtos = new
		// ArrayList<>();
		List<BeneficioDTO> beneficiosDtos = new ArrayList<>();
		List<Beneficio> beneficios;
	
			try {
			beneficios = campaniaDao.beneficiosDeCampania(idCampania);
			for (Beneficio b : beneficios) {
				if (puntosPropietario >= b.obtenerPuntaje()) {
					BeneficioDTO beneficio = new BeneficioDTO(b.obtenerDescripcion(), b.obtenerPuntaje(),
							b.obtenerIdBeneficio());
					beneficiosDtos.add(beneficio);
				}
					
			}
			if(beneficiosDtos.isEmpty())
				throw new PuntajeException("");
		

		return beneficiosDtos;
			}catch(NullException e) {
				throw new ApiException(e.getMessage());
			}catch(JdbcException e) {
				throw new ApiException(e.getMessage());
			}
	}

	/////////// PREDICATE

	/*
	 * @Override public List<ViviendaDTO> filtradoViviendas( Predicate <ViviendaDTO>
	 * predicate) throws SQLException, NullException { return
	 * this.obtenerViviendas().stream().filter(predicate).collect(Collectors.toList(
	 * ));
	 */

	/*
	 * public <T> ViviendaDTO filtradoViviendas(List<T> list, Predicate<T>
	 * viviendas) throws NullException {
	 * 
	 * return
	 * this.obtenerViviendas().stream().filter(viviendas).collect(Collectors.toList(
	 * ));
	 * 
	 * }
	 */

	/*
	 * public OrdenDePedidoDTO obtenerOrdenDePedidoAdministrador(int numeroPedido,
	 * int numeroVivienda) throws AppException, NullException { List<ResiduoDTO>
	 * residuosDto = new ArrayList<>(); Vivienda vivienda =
	 * viviendaDao.find(numeroVivienda); Pedido pedido =
	 * pedidoDao.find(numeroPedido, vivienda);
	 * 
	 * OrdenDePedido orden = ordenDao.findPorAdministrador(numeroPedido);
	 * orden.setPedido(pedido);
	 * 
	 * PedidoDTO pedidoDto = this.obtenerPedido(numeroVivienda, numeroPedido);
	 * OrdenDePedidoDTO ordenDto = new OrdenDePedidoDTO(orden.getFechaPedido(),
	 * orden.getEstado(), orden.getId(), pedidoDto);
	 * 
	 * return ordenDto; }
	 */

}
