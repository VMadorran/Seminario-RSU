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

public class VentanaPrincipalRecolector extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					VentanaPrincipalRecolector frame = new VentanaPrincipalRecolector(api,null);
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
	public VentanaPrincipalRecolector(IApi api,ResourceBundle labels) {
		// ii8n
		// ResourceBundle labels=ResourceBundle.getBundle("labels");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 863, 300);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("texto");
		setJMenuBar(menuBar);

		JMenu mnNewMenu_1 = new JMenu(labels.getString("ventana.principal.menu.item.orden")); //$NON-NLS-1$
		menuBar.add(mnNewMenu_1);
		
				JMenuItem mntmNewMenuItem_1 = new JMenuItem(labels.getString("ventana.principal.menu.listado.ordenes"));
				mnNewMenu_1.add(mntmNewMenuItem_1);
				getContentPane().setLayout(null);
				
				JButton salirNewButton = new JButton(labels.getString("ventana.principal.recolector.button.salir"));
				salirNewButton.addActionListener(e->dispose());
				salirNewButton.setBounds(167, 206, 89, 23);
				getContentPane().add(salirNewButton);
				mntmNewMenuItem_1.addActionListener(e->{
						ListadoOrden listado = new ListadoOrden(api,labels);
						listado.setVisible(true);
				});
	}
}


