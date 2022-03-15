package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
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
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.PropietarioDTO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListadoDePropietariosParaCanjeo extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelo;
	IApi api;
	private JButton btnNewButton;
	private Panel panel;
	private JTextField textField;
	private JTextField textField_1;
	private boolean encontro = false;

	/**
	 * Create the frame.
	 *
	 * @throws NullException
	 * @throws SQLException
	 */
	
	public ListadoDePropietariosParaCanjeo(IApi api,ResourceBundle labels) {
		this.api = api;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 5, 487, 231);
		contentPane.add(scrollPane);

		JButton seleccionarButton = new JButton(labels.getString("listado.propietarios.para.canjeo.button.seleccionar"));
		seleccionarButton.addActionListener(e->{

				String dni = (String) table.getModel().getValueAt(table.getSelectedRow(), 2);

				PropietarioDTO propietario=null;
			
					try {
						propietario = api.obtenerPropietario(dni);
						CanjearPuntos ventanaCanjear;
							ventanaCanjear = new CanjearPuntos(api, propietario,labels);
							ventanaCanjear.setVisible(true);

						
					} catch (ApiException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
			
		});
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				habilitarBoton(seleccionarButton);
			}
		});
		String[] titulos = { labels.getString("listado.propietarios.titulo.nombre"),
				labels.getString("listado.propietarios.titulo.apellido"),
				labels.getString("listado.propietarios.titulo.dni") };

		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		// Obtiene la lista de usuarios a mostrar
		List<PropietarioDTO> propietarios = null;
		
			try {
				propietarios = api.obtenerPropietarios();
			} catch (ApiException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			for (PropietarioDTO p : propietarios) {

			modelo.addRow(new Object[] { p.getNombre(), p.getApellido(), p.getDni() });
		}
		

		table.setModel(modelo);
		scrollPane.setViewportView(table);

		panel = new Panel();
		panel.setBounds(5, 236, 790, 35);
		contentPane.add(panel);

		btnNewButton = new JButton(labels.getString("listado.propietarios.para.canjeo.button.cerrar"));
		btnNewButton.addActionListener(e->{
				dispose();
		});
		panel.add(btnNewButton);

		Panel panel_1 = new Panel();
		panel_1.setBounds(527, 5, 263, 231);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		seleccionarButton.setBounds(35, 108, 188, 25);
		panel_1.add(seleccionarButton);

	}

	private void habilitarBoton(JButton boton) {
		boton.setEnabled(true);

	}

}