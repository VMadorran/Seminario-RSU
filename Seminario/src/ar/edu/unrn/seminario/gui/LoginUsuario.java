package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.exception.ApiException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;

import javax.swing.JRadioButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;

public class LoginUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField ingresoContrasenia;
	private JTextField ingresoUsuario;
	private ResourceBundle labels;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						 UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					IApi api = new PersistenceApi();

					LoginUsuario frame = new LoginUsuario(api);
					
					
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
	public LoginUsuario(IApi api) {
		setBackground(Color.LIGHT_GRAY);
		
		setTitle("Ingresar cuenta");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 691, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		labels = ResourceBundle.getBundle("labels", new Locale("es"));
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setForeground(Color.GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		ingresoContrasenia = new JTextField();
		ingresoContrasenia.setBounds(172, 145, 114, 19);
		panel.add(ingresoContrasenia);
		ingresoContrasenia.setColumns(10);

		JLabel lblNewLabel = new JLabel(labels.getString("iniciar.sesion.label.password"));
		lblNewLabel.setBounds(74, 147, 70, 15);
		panel.add(lblNewLabel);

		JLabel lblUsername = new JLabel(labels.getString("iniciar.sesion.label.usuario"));
		lblUsername.setBounds(74, 81, 87, 15);
		panel.add(lblUsername);

		ingresoUsuario = new JTextField();
		ingresoUsuario.setBounds(172, 79, 114, 19);
		panel.add(ingresoUsuario);
		ingresoUsuario.setColumns(10);

		Button button = new Button(labels.getString("iniciar.sesion.titulo"));
		button.setBounds(122, 200, 158, 23);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Khmer OS System", Font.PLAIN, 20));
		button.setBackground(Color.BLACK);
		button.addActionListener(e-> {
				try {
					boolean existe = api.ingresarUsuario(ingresoUsuario.getText(), ingresoContrasenia.getText());
					if (existe == true) {
						
						String nombreRol=api.usuarioTieneRol(ingresoUsuario.getText());
						if(nombreRol.equals("Propietario")) {
							VentanaPrincipalUsuario ventanaPrincipal = new VentanaPrincipalUsuario(api,labels);
							ventanaPrincipal.setVisible(true);
							JOptionPane.showMessageDialog(null, "Se ha registrado correctamente",
									"Info", JOptionPane.INFORMATION_MESSAGE);  //internacionalizacion
						}
						if(nombreRol.equals("Recolector")) {
							VentanaPrincipalRecolector ventanaPrincipal = new VentanaPrincipalRecolector(api,labels);
							ventanaPrincipal.setVisible(true);
							JOptionPane.showMessageDialog(null, "Se ha registrado correctamente", "Info", JOptionPane.INFORMATION_MESSAGE);
						}
						if(nombreRol.equals("Administrador")) {
							VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(api,labels);
							ventanaPrincipal.setVisible(true);
							JOptionPane.showMessageDialog(null, "Se ha registrado correctamente",
							"Info", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}

				catch (ApiException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		);
		panel.add(button);

		JLabel lblRegistrarcuenta = new JLabel(labels.getString("iniciar.sesion.label.registrarse"));
		lblRegistrarcuenta.setBounds(182, 175, 152, 19);
		lblRegistrarcuenta.setForeground(Color.BLUE);
		lblRegistrarcuenta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AltaUsuario altaUsuario = new AltaUsuario(api,true, labels);
				altaUsuario.setVisible(true);

			}
		});
		panel.add(lblRegistrarcuenta);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton(labels.getString("ventana.principal.menu.item.espaniol"));
		rdbtnNewRadioButton.setBounds(414, 175, 96, 23);
		rdbtnNewRadioButton.addActionListener(e-> labels = ResourceBundle.getBundle("labels", new Locale("es")));
		panel.add(rdbtnNewRadioButton);
		
		
			JRadioButton rdbtnNewRadioButton_1 = new JRadioButton(labels.getString("ventana.principal.menu.item.ingles")); //$NON-NLS-1$
			rdbtnNewRadioButton_1.setBounds(414, 214, 149, 23);
		rdbtnNewRadioButton_1.addActionListener(e-> labels = ResourceBundle.getBundle("labels", new Locale("en")));
		panel.add(rdbtnNewRadioButton_1);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnNewRadioButton);
	    group.add(rdbtnNewRadioButton_1);
		

		
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
