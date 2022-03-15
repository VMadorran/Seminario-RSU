package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.exception.ApiException;
import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.FiltradoException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.accesos.RecolectorDao;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;

import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListarRecolectores extends JFrame {

	private JPanel contentPane;
/////////////////////////////////////////////////////////////////////////////////

	DefaultTableModel modelo;
	private JTextField legajoField;
	private JTable table_1;
	private IApi api;
/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					ListarRecolectores frame = new ListarRecolectores(api);
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
	public ListarRecolectores(IApi api,ResourceBundle labels){

		this.api = api;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 5, 518, 231);
		contentPane.add(scrollPane);

		this.setTitle(labels.getString("listado.recolectores.ventana"));

		table_1 = new JTable();

		String[] titulos = { labels.getString("listado.recolectores.columna.nombre"),
				labels.getString("listado.recolectores.columna.apellido"),
				labels.getString("listado.recolectores.columna.email"),
				labels.getString("listado.recolectores.columna.dni"),
				labels.getString("listado.recolectores.columna.legajo") };
		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		// Obtiene la lista de usuarios a mostrar
		List<RecolectorDTO> recolectores;
		try {
			recolectores = api.obtenerRecolectores();
			// Agrega los usuarios en el model
			for (RecolectorDTO r : recolectores) {
				modelo.addRow(new Object[] { r.obtenerNombre(), r.obtenerApellido(), r.obtenerEmail(), r.obtenerDni(),
						r.obtenerLegajo() });
			}
		} catch (ApiException e3) {
			JOptionPane.showMessageDialog(null, e3.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		table_1.setModel(modelo);
		scrollPane.setViewportView(table_1);

		JPanel panel = new JPanel();
		panel.setBounds(540, 5, 234, 230);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel(labels.getString("listado.recolectores.label.filtradolegajo"));
		lblNewLabel.setBounds(10, 91, 100, 14);
		panel.add(lblNewLabel);

		legajoField = new JTextField();
		legajoField.setBounds(121, 88, 111, 20);
		panel.add(legajoField);
		legajoField.setColumns(10);

		JButton legajoFiltrarButton = new JButton(labels.getString("listado.recolectores.button.filtrar"));
		legajoFiltrarButton.setBounds(85, 132, 89, 23);
		legajoFiltrarButton.addActionListener(e-> {

				try {
					List<RecolectorDTO> recolectoresPorLegajo = api.obtenerFiltradoLegajo(legajoField.getText());
					if (modelo.getRowCount() > 0) {
						modelo.setRowCount(0);
						modelo.fireTableDataChanged();
					}
					for (RecolectorDTO r : recolectoresPorLegajo) {
						modelo.addRow(new Object[] { r.obtenerNombre(), r.obtenerApellido(), r.obtenerEmail(),
								r.obtenerDni(), r.obtenerLegajo() });

					}
					modelo.fireTableDataChanged();
					// modelo.fireTableDataChanged();
					if (recolectoresPorLegajo.size() == 0) {
						throw new FiltradoException(
								"No se encontro ningun recolector con el nro de legajo:" + legajoField.getText());

					}
				} catch (ApiException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

		});
		panel.add(legajoFiltrarButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 240, 764, 35);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JButton cerrarButton = new JButton(labels.getString("listado.recolectores.button.cancelar"));
		cerrarButton.addActionListener(e->setVisible(false));
		cerrarButton.setBounds(315, 11, 89, 23);
		panel_1.add(cerrarButton);

	}

	private void reloadGrid() {

		// Obtiene el model del table
		DefaultTableModel modelo = (DefaultTableModel) table_1.getModel();

		// Obtiene la lista de usuarios a mostrar
		List<RecolectorDTO> recolectores;

		try {
			recolectores = api.obtenerRecolectores();
			modelo.setRowCount(0);

			// Agrega los usuarios en el model
			for (RecolectorDTO r : recolectores) {
				modelo.addRow(new Object[] { r.obtenerNombre(), r.obtenerApellido(), r.obtenerEmail(), r.obtenerDni(),
						r.obtenerLegajo() });
			}
		} catch (ApiException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		// Resetea el model

	}

}