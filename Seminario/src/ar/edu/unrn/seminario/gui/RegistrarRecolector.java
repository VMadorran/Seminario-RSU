package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.exception.ApiException;
import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.ExisteException;
import ar.edu.unrn.seminario.exception.NullException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class RegistrarRecolector extends JFrame {

	private JPanel contentPane;
	private JTextField nombreField;
	private JTextField apellidoField;
	private JTextField emailField;
	private JTextField dniField;
	private JTextField legajoField;
	private IApi api;

	/**
	 * Launch the application.
	 */

	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					RegistrarRecolector frame = new RegistrarRecolector(api);
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
	public RegistrarRecolector(IApi api,ResourceBundle labels) {
		this.api = api;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 696, 349);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.setTitle(labels.getString("registrar.recolector.ventana"));

		JLabel tituloLabel = new JLabel("Registrar un nuevo recolector:");
		tituloLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tituloLabel.setBounds(120, 11, 183, 14);
		contentPane.add(tituloLabel);

		JLabel nombreLabel = new JLabel(labels.getString("registro.recolector.label.nombre"));
		nombreLabel.setBounds(116, 59, 60, 14);
		contentPane.add(nombreLabel);

		JLabel apellidoLabel = new JLabel(labels.getString("registro.recolector.label.apellido"));
		apellidoLabel.setBounds(116, 107, 50, 14);
		contentPane.add(apellidoLabel);

		JLabel emailLabel = new JLabel(labels.getString("registro.recolector.label.email"));
		emailLabel.setBounds(21, 151, 46, 14);
		contentPane.add(emailLabel);

		JButton registrarButton = new JButton(labels.getString("registro.recolector.button.registrar"));
		registrarButton.addActionListener(e-> {

				try {
					
						api.registrarRecolector(nombreField.getText(), apellidoField.getText(), emailField.getText(),
								dniField.getText(), legajoField.getText());
					
					JOptionPane.showMessageDialog(null, "Recolector registrado con exito!", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					// setVisible(false);
					// dispose();
				} catch (ApiException | ExisteException  e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

		});

		registrarButton.setBounds(88, 227, 89, 23);
		contentPane.add(registrarButton);

		JButton cancelarButton = new JButton(labels.getString("registro.recolector.button.cancelar"));
		cancelarButton.addActionListener(e->setVisible(false));
		cancelarButton.setBounds(237, 227, 89, 23);
		contentPane.add(cancelarButton);

		nombreField = new JTextField();
		nombreField.setBounds(176, 56, 86, 20);
		contentPane.add(nombreField);
		nombreField.setColumns(10);

		apellidoField = new JTextField();
		apellidoField.setBounds(176, 104, 86, 20);
		contentPane.add(apellidoField);
		apellidoField.setColumns(10);

		emailField = new JTextField();
		emailField.setBounds(91, 148, 86, 20);
		contentPane.add(emailField);
		emailField.setColumns(10);

		JLabel dniLabel = new JLabel(labels.getString("registro.recolector.label.dni"));
		dniLabel.setBounds(344, 56, 46, 14);
		contentPane.add(dniLabel);

		dniField = new JTextField();
		dniField.setBounds(418, 53, 86, 20);
		contentPane.add(dniField);
		dniField.setColumns(10);

		JLabel legajoLabel = new JLabel(labels.getString("registro.recolector.label.legajo"));
		legajoLabel.setBounds(344, 104, 46, 14);
		contentPane.add(legajoLabel);

		legajoField = new JTextField();
		legajoField.setBounds(418, 101, 86, 20);
		contentPane.add(legajoField);
		legajoField.setColumns(10);
	}
}
