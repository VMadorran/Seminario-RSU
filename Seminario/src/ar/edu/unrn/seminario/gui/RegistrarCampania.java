package ar.edu.unrn.seminario.gui;

import ar.edu.unrn.seminario.herramienta.Fecha;

import java.util.Iterator;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import ar.edu.unrn.seminario.exception.ExisteException;
import ar.edu.unrn.seminario.exception.FechaException;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BeneficioDTO;
import ar.edu.unrn.seminario.dto.CampaniaDTO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class RegistrarCampania extends JFrame {

	private JPanel contentPane;
	DefaultTableModel modelo, modeloDos;
	private JTextField nombreField;
	private JTable table_1;
	private IApi api;
	private JTextField diaFechaInicioField;
	private JTextField diaFechaFinField;
	private JTable table_2;
	private JButton agregarButton;
	private ArrayList<BeneficioDTO> beneficiosDeCampania = new ArrayList<BeneficioDTO>();
	private List<BeneficioDTO> beneficios = new ArrayList<BeneficioDTO>();
	// private ArrayList<BeneficioDTO> replicaTablaBeneficio = new
	// ArrayList<BeneficioDTO>();
	private JTextField mesFechaFinField;
	private JTextField anioFechaFinField;
	private JTextField mesFechaInicioField;
	private JTextField anioFechaInicioField;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					RegistrarCampania frame = new RegistrarCampania(api);
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
	public RegistrarCampania(IApi api,ResourceBundle labels) {
		this.api = api;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 960, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	
		contentPane.setLayout(null);

		this.setTitle(labels.getString("registrar.campania.ventana"));

		String[] titulos = { labels.getString("registrar.campania.titulos.descripcion"),
				labels.getString("registrar.campania.titulos.puntaje") };
		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		// Obtiene la lista de usuarios a mostrar

		try {
			beneficios = api.obtenerBeneficios();
			// Agrega los usuarios en el model
			for (BeneficioDTO b : beneficios) {
				modelo.addRow(new Object[] { b.obtenerDescripcion(), b.obtenerPuntaje() });

			}
		} catch (ApiException  e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		////////////////////////////////////////////////////////////////////////////////////////////////////////
		JPanel panel = new JPanel();
		panel.setBounds(12, 5, 233, 336);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel nombreLabel = new JLabel(labels.getString("registrar.campania.titulo.nombre"));
		nombreLabel.setBounds(10, 52, 100, 14);
		panel.add(nombreLabel);

		////////// NOMBRE
		nombreField = new JTextField();
		nombreField.setBounds(112, 49, 111, 20);
		panel.add(nombreField);
		nombreField.setColumns(10);

		JLabel fechaInicioLabel = new JLabel(labels.getString("registrar.campania.titulo.fecha.inicio")); //$NON-NLS-1$
		fechaInicioLabel.setBounds(10, 149, 81, 14);
		panel.add(fechaInicioLabel);

		////////// Fecha de inicio//////////////

		JLabel fechaFinLabel = new JLabel(labels.getString("registrar.campania.titulo.fecha.final")); //$NON-NLS-1$
		fechaFinLabel.setBounds(10, 201, 81, 14);
		panel.add(fechaFinLabel);

		////////// Fecha de finalizacion////////////////
		diaFechaFinField = new JTextField();
		diaFechaFinField.setBounds(101, 195, 29, 20);
		panel.add(diaFechaFinField);
		diaFechaFinField.setColumns(10);

		mesFechaFinField = new JTextField();
		// mesFechaFinField.setText(labels.getString("RegistrarCampania.textField.text"));
		// //$NON-NLS-1$
		mesFechaFinField.setBounds(140, 195, 29, 20);
		panel.add(mesFechaFinField);
		mesFechaFinField.setColumns(10);

		anioFechaFinField = new JTextField();

		anioFechaFinField.setBounds(189, 195, 34, 20);
		panel.add(anioFechaFinField);
		anioFechaFinField.setColumns(10);

		////////////////////////////////// Fecha De
		////////////////////////////////// Inicio//////////////////////////////////////////
		diaFechaInicioField = new JTextField();
		diaFechaInicioField.setBounds(101, 146, 29, 20);
		panel.add(diaFechaInicioField);
		diaFechaInicioField.setColumns(10);

		mesFechaInicioField = new JTextField();

		mesFechaInicioField.setBounds(140, 146, 29, 20);
		panel.add(mesFechaInicioField);
		mesFechaInicioField.setColumns(10);

		anioFechaInicioField = new JTextField();
		// anioFechaInicioField.setText(labels.getString("RegistrarCampania.textField_2.text"));
		// //$NON-NLS-1$
		anioFechaInicioField.setBounds(189, 146, 34, 20);
		panel.add(anioFechaInicioField);
		anioFechaInicioField.setColumns(10);

		JLabel diaLabel = new JLabel(labels.getString("registrar.campania.titulos.dia")); //$NON-NLS-1$
		diaLabel.setBounds(101, 121, 29, 14);
		panel.add(diaLabel);

		JLabel mesLabel = new JLabel(labels.getString("registrar.campania.titulos.mes")); //$NON-NLS-1$
		mesLabel.setBounds(141, 121, 28, 14);
		panel.add(mesLabel);

		JLabel anioLabel = new JLabel(labels.getString("registrar.campania.titulos.anio")); //$NON-NLS-1$
		anioLabel.setBounds(189, 121, 34, 14);
		panel.add(anioLabel);

		JLabel lblNewLabel_1 = new JLabel((String) null);
		lblNewLabel_1.setBounds(161, 149, 18, 14);
		panel.add(lblNewLabel_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(255, 364, 677, 35);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JButton cancelarButton = new JButton(labels.getString("registrar.campania.button.cancelar"));
		cancelarButton.addActionListener(e->setVisible(false));
		cancelarButton.setBounds(578, 11, 89, 23);
		panel_1.add(cancelarButton);

		////////////////////// Crear Campania/////////////////////
		JButton crearButton = new JButton(labels.getString("registrar.campania.button.crear"));
		crearButton.addActionListener(e->{

				int reply = JOptionPane.showConfirmDialog(null,
						labels.getString("registrar.campania.ventana.emergente.mensaje"), // registrar.campania.ventana.emergente.mensaje
						labels.getString("registrar.campania.ventana.emergente.titulo"), // registrar.campania.ventana.emergente.titulo
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {

					try {
						try {
							api.registrarCampania(nombreField.getText(),

									Fecha.textoAFecha(Integer.parseInt(diaFechaInicioField.getText()),
											Integer.parseInt(mesFechaInicioField.getText()),
											Integer.parseInt(anioFechaInicioField.getText())),
									Fecha.textoAFecha(Integer.parseInt(diaFechaFinField.getText()),
											Integer.parseInt(mesFechaFinField.getText()),
											Integer.parseInt(anioFechaFinField.getText())),
									api.dtoAModelo(beneficiosDeCampania));
							
							JOptionPane.showMessageDialog(null, labels.getString("registrar.campania.mensaje.exito"),
									"Info", JOptionPane.INFORMATION_MESSAGE);
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (ApiException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (ExisteException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (FechaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}

				}

		});
		crearButton.setBounds(472, 11, 89, 23);
		panel_1.add(crearButton);

		modeloDos = new DefaultTableModel(new Object[][] {}, titulos);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		panel_2.setBounds(255, 5, 677, 355);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 43, 276, 271);
		panel_2.add(scrollPane);

///////////////////////////// TABLA PRINCIPAL
		table_1 = new JTable();
		table_1.setRowSelectionAllowed(true);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				habilitarBotones(true);
			}
		});
		// __________________________________________________________________________________________________________________________________

		JButton quitarButton = new JButton(labels.getString("registrar.campania.button.quitar"));
		quitarButton.addActionListener(e-> {
				int id = (int) table_2.getSelectedRow();
				eliminarBeneficioDeCampania(beneficiosDeCampania.get(id).obtenerIdBeneficio());
				reloadGridSecundario();
		});
		quitarButton.setBounds(491, 325, 89, 23);
		panel_2.add(quitarButton);

		table_1.setModel(modelo);
		scrollPane.setViewportView(table_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(391, 43, 276, 271);
		panel_2.add(scrollPane_1);
		/////////////////////////////////////////// Tabla 2

		table_2 = new JTable();
		table_2.setModel(modeloDos);
		scrollPane_1.setViewportView(table_2);

		agregarButton = new JButton(labels.getString("registrar.campania.button.agregar"));
		agregarButton.setBounds(296, 283, 85, 23);
		panel_2.add(agregarButton);


		agregarButton.addActionListener(e-> {

				// Recuperar los elementos del row
				int id = (int) table_1.getSelectedRow();

				// Crear dto de beneficio
				BeneficioDTO beneficio;

				try {
					beneficio = api.obtenerBeneficio(beneficios.get(id).obtenerIdBeneficio());
					beneficiosDeCampania.add(beneficio);

					// agregar elemento a la lista de beneficios

					// Actualizar tabla de beneficios de campania
					reloadGridSecundario();
				} catch (ApiException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

		});

		for (BeneficioDTO b : beneficiosDeCampania) {
			modeloDos.addRow(new Object[] { b.obtenerDescripcion(), b.obtenerPuntaje() });

		}
		habilitarBotones(false);

	}

	//////////////// METODOS
	private void habilitarBotones(boolean b) {
		agregarButton.setEnabled(b);

	}

	private void reloadGrid() {

		// Obtiene el model del table
		DefaultTableModel modelo = (DefaultTableModel) table_1.getModel();

		// Obtiene la lista de usuarios a mostrar

		try {
			beneficios = api.obtenerBeneficios();
			// Resetea el model
			modelo.setRowCount(0);

			// Agrega los usuarios en el model
			for (BeneficioDTO r : beneficios) {
				modelo.addRow(new Object[] { r.obtenerDescripcion(), r.obtenerPuntaje() });
				// modelo.addRow(new Object[] { r.obtenerDescripcion(), r.obtenerPuntaje()});
			}
		} catch (ApiException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void reloadGridSecundario() {

		DefaultTableModel modeloDos = (DefaultTableModel) table_2.getModel();
		modeloDos.setRowCount(0);

		for (BeneficioDTO b : this.beneficiosDeCampania) {
			modeloDos.addRow(new Object[] { b.obtenerDescripcion(), b.obtenerPuntaje() });

		}

	}

	private void eliminarBeneficioDeCampania(int id) {

		for (Iterator<BeneficioDTO> b = beneficiosDeCampania.iterator(); b.hasNext();) {
			BeneficioDTO beneficio = b.next();
			if (beneficio.obtenerIdBeneficio() == id)
				b.remove();

		}

	}



	void datoFaltante(ResourceBundle labels) {
		JOptionPane.showMessageDialog(null, labels.getString("canjear.puntos.ventana.emergente.faltan.datos"),
				labels.getString("canjear.puntos.ventana.emergente.datos.faltantes.titulo"), JOptionPane.ERROR_MESSAGE);
	}

}
