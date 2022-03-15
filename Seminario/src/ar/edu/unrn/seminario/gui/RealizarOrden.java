package ar.edu.unrn.seminario.gui;
/*
import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ModeloException.AppException;
import ModeloException.NullException;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenDePedidoDTO;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.dto.ResiduoDTO;

public class RealizarOrden extends JFrame {

	private JPanel contentPane;
	DefaultTableModel modelo;
	private JTable table;
	private JTable table_1;
	private boolean seleccionoRecolector;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { RealizarOrden frame = new
	 * RealizarOrden(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */ 
	/**
	 * Create the frame.
	 */
/*	public RealizarOrden(IApi api, OrdenDePedidoDTO ordenDto) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es"));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		

		 this.setTitle(labels.getString("realizar.orden.ventana"));

		JPanel panel = new JPanel();
		panel.setBounds(12, 0, 175, 158);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNumeroPedido = new JLabel(labels.getString("realizar.orden.label.numero.pedido"));
		lblNumeroPedido.setBounds(0, 0, 113, 15);
		panel.add(lblNumeroPedido);

		JLabel numeroPedido = new JLabel("");
		numeroPedido.setBounds(125, 0, 49, 15);
		panel.add(numeroPedido);
		numeroPedido.setText("" + ordenDto.getPedido().getNumeroPedido());

		JLabel lblFechaDelPedido = new JLabel(labels.getString("realizar.orden.label.fecha.pedido"));
		lblFechaDelPedido.setBounds(0, 92, 124, 15);
		panel.add(lblFechaDelPedido);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 20, 174, 60);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNewLabel.setText(ordenDto.getEstado());

		JLabel label = new JLabel("");
		label.setBounds(0, 119, 174, 15);
		panel.add(label);
		label.setText("" + ordenDto.getFechaPedido());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(264, 0, 377, 110);
		contentPane.add(scrollPane);

		table_1 = new JTable();

		String[] titulos = { labels.getString("listado.pedidos.titulos.residuo.numero"),
				labels.getString("listado.pedidos.titulos.residuo.nombre"),
				labels.getString("listado.pedidos.titulos.residuo.peso"),
				labels.getString("listado.pedidos.titulos.residuo.puntaje") };
		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		for (ResiduoDTO r : ordenDto.getPedido().getResiduoDto()) {

			modelo.addRow(new Object[] { r.getTipoResiduoDto().getId(), r.getTipoResiduoDto().getNombre(), r.getPeso(),
					r.getTipoResiduoDto().getPuntosResiduo() });
		}
		table_1.setModel(modelo);

		scrollPane.setViewportView(table_1);
		List<RecolectorDTO> recolectoresDto = new ArrayList<>();
		try {
			recolectoresDto = api.obtenerRecolectores();
		} catch (NullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] recolectores = new String[recolectoresDto.size()];
		int i = 0;

		for (RecolectorDTO r : recolectoresDto) {

			// System.out.println(r.obtenerApellido());
			recolectores[i] = "legajo:" + r.obtenerLegajo() + "|nombre:" + r.obtenerNombre() + "|apellido:"
					+ r.obtenerApellido();
			i++;
		}

		System.out.println(recolectores[0]);
		JComboBox comboBox = new JComboBox(recolectores);
		comboBox.setBounds(264, 111, 365, 24);
		contentPane.add(comboBox);

		Button button = new Button(labels.getString("realizar.orden.button.asignar.recolector"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String recolector = (String) comboBox.getSelectedItem();

				try {

					api.asignarRecolectorAOrden(recolector);
					seleccionoRecolector = true;
					JOptionPane.showMessageDialog(null, "Recolector asignado", "Info", JOptionPane.INFORMATION_MESSAGE);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		button.setBounds(484, 141, 128, 23);
		contentPane.add(button);
		JButton btnNewButton = new JButton(labels.getString("realizar.orden.button.generar"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (seleccionoRecolector == true) {
					JOptionPane.showMessageDialog(null, "Orden generada con exito!", "Info", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "seleccionar un recolector", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		});
		btnNewButton.setBounds(454, 239, 175, 25);
		contentPane.add(btnNewButton);

	}
}*/



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
import ar.edu.unrn.seminario.exception.ElegirRecolectorException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.accesos.RecolectorDao;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.OrdenDePedidoDTO;
import ar.edu.unrn.seminario.dto.PropietarioDTO;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.modelo.Recolector;

import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RealizarOrden extends JFrame {

	private JPanel contentPane;
/////////////////////////////////////////////////////////////////////////////////

	DefaultTableModel modelo;
	private JTextField legajoField;
	private JTable table_1;
	private IApi api;
	private boolean seleccionoRecolector;
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
	public RealizarOrden(IApi api, OrdenDePedidoDTO ordenDto,ResourceBundle labels){

		this.api =api;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 39, 386, 197);
		contentPane.add(scrollPane);
		
		this.setTitle(labels.getString("listado.recolectores.ventana"));


		table_1 = new JTable();
		
		String [] titulos ={
					labels.getString("listado.recolectores.columna.nombre"), labels.getString("listado.recolectores.columna.apellido"), 
					labels.getString("listado.recolectores.columna.email"), 
					labels.getString("listado.recolectores.columna.dni"),labels.getString("listado.recolectores.columna.legajo")
			};
		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		
		// Obtiene la lista de usuarios a mostrar
				List<RecolectorDTO> recolectores;
				try {
					recolectores = api.obtenerRecolectores();
					// Agrega los usuarios en el model
				for (RecolectorDTO r : recolectores) {
					modelo.addRow(new Object[] { r.obtenerNombre(), r.obtenerApellido(), r.obtenerEmail(), 
							r.obtenerDni(), r.obtenerLegajo() });
					}
				} catch (ApiException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				

				
		
		table_1.setModel(modelo);
		scrollPane.setViewportView(table_1);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 240, 764, 35);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton cerrarButton = new JButton(labels.getString("listado.recolectores.button.cancelar"));
		cerrarButton.addActionListener(e->{
				try {
				if(seleccionoRecolector==false) {
					 throw new ElegirRecolectorException("Debe elegir el recolector");
				}
				else {
					dispose();
				}
				
				}catch(ElegirRecolectorException e1) {
					 JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
		});
		cerrarButton.setBounds(315, 11, 89, 23);
		panel_1.add(cerrarButton);
		
		JLabel lblNewLabel = new JLabel(labels.getString("realizar.orden.label.asigne.un.recolector")); //$NON-NLS-1$
		lblNewLabel.setBounds(12, 14, 46, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton(labels.getString("realizar.orden.button.asignar.recolector")); //$NON-NLS-1$
		btnNewButton.addActionListener(e->{
				  try {

	                    int tablaElegida = table_1.getSelectedRow();
	                    if (tablaElegida >= 0) {
	                        String legajo = (String) table_1.getValueAt(tablaElegida, 4);

	                        
	                    //    PropietarioDTO propietarioDTO = api.obtenerPropietario(numeroDNI);
	                       RecolectorDTO recolectorDTO= api.obtenerRecolector(legajo) ; 
	                       

	   					api.asignarRecolectorAOrden(recolectorDTO);  
	   					seleccionoRecolector= true; 
	   					JOptionPane.showMessageDialog(null, "Recolector asignado", "Info", JOptionPane.INFORMATION_MESSAGE);
	                    dispose();

	                        
	                    } else {
	                        throw new ElegirRecolectorException("Ha ocurrido un error no se ha elegido ningun recolector");
	                    }
	                } catch (ElegirRecolectorException | ApiException e1) {
	                	e1.printStackTrace();
	                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                }
	        });
		
		btnNewButton.setBounds(420, 139, 89, 23);
		contentPane.add(btnNewButton);
		
		
	
	}

	private void reloadGrid() {
			
			// Obtiene el model del table
			DefaultTableModel modelo = (DefaultTableModel) table_1.getModel();
			
			// Obtiene la lista de usuarios a mostrar
			List<RecolectorDTO> recolectores;
		
				try {
					recolectores = api.obtenerRecolectores();
					// Resetea el model
			modelo.setRowCount(0);

			// Agrega los usuarios en el model
			for (RecolectorDTO r : recolectores) {
				modelo.addRow(new Object[] { r.obtenerNombre(), r.obtenerApellido(), r.obtenerEmail(), r.obtenerDni(), r.obtenerLegajo() });
			}
		
				} catch (ApiException  e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

		}
}


