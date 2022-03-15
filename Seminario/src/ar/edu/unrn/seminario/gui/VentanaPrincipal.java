package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import javax.swing.JToolBar;
import javax.swing.JDesktopPane;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					VentanaPrincipal frame = new VentanaPrincipal(api, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal(IApi api, ResourceBundle labels) {
		// ii8n
		//ResourceBundle labels=ResourceBundle.getBundle("labels");
		ResourceBundle.getBundle("labels");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 863, 300);

		// ResourceBundle labels = ResourceBundle.getBundle("labels");
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("texto");
		setJMenuBar(menuBar);

		JMenu mnNewMenu_1 = new JMenu(labels.getString("ventana.principal.menu.item.orden")); //$NON-NLS-1$
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem(labels.getString("ventana.principal.menu.listado.ordenes"));
		mnNewMenu_1.add(mntmNewMenuItem_1);
		mntmNewMenuItem_1.addActionListener(e->{
				ListadoOrden listado = new ListadoOrden(api, labels);
				listado.setVisible(true);
			
		});

		JMenu campaniasMenu = new JMenu(labels.getString("ventana.principal.menu.item.campania")); //$NON-NLS-1$
		menuBar.add(campaniasMenu);

		JMenuItem creaBeneficioMenuItem = new JMenuItem(
				labels.getString("ventana.principal.menu.item.campania.registrar.beneficio")); //$NON-NLS-1$
		creaBeneficioMenuItem.addActionListener(e->{
				CrearBeneficio crear = new CrearBeneficio(api, labels);
				crear.setVisible(true);
		});
		campaniasMenu.add(creaBeneficioMenuItem);

		JMenuItem campaniaMenuItem = new JMenuItem(
				labels.getString("ventana.principal.menu.item.campania.registrar.campania")); //$NON-NLS-1$
		campaniaMenuItem.addActionListener(e->{
				RegistrarCampania registrar = new RegistrarCampania(api, labels);
				registrar.setVisible(true);
		});
		campaniasMenu.add(campaniaMenuItem);

		JMenuItem canjearMenuItem = new JMenuItem(
				labels.getString("ventana.principal.menu.item.campania.canjear.beneficio")); //$NON-NLS-1$
		canjearMenuItem.addActionListener(e->{

				ListadoDePropietariosParaCanjeo listado;

				listado = new ListadoDePropietariosParaCanjeo(api, labels);
				listado.setVisible(true);
		});
		campaniasMenu.add(canjearMenuItem);

		JMenu UsuariosNewMenu = new JMenu(labels.getString("ventana.principal.menu.usuarios"));
		menuBar.add(UsuariosNewMenu);

		JMenuItem AltaModificacionNewMenuItem = new JMenuItem(
				labels.getString("ventana.principal.menu.alta.modificacion"));
		AltaModificacionNewMenuItem.addActionListener(e -> new AltaUsuario(api, false, labels).setVisible(true));

		UsuariosNewMenu.add(AltaModificacionNewMenuItem);

		JMenuItem ListadoNewMenuItem = new JMenuItem(labels.getString("ventana.principal.menu.item.listado"));
		ListadoNewMenuItem.addActionListener(e -> new ListarUsuario(api, labels).setVisible(true));
		UsuariosNewMenu.add(ListadoNewMenuItem);

		JMenu RolesNewMenu = new JMenu(labels.getString("ventana.principal.menu.rol"));
		menuBar.add(RolesNewMenu);

		JMenuItem AltaRolNewMenuItem = new JMenuItem(labels.getString("ventana.principal.menu.item.rol"));
		AltaRolNewMenuItem.addActionListener(e -> new AltaRol(api, labels).setVisible(true));

		RolesNewMenu.add(AltaRolNewMenuItem);

		JMenuItem ListarNewMenuItem = new JMenuItem(labels.getString("ventana.principal.menu.lista.rol"));
		ListarNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		RolesNewMenu.add(ListarNewMenuItem);

		JMenu ViviendasNewMenu = new JMenu(labels.getString("ventana.principal.menu.viviendas"));
		menuBar.add(ViviendasNewMenu);

		JMenuItem RegistrarViviendaNewMenuItem = new JMenuItem(
				labels.getString("ventana.principal.menu.item.registrar.vivienda"));
		RegistrarViviendaNewMenuItem.addActionListener(e->{
				RegistrarVivienda registrarVivienda = new RegistrarVivienda(api, labels);
				registrarVivienda.setVisible(true);

		});
		ViviendasNewMenu.add(RegistrarViviendaNewMenuItem);

		JMenuItem ListarViviendaNewMenuItem = new JMenuItem(
				labels.getString("ventana.principal.menu.item.listar.vivienda"));
		ListarViviendaNewMenuItem.addActionListener(e -> {

			ListadoVivienda listado;

			listado = new ListadoVivienda(api, labels);
			listado.setVisible(true);

		});
		ViviendasNewMenu.add(ListarViviendaNewMenuItem);

		JMenuItem ListadoPropietariosMenuItem = new JMenuItem(
				labels.getString("ventana.principal.menu.item.listado.propietarios"));
		ListadoPropietariosMenuItem.addActionListener(e->{

				ListadoPropietarios listadoPropietarios = new ListadoPropietarios(api, labels);

				listadoPropietarios.setVisible(true);
		});
		ViviendasNewMenu.add(ListadoPropietariosMenuItem);

		JMenu mnNewMenu = new JMenu(labels.getString("ventana.principal.menu.item.pedidos"));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem(labels.getString("ventana.principal.menu.item.pedido.de.retiro")); //$NON-NLS-1$
		mntmNewMenuItem.addActionListener(e->{
				ListadoDePedidos listadoPedido;

				listadoPedido = new ListadoDePedidos(api, labels);
				listadoPedido.setVisible(true);
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenu registrarRecolector = new JMenu(labels.getString("ventana.principal.menu.registrar.recolector")); //$NON-NLS-1$
		menuBar.add(registrarRecolector);

		JMenuItem registrar = new JMenuItem(labels.getString("ventana.principal.menu.item.registrar.recolector")); //$NON-NLS-1$
		registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				RegistrarRecolector registrar = new RegistrarRecolector(api, labels);
				registrar.setVisible(true);

			}
		});
		registrarRecolector.add(registrar);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem(
				labels.getString("ventana.principal.menu.item.listado.recolectores")); //$NON-NLS-1$
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ListarRecolectores listarRecolectores;

				listarRecolectores = new ListarRecolectores(api, labels);
				listarRecolectores.setVisible(true);

			}
		});
		registrarRecolector.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton SalirNewButton = new JButton(labels.getString("ventana.principal.menu.item.salir"));
		SalirNewButton.addActionListener(e->dispose());
		SalirNewButton.setBounds(179, 217, 89, 23);
		contentPane.add(SalirNewButton);

	}
}
