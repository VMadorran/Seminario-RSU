package ar.edu.unrn.seminario.gui;

import ar.edu.unrn.seminario.herramienta.Fecha;
import ar.edu.unrn.seminario.modelo.Beneficio;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.exception.ApiException;
import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.exception.PuntajeException;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.BeneficioDTO;
import ar.edu.unrn.seminario.dto.CampaniaDTO;
import ar.edu.unrn.seminario.dto.PropietarioDTO;
import ar.edu.unrn.seminario.dto.RecolectorDTO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.border.LineBorder;
import javax.swing.JTextPane;
import javax.swing.JComboBox;

public class CanjearPuntos extends JFrame {

	private JPanel contentPane;
	DefaultTableModel modelo, modeloDos;
	private JTable table_1;
	private IApi api;
	private JTable table_2;
	private JButton listarBeneficiosCampaniaButton;
	private PropietarioDTO propietario;
	private List<CampaniaDTO> campanias = new ArrayList<CampaniaDTO>();
	private List<BeneficioDTO> beneficios = new ArrayList<BeneficioDTO>();

	/**
	 * Create the frame.
	 * 
	 */
	public CanjearPuntos(IApi api, PropietarioDTO propietario,ResourceBundle labels) {

		this.api = api;
		this.propietario = propietario;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 882, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		this.setTitle(labels.getString("canjear.puntos.ventana"));

		String[] titulosCampanias = { // Primera tabla
				labels.getString("canjear.puntos.titulo.descripcion"), labels.getString("canjear.puntos.titulo.inicio"),
				labels.getString("canjear.puntos.titulo.fin") };
		modelo = new DefaultTableModel(new Object[][] {}, titulosCampanias);

		
			try {
				campanias = api.obtenerCampaniasVigentes();
			} catch (ApiException e3) {
				JOptionPane.showMessageDialog(null, e3.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
			// Agrega los usuarios en el model
			for (CampaniaDTO c : campanias) {
				System.out.print("nombre campania:" + c.obtenerNombre());
				modelo.addRow(new Object[] { c.obtenerNombre(), c.obtenerFechaInicio(), c.obtenerFechaFin() });

			}
	

		String[] titulosBeneficios = { // Segunda tabla
				labels.getString("registrar.campania.titulos.descripcion"),
				labels.getString("registrar.campania.titulos.puntaje"), };

		modeloDos = new DefaultTableModel(new Object[][] {}, titulosBeneficios);// Segunda tabla

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		panel_2.setBounds(10, 11, 839, 370);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 54, 276, 271);
		panel_2.add(scrollPane);
		listarBeneficiosCampaniaButton = new JButton(labels.getString("registrar.campania.button.agregar"));
		deshabilitarBoton(listarBeneficiosCampaniaButton);

		///////////////////////////// TABLA PRINCIPAL
		table_1 = new JTable();
		table_1.setRowSelectionAllowed(true);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				habilitarBoton(listarBeneficiosCampaniaButton);
			}
		});

		table_1.setModel(modelo);
		scrollPane.setViewportView(table_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(332, 54, 276, 271);
		panel_2.add(scrollPane_1);
		JButton canjearBeneficioButton = new JButton(labels.getString("canjear.puntos.button.canjear.beneficios"));
		deshabilitarBoton(canjearBeneficioButton);

		/////////////////////////////////////////// Tabla 2

		JTextPane puntostextPane = new JTextPane();

		table_2 = new JTable();
		table_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// habilitar boton de canjear
				habilitarBoton(canjearBeneficioButton);

			}
		});
		table_2.setModel(modeloDos);
		scrollPane_1.setViewportView(table_2);

		listarBeneficiosCampaniaButton.addActionListener(e-> {

				int fila = (int) table_1.getSelectedRow();
				// Recuperar el id de la campania

				try {

					int puntos=api.consultarPuntosCliente(propietario.getDni());
					System.out.println("Punto del propietario: "+ puntos);
					
						beneficios.addAll(api.beneficiosDeCampaniaPorPuntos(campanias.get(fila).obtenerId(),puntos));
					
					
					reloadGridSecundario();					
				
					
				} catch (ApiException | PuntajeException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		);

		listarBeneficiosCampaniaButton.setBounds(95, 336, 87, 23);
		panel_2.add(listarBeneficiosCampaniaButton);

		
		puntostextPane.setFont(new Font("Arial", Font.BOLD, 18));
		puntostextPane.setEditable(false);
		puntostextPane.setBounds(645, 102, 56, 32);
		int puntos;
		try {
			puntos = api.consultarPuntosCliente(propietario.getDni());
			puntostextPane.setText(String.valueOf(puntos));
		} catch (ApiException e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			e2.printStackTrace();
		} catch (PuntajeException e2) {
			// TODO Auto-generated catch block
			puntosInsuficientes(labels);
			e2.printStackTrace();
		}


		panel_2.add(puntostextPane);

		canjearBeneficioButton.setBounds(386, 336, 139, 23);
		panel_2.add(canjearBeneficioButton);

		JLabel campaniasVigentesLabel = new JLabel(labels.getString("canjear.puntos.titulo.campania.vigente"));
		campaniasVigentesLabel.setBounds(10, 11, 55, 14);
		panel_2.add(campaniasVigentesLabel);

		JLabel beneficiosCampaniaLabel = new JLabel(labels.getString("canjear.puntos.titulo.beneficios.campania"));
		beneficiosCampaniaLabel.setBounds(332, 11, 276, 14);
		panel_2.add(beneficiosCampaniaLabel);

		JLabel puntosDisponiblesLabel = new JLabel(labels.getString("canjear.puntos.titulo.cantidad.puntos"));
		puntosDisponiblesLabel.setBounds(616, 61, 213, 50);
		panel_2.add(puntosDisponiblesLabel);

		JButton btnNewButton = new JButton(labels.getString("alta.rol.label.boton.cancelar")); //$NON-NLS-1$
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnNewButton.setBounds(653, 336, 139, 23);
		panel_2.add(btnNewButton);

		//////////////////// BOTON CANJEAR
		canjearBeneficioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int reply = JOptionPane.showConfirmDialog(null,
						labels.getString("canjear.puntos.ventana.emergente.mensaje"),
						labels.getString("canjear.puntos.ventana.emergente.titulo"),
						// api.canjearPuntos(propietario.getDni(),
						// beneficios.get(fila).obtenerIdBeneficio(), Fecha.fechaHoy());
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					int fila = (int) table_2.getSelectedRow();
						try {
							
							int puntos = 0;
							
								puntos = api.consultarPuntosCliente(propietario.getDni());
							
							System.out.println("En canjear");
							
							
							System.out.println("Puntos del cliente: "+ puntos);
							
								api.canjearPuntos(propietario.getDni(),beneficios.get(fila).obtenerIdBeneficio() , Fecha.fechaHoy());
							
							
							int puntajeActualizado = 0;
							
								puntajeActualizado = api.consultarPuntosCliente(propietario.getDni());
								
							puntostextPane.setText(String.valueOf(puntajeActualizado));
							System.out.println("Puntos del cliente actualizado: "+ puntajeActualizado);
						} catch (ApiException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
						}catch (PuntajeException e1) {
						
						puntosInsuficientes(labels);
						e1.printStackTrace();
					}
				}//
			}//¿
		});

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////// METODOS
	private void habilitarBoton(JButton boton) {
		boton.setEnabled(true);

	}

	private void deshabilitarBoton(JButton boton) {
		boton.setEnabled(false);

	}

	private void reloadGridSecundario() {

		DefaultTableModel modeloDos = (DefaultTableModel) table_2.getModel();
		modeloDos.setRowCount(0);

		// Obtiene la lista de usuarios a mostrar

		// Resetea el model
		modeloDos.setRowCount(0);

		// Agrega los usuarios en el model
		for (BeneficioDTO r : beneficios) {
			modeloDos.addRow(new Object[] { r.obtenerDescripcion(), r.obtenerPuntaje(), r.obtenerPuntaje() });

		}
	}

	private void puntosInsuficientes(ResourceBundle labels) {

		JOptionPane.showMessageDialog(null, labels.getString("canjear.puntos.ventana.emergente.puntos.insuficientes"),
				labels.getString("canjear.puntos.ventana.emergente.puntos.insuficientes.titulo"),
				JOptionPane.ERROR_MESSAGE);

	}

}
