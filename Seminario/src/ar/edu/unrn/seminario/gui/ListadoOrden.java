package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.exception.ApiException;
import ar.edu.unrn.seminario.exception.JdbcException;
import ar.edu.unrn.seminario.exception.EstadoException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenDePedidoDTO;

public class ListadoOrden extends JFrame {

	private JPanel contentPane;
	DefaultTableModel modelo;
	private JTable table;
	private IApi api;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { ListadoOrden frame = new
	 * ListadoOrden(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 */
	public ListadoOrden(IApi api,ResourceBundle labels) {
		this.api = api;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 602, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 0, 409, 110);
		contentPane.add(scrollPane);

		table = new JTable();
		List<OrdenDePedidoDTO> ordenDto;
		try {
			ordenDto = api.obtenerListadoOrdenes();
			String[] titulos = { labels.getString("listado.orden.id"), labels.getString("listado.orden.fecha"),
				labels.getString("listado.orden.estado"), labels.getString("listado.orden.legajo") };
		modelo = new DefaultTableModel(new Object[][] {}, titulos);
		for (OrdenDePedidoDTO o : ordenDto) {
			
			modelo.addRow(new Object[] { o.getId(), o.getFechaPedido(), o.getEstado(),
					o.getRecolectorBasura().obtenerLegajo() });

		}
		} catch (ApiException e3) {
			JOptionPane.showMessageDialog(null, e3.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		

		table.setModel(modelo);
		scrollPane.setViewportView(table);

		JButton btnEjecutarVisita = new JButton(labels.getString("listado.orden.boton.registrar"));
		btnEjecutarVisita.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int tablaElegida = table.getSelectedRow();
				int numeroOrden = (int) table.getValueAt(tablaElegida, 0);
				//String estadoOrden = (String) table.getValueAt(tablaElegida, 2);
		
						OrdenDePedidoDTO ordenDto = null;
						try {
							ordenDto = api.obtenerOrdenDePedido(numeroOrden);
						} catch (ApiException  e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
						if(!ordenDto.ordenSeEncuentraConcretadaOCancelada()) {
							RegistrarVisita visita = new RegistrarVisita(api, ordenDto);
							visita.setVisible(true);
						}


				} 
			

			}
		);
		btnEjecutarVisita.setBounds(433, 24, 157, 25);
		contentPane.add(btnEjecutarVisita);

		btnNewButton_1 = new JButton(labels.getString("ventana.principal.menu.item.salir")); //$NON-NLS-1$
		btnNewButton_1.addActionListener(e-> dispose());
		btnNewButton_1.setBounds(485, 239, 117, 25);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton(labels.getString("listado.orden.cancelar")); //$NON-NLS-1$
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int tablaElegida = table.getSelectedRow();
				int numeroOrden = (int) table.getValueAt(tablaElegida, 0);
				OrdenDePedidoDTO ordenDto;
				try {
					ordenDto = api.obtenerOrdenDePedido(numeroOrden);
					if(!ordenDto.ordenSeEncuentraConcretadaOCancelada()) {
					ordenDto.cambiarEstadoCancelado();
					api.cambiarEstadoOrden(ordenDto.getId(), ordenDto.getEstado());
				}
				} catch (ApiException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} 
			}
		});
		btnNewButton_2.setBounds(457, 76, 117, 25);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton(labels.getString("listado.orden.boton.actualizar")); //$NON-NLS-1$
		btnNewButton_3.addActionListener(e->reloadGrid());
		btnNewButton_3.setBounds(169, 148, 117, 25);
		contentPane.add(btnNewButton_3);

	}

	public void reloadGrid() {

		table.getModel();

		// Obtiene la lista de usuarios a mostrar List<PedidoDTO> pedidos =
		List<OrdenDePedidoDTO> ordenDto;
		try {
			ordenDto = api.obtenerListadoOrdenes();
			modelo.setRowCount(0);

		for (OrdenDePedidoDTO o : ordenDto) {
			modelo.addRow(new Object[] { o.getId(), o.getFechaPedido(), o.getEstado(),
					o.getRecolectorBasura().obtenerLegajo(), });

		}
		} catch (ApiException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		

	}
}
