package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.exception.ApiException;
import ar.edu.unrn.seminario.exception.ExisteException;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BeneficioDTO;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CrearBeneficio extends JFrame {

	private JPanel contentPane;
	private JTextField descripcionField;
	private JTextField puntajeField;
	private IApi api;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public CrearBeneficio(IApi api,ResourceBundle labels) {
		this.api = api;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 545, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.setTitle(labels.getString("crear.beneficio.ventana"));
		
		
		
		descripcionField = new JTextField();
		descripcionField.setBounds(198, 109, 245, 40);
		contentPane.add(descripcionField);
		descripcionField.setColumns(10);
		
		puntajeField = new JTextField();
		puntajeField.setBounds(198, 196, 86, 20);
		contentPane.add(puntajeField);
		puntajeField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel(labels.getString("crear.beneficio.descripcion"));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(93, 119, 76, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(labels.getString("crear.beneficio.puntaje"));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(93, 199, 76, 17);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(labels.getString("crear.beneficio.label.ingreso.datos"));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(124, 36, 291, 40);
		contentPane.add(lblNewLabel_2);
		
		
		JButton btnNewButton = new JButton(labels.getString("crear.beneficio.button.crear"));
		btnNewButton.addActionListener((e)-> {
	
				
						
							BeneficioDTO beneficio= new BeneficioDTO(descripcionField.getText(), Integer.parseInt(puntajeField.getText()));
							
									try {
										api.registrarBeneficio(descripcionField.getText(), Integer.parseInt(puntajeField.getText()));
										JOptionPane.showMessageDialog(null, "Beneficio registrado con exito!", "Info", JOptionPane.INFORMATION_MESSAGE);
									} catch (NumberFormatException | ApiException | ExisteException  e1) {
										JOptionPane.showMessageDialog(null, e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
									}
											
	
				
				
					
			
		});
		
	

		btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
		btnNewButton.setBounds(151, 294, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.addActionListener(e->{
				dispose();
		});
		btnNewButton_1.setBounds(392, 295, 89, 23);
		contentPane.add(btnNewButton_1);
		
		
	}
	

	void datoFaltante(ResourceBundle labels) {
		JOptionPane.showMessageDialog(null,labels.getString("canjear.puntos.ventana.emergente.faltan.datos"), 
		labels.getString("canjear.puntos.ventana.emergente.datos.faltantes.titulo"),
		JOptionPane.ERROR_MESSAGE);
	}
}
