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

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import javax.swing.JToolBar;
import javax.swing.JDesktopPane;

public class VentanaPrincipalUsuario extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResourceBundle labels =ResourceBundle.getBundle("labels");
					IApi api = new PersistenceApi();
					VentanaPrincipalUsuario frame = new VentanaPrincipalUsuario(api, labels);
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
	public VentanaPrincipalUsuario(IApi api, ResourceBundle labels) {
		// ii8n
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 863, 300);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("texto");
		setJMenuBar(menuBar);

		JMenuItem canjearMenuItem = new JMenuItem(
				labels.getString("ventana.principal.menu.item.campania.canjear.beneficio"));
		canjearMenuItem.addActionListener(e->{

				ListadoDePropietariosParaCanjeo listado;
				listado = new ListadoDePropietariosParaCanjeo(api, labels);
				listado.setVisible(true);
			
		});

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

		JMenu mnCanjear = new JMenu(labels.getString("ventana.principal.menu.item.campania.canjear.beneficio")); //$NON-NLS-1$
		menuBar.add(mnCanjear);

		JMenuItem mntmCanjearPuntos = new JMenuItem(
				labels.getString("ventana.principal.menu.item.campania.canjear.beneficio")); //$NON-NLS-1$
		mntmCanjearPuntos.addActionListener(e->{

				ListadoDePropietariosParaCanjeo listado;

				listado = new ListadoDePropietariosParaCanjeo(api, labels);
				listado.setVisible(true);
		});
		mnCanjear.add(mntmCanjearPuntos);

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
