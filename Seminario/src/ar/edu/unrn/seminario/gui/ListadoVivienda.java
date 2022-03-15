package ar.edu.unrn.seminario.gui;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Comparator;
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
import ar.edu.unrn.seminario.exception.FiltradoException;
import ar.edu.unrn.seminario.exception.NullException;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ViviendaDTO;

public class ListadoVivienda extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelo;
	IApi api;
	JButton activarButton;
	JButton desactivarButton;
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
	public ListadoVivienda(IApi api,ResourceBundle labels) {
		this.api = api;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 5, 487, 236);
		contentPane.add(scrollPane);
		
		 this.setTitle(labels.getString("listado.viviendas.ventana"));

		table = new JTable();
		String[] titulos = { labels.getString("listado.viviendas.titulos.vivienda.numeroVivienda"),
				labels.getString("listado.viviendas.titulos.vivienda.DNI"),
				labels.getString("listado.viviendas.titulos.vivienda.NOMBRE"),
				labels.getString("listado.viviendas.titulos.vivienda.APELLIDO"),
				labels.getString("listado.viviendas.titulos.vivienda.ALTURA"),
				labels.getString("listado.viviendas.titulos.vivienda.BARRIO"),
				labels.getString("listado.viviendas.titulos.vivienda.CALLE") };

		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		// Obtiene la lista de usuarios a mostrar
		List<ViviendaDTO> viviendas;
		try {
			viviendas = api.obtenerViviendas();
			for (ViviendaDTO v : viviendas) {

			modelo.addRow(new Object[] { v.getNumeroVivienda(), v.getDnipropietarioDto(), v.getNombrePropietario(),
					v.getApellidopropietarioDto(), v.getNumero(), v.getBarrio(), v.getCalle() });
		}
		} catch (ApiException e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		// Agrega los usuarios en el model
		

		table.setModel(modelo);
		scrollPane.setViewportView(table);

		panel = new Panel();
		panel.setBounds(10, 255, 764, 35);
		contentPane.add(panel);

		btnNewButton = new JButton(labels.getString("listado.viviendas.button.cerrar"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panel.add(btnNewButton);

		Panel panel_1 = new Panel();
		panel_1.setBounds(505, 10, 269, 231);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblBuscarPorBarrio = new JLabel(labels.getString("listado.viviendas.filtrar.label"));
		lblBuscarPorBarrio.setBounds(22, 12, 127, 15);
		panel_1.add(lblBuscarPorBarrio);

		textField_1 = new JTextField();
		textField_1.setBounds(22, 33, 117, 19);
		panel_1.add(textField_1);
		textField_1.setColumns(10);

		JButton btnFiltrar_1 = new JButton(labels.getString("listado.viviendas.filtrar.button"));
		btnFiltrar_1.addActionListener(e->{

				try {

					List<ViviendaDTO> viviendasFiltradas = api.obtenerViviendaPorFiltrado(textField_1.getText());
					if (modelo.getRowCount() > 0) {
						modelo.setRowCount(0);
						modelo.fireTableDataChanged();
					}
					for (ViviendaDTO v : viviendasFiltradas) {
						modelo.addRow(new Object[] { v.getNumeroVivienda(), v.getDnipropietarioDto(),
								v.getNombrePropietario(), v.getApellidopropietarioDto(), v.getNumero(), v.getBarrio(),
								v.getCalle() });
					}
					modelo.fireTableDataChanged();
					if (viviendasFiltradas.size() == 0) {
						throw new FiltradoException("No se encontro ninguna vivienda:" + textField_1.getText());

					}
				} catch (ApiException | FiltradoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

				} 

			
		});

		btnFiltrar_1.setBounds(154, 30, 103, 25);
		panel_1.add(btnFiltrar_1);

	
		JButton btnNewButton_1 = new JButton(labels.getString("listado.viviendas.Pedido.button"));
		btnNewButton_1.addActionListener(e->{
				try {

					int tablaElegida = table.getSelectedRow();
					if (tablaElegida >= 0) {
						int numeroVivienda = (int) table.getValueAt(tablaElegida, 0);

						ViviendaDTO viviendaDto = api.obtenerVivienda(numeroVivienda);

						RealizarPedido realizarPedido = new RealizarPedido(api, viviendaDto,labels);

						realizarPedido.setVisible(true);
						System.out.println(viviendaDto.getNumeroVivienda());
					} else {
						throw new Exception("Ha ocurrido un error no se a elegido ninguna vivienda");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

				}
		});
		btnNewButton_1.setBounds(32, 159, 188, 25);
		panel_1.add(btnNewButton_1);

	

    JButton ModificarNewButton = new JButton(labels.getString("listado.viviendas.modificar.button"));
    ModificarNewButton.addActionListener(e-> {
            try {

                int tablaElegida = table.getSelectedRow();
                if (tablaElegida >= 0) {
                    int numeroVivienda = (int) table.getValueAt(tablaElegida, 0);

                    ViviendaDTO viviendaDto = api.obtenerVivienda(numeroVivienda);

                    ModificarDatosVivienda modificarDatosVivienda= new ModificarDatosVivienda(api, viviendaDto); 
                    modificarDatosVivienda.setVisible(true);
                } else {
                    throw new Exception("Ha ocurrido un error no se ha elegido ninguna vivienda");
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }


        });
    ModificarNewButton.setBounds(29, 195, 188, 25); 
    panel_1.add(ModificarNewButton);
    
    JButton ordenar = new JButton(labels.getString("Listado.Viviendas.Comparar.Nombre"));
    ordenar.addActionListener(e-> {
    		try {

    			Comparator<ViviendaDTO>	comparator = (ViviendaDTO v1, ViviendaDTO v2)->
    			(v1.getPropietario().getNombre().compareToIgnoreCase(v2.getPropietario().getNombre()));
				if (modelo.getRowCount() > 0) {
					modelo.setRowCount(0);
					modelo.fireTableDataChanged();
				}
				
				for (ViviendaDTO v : api.obtenerViviendas(comparator)) {
					modelo.addRow(new Object[] { v.getNumeroVivienda(), v.getDnipropietarioDto(),
							v.getNombrePropietario(), v.getApellidopropietarioDto(), v.getNumero(), v.getBarrio(),
							v.getCalle() });
				}
		
			
			} catch ( ApiException  e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

			} 
    		
    	
    });
    ordenar.setBounds(32, 64, 185, 25);
    panel_1.add(ordenar);
  
    
    JButton btnNewButton_2 = new JButton(labels.getString("Listado.Viviendas.Comparar.numero.vivienda"));
    btnNewButton_2.addActionListener(e-> {

    		try {

    			Comparator<ViviendaDTO>	comparator = (ViviendaDTO v1, ViviendaDTO v2)->
    			(String.valueOf(v1.getNumeroVivienda()).compareToIgnoreCase(String.valueOf(v2.getNumeroVivienda())));
				if (modelo.getRowCount() > 0) {
					modelo.setRowCount(0);
					modelo.fireTableDataChanged();
				}
				
				for (ViviendaDTO v : api.obtenerViviendas(comparator)) {
					modelo.addRow(new Object[] { v.getNumeroVivienda(), v.getDnipropietarioDto(),
							v.getNombrePropietario(), v.getApellidopropietarioDto(), v.getNumero(), v.getBarrio(),
							v.getCalle() });
				}
		
			
			} catch (ApiException  e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

			}
    });
    btnNewButton_2.setBounds(32, 101, 188, 25);
    panel_1.add(btnNewButton_2);

}
}