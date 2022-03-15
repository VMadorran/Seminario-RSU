package ar.edu.unrn.seminario.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import ar.edu.unrn.seminario.exception.ExisteException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenDePedidoDTO;
import ar.edu.unrn.seminario.dto.PedidoDTO;
import ar.edu.unrn.seminario.dto.ResiduoDTO;

public class ListadoDePedidos extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelo;
	IApi api;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table_1;
	private JTextField textField_2;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;

	/**
	 * Launch the application.
	 * 
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { ListadoDePedidos frame = new
	 * ListadoDePedidos(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 * 
	 * @throws NullException
	 * @throws SQLException
	 * 
	 * 
	 */
	public ListadoDePedidos(IApi api,ResourceBundle labels)  {

		this.api = api;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1065, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 0, 764, 270);
		contentPane.add(scrollPane);
		

		 this.setTitle(labels.getString("listado.pedidos.ventana"));


		table_1 = new JTable();

		String[] titulos = { labels.getString("listado.pedidos.titulos.id.vivienda"),
				labels.getString("listado.pedidos.titulos.nro.pedido"),
				labels.getString("listado.pedidos.titulos.fecha"), labels.getString("listado.pedidos.titulos.vehiculo"),
				labels.getString("listado.pedidos.titulos.observacion"),
				labels.getString("listado.pedidos.titulos.residuo") };
		modelo = new DefaultTableModel(new Object[][] {}, titulos);
		modelo.isCellEditable(5, 5);
		List<PedidoDTO> pedidos=null;
		try {
			pedidos = api.obtenerPedidos();
		} catch (ApiException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}

		for (PedidoDTO p : pedidos) {
			String residuo = "";
			for (ResiduoDTO r : p.getResiduoDto()) {
				System.out.println("el nombre es:" + r.getTipoResiduoDto().getNombre() + "el id es:"
						+ r.getTipoResiduoDto().getId() + "el peso es:" + r.getPeso());
				residuo += r.getTipoResiduoDto().toString() + " el peso es:" + r.getPeso();
			}
			modelo.addRow(new Object[] { p.obtenerVivienda().getNumeroVivienda(), p.getNumeroPedido(), p.getFecha(),
					p.getVehiculo(), p.getObservacion(), residuo });

		}

		table_1.setModel(modelo);

		scrollPane.setViewportView(table_1);

		JPanel panel = new JPanel();
		panel.setBounds(878, 0, 175, 300);
		contentPane.add(panel);
		panel.setLayout(null);

	/*	textField_2 = new JTextField();
		textField_2.setBounds(37, 58, 86, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
*/
	/*	btnNewButton = new JButton("Mostrar residuo"); 

		/*
		 * btnNewButton.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) {
		 * 
		 * VentanaResiduos ventana = new VentanaResiduos(api); ventana.setVisible(true);
		 * } });
		 */
/*		btnNewButton.setBounds(37, 107, 89, 23);
		panel.add(btnNewButton); 
*/
		JButton realizarOrden = new JButton(labels.getString("listado.pedidos.generar.orden"));
		realizarOrden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int tablaElegida = table_1.getSelectedRow();
				if (tablaElegida >= 0) {
					int numeroPedido = (int) table_1.getValueAt(tablaElegida, 1);
					int numeroVivienda = (int) table_1.getValueAt(tablaElegida, 0);
				
					try {
					
						PedidoDTO pedidoDto = api.obtenerPedido(numeroPedido);
						OrdenDePedidoDTO ordenDto = new OrdenDePedidoDTO(pedidoDto.getFecha(), pedidoDto);
					
						try {
							api.registrarOrden(numeroPedido, pedidoDto.getFecha(), ordenDto.getEstado(), numeroVivienda);
							JOptionPane.showMessageDialog(null, labels.getString("registrar.orden.mensaje.exito"),
									"Info", JOptionPane.INFORMATION_MESSAGE);
							RealizarOrden realizarOrden = null;
							realizarOrden = new RealizarOrden(api, ordenDto,labels);
						realizarOrden.setVisible(true);
						dispose();
						} catch (ApiException | ExisteException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
						
					} catch (ApiException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}


						
					

				}
			}
		});

		realizarOrden.setBounds(12, 248, 151, 25);
		panel.add(realizarOrden);

		
		
	/*	
		btnNewButton_2 = new JButton(labels.getString("ListadoDePedidos.boton.ver.orden")); //$NON-NLS-1$
		btnNewButton_2.setEnabled(false);
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int tablaElegida = table_1.getSelectedRow();
				if (tablaElegida >= 0) {
					int numeroPedido = (int) table_1.getValueAt(tablaElegida, 1);
					int numeroVivienda = (int) table_1.getValueAt(tablaElegida, 0);
					System.out.println("la tabla elegida es:" + numeroPedido);

					// arreglar esta parte y ponerla en el persistence
					OrdenDePedidoDTO ordenDto = null;

					RealizarOrden realizarOrden = new RealizarOrden(api, ordenDto);
					realizarOrden.setVisible(true);

				}

			}
		});
		btnNewButton_2.setBounds(0, 197, 163, 25);
		panel.add(btnNewButton_2);
*/
		
		JButton btnCerrar = new JButton(labels.getString("ventana.principal.menu.item.salir")); //$NON-NLS-1$
		btnCerrar.addActionListener(e->{
				dispose();
			});
		btnCerrar.setBounds(22, 275, 117, 25);
		contentPane.add(btnCerrar);

	}
}
/*
 * private void reloadGrid() throws SQLException, NullException {
 * 
 * // Obtiene el model del table DefaultTableModel modelo = (DefaultTableModel)
 * table_1.getModel();
 * 
 * // Obtiene la lista de usuarios a mostrar List<PedidoDTO> pedidos =
 * api.obtenerPedidos(); try { pedidos = api.obtenerPedidos(); // Resetea el
 * model modelo.setRowCount(0);
 * 
 * // Agrega los usuarios en el model for (PedidoDTO p : pedidos) {
 * modelo.addRow(new Object[] { p.obtenerVivienda().getNumero(), p.getFecha(),
 * p.getResiduoDto(), p.getVehiculo(), p.getObservacion(), p.getObservacion()
 * }); } } catch (SQLException | NullException e) { // TODO Auto-generated catch
 * block e.printStackTrace(); }
 * 
 * }
 */
