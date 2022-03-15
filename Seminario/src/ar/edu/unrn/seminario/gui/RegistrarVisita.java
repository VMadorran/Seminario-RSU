package ar.edu.unrn.seminario.gui;

import java.awt.Button;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenDePedidoDTO;
import ar.edu.unrn.seminario.dto.ResiduoDTO;
import ar.edu.unrn.seminario.dto.TipoResiduoDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.herramienta.Fecha;

public class RegistrarVisita extends JFrame {

	private JPanel contentPane;
	private JTextField observacionestextField_4;
	private ViviendaDTO vivienda;
	DefaultTableModel modelo;
	private JTable table;
	private JTextField textField;
	private float kilos=0;
	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { RealizarPedido frame = new
	 * RealizarPedido(IApi api); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */
	/**
	 * Create the frame.
	 * @param labels2 
	 */
	public RegistrarVisita(IApi api, OrdenDePedidoDTO ordenDto) {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es"));
		JLabel lblNewLabel = new JLabel(labels.getString("visita.numero.orden"));

		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 0, 104, 46);
		contentPane.add(lblNewLabel);
		JLabel lblNewLabel_6 = new JLabel(labels.getString("realizarpedido.observacion"));
		lblNewLabel_6.setBounds(10, 120, 116, 14);
		contentPane.add(lblNewLabel_6);

		observacionestextField_4 = new JTextField();
		observacionestextField_4.setBounds(132, 120, 158, 54);
		contentPane.add(observacionestextField_4);
		observacionestextField_4.setColumns(10);

		JButton cancelarNewButton_1 = new JButton(labels.getString("ventana.principal.menu.item.salir"));
		cancelarNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelarNewButton_1.setBounds(624, 256, 126, 23);
		contentPane.add(cancelarNewButton_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(517, 0, 233, 134);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setColumnHeaderView(table);
		String[] titulos = { labels.getString("realizarpedido.titulo.table.numero"),
				labels.getString("realizarpedido.titulo.table.numero.tiporesiduo"),
				labels.getString("realizarpedido.titulo.table.tipo"),
				labels.getString("realizarpedido.titulo.table.kilo"),
				labels.getString("realizarpedido.titulo.table.puntos"),
				labels.getString("realizarpedido.titulo.table.kilo") 
				};

		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		List<ResiduoDTO> residuos;
		try {
				residuos = api.obtenerResiduosNoLevantados(ordenDto.getId());
			
			for (ResiduoDTO r : residuos) {
			System.out.println(r.getId());
			modelo.addRow(new Object[] { r.getId(), r.getTipoResiduoDto().getId(), r.getTipoResiduoDto().getNombre(),
					r.getPeso(), r.getTipoResiduoDto().getPuntosResiduo() });
		}
		} catch (ApiException e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		Label label = new Label();
		label.setBounds(289, 52, 206, 21);
		contentPane.add(label);
		label.setVisible(false);
		table.setModel(modelo);
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int tablaElegida = table.getSelectedRow();

				String nombreResiduo = (String) table.getValueAt(tablaElegida, 2);

				label.setVisible(true);
				label.setText(labels.getString("registrar.visita.retirar"));

				String nombreKilo = label.getText().concat(" " + nombreResiduo + "?");
				label.setText(nombreKilo);

			}
		});
		JButton pedidoNewButton = new JButton(labels.getString("registar.visita.button"));

		pedidoNewButton.addActionListener(e-> {

				List<ResiduoDTO> residuosDto = new ArrayList<>();
				
					
					for (int j = 0; j < table.getRowCount(); j++) {
						float kilosRetirados=0;
						int idResiduo = (int) table.getValueAt(j, 0);
						int idTipoResiduo = (int) table.getValueAt(j, 1);

						String nombreTipo = (String) table.getValueAt(j, 2);
						float peso = (float) table.getValueAt(j, 3);
						int puntos = (int) table.getValueAt(j, 4);
						
						if(table.getValueAt(j, 5)!=null) {
							kilosRetirados=(float) table.getValueAt(j, 5);
						}
						
						
						residuosDto.add(
								new ResiduoDTO(idResiduo, new TipoResiduoDTO(idTipoResiduo, nombreTipo, puntos), peso,kilosRetirados));

					}

					
					try {
						
							api.registrarVisita(Fecha.fechaHoy(), observacionestextField_4.getText(), residuosDto, ordenDto);
							JOptionPane.showMessageDialog(null, labels.getString("visita.registrar.mensaje.exito"),
									"Info", JOptionPane.INFORMATION_MESSAGE);
						for(ResiduoDTO r:residuosDto) {
							System.out.println("peso retirado :"+r.getPesoRetirado());
							System.out.println("el peso es:"+r.getPeso());
						}
						
						dispose();
					} catch ( ApiException  e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}

		});
		pedidoNewButton.setBounds(12, 256, 168, 23);
		contentPane.add(pedidoNewButton);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNewLabel_2.setText(Integer.toString(ordenDto.getId()));
		lblNewLabel_2.setBounds(126, 15, 50, 24);
		contentPane.add(lblNewLabel_2);


		textField = new JTextField();

		textField.setBounds(379, 91, 114, 19);
		contentPane.add(textField);
		textField.setColumns(10);

		Button button_1 = new Button(labels.getString("registrar.visita.button.retirar"));
		button_1.addActionListener(e-> {
	
				try {
					float kilosRetirados=0;
					kilos =Float.parseFloat(textField.getText());
					String kilosTablaString = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
					float kilosTablaInt = Float.parseFloat(kilosTablaString);
					if(table.getValueAt(table.getSelectedRow(), 5)!=null) {
						String kilosTablaRetirados = String.valueOf(table.getValueAt(table.getSelectedRow(), 5));
						 kilosRetirados=Float.parseFloat(kilosTablaRetirados);
					}
					
					
					if (Float.parseFloat(textField.getText()) >= 1 && Float.parseFloat(textField.getText()) <= kilosTablaInt) {
						table.setValueAt(kilosTablaInt -Float.parseFloat(textField.getText()), table.getSelectedRow(), 3);
						table.setValueAt(kilos+kilosRetirados, table.getSelectedRow(), 5);
						System.out.println(kilos);
					}
						
					else
						throw new Exception("el kilaje indicado es inadecuado");

				} catch (NumberFormatException e1) {
				
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e1) {
					
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

		});
		modelo.fireTableDataChanged();
		button_1.setBounds(389, 120, 86, 23);
		contentPane.add(button_1);

		

		JButton btnNewButton = new JButton(labels.getString("registrar.visita.concretar.button")); //$NON-NLS-1$
		btnNewButton.addActionListener(e-> {

				
				List<ResiduoDTO> residuosDto = new ArrayList<>();

				
				for (int j = 0; j < table.getRowCount(); j++) {

					int idResiduo = (int) table.getValueAt(j, 0);
					int idTipoResiduo = (int) table.getValueAt(j, 1);

					String nombreTipo = (String) table.getValueAt(j, 2);
					float peso = (float) table.getValueAt(j, 3);
					int puntos = (int) table.getValueAt(j, 4);

					
					residuosDto.add(
							new ResiduoDTO(idResiduo, new TipoResiduoDTO(idTipoResiduo, nombreTipo, puntos), peso,Float.parseFloat(textField.getText())));

				}

				
				try {
					System.out.print("vista id" + ordenDto.getPedido().getObservacion());
					if(api.pesoRecolectorEsMenor(residuosDto)) {
						int confirmar = JOptionPane.showConfirmDialog(null, "El peso retirado es menor que el peso del pedido, desea concretar la orden de todas maneras?" + "'?", "Confirmar modificacion.", JOptionPane.YES_NO_OPTION);
                        if (confirmar == JOptionPane.YES_OPTION) {
                        	api.registrarVisitaYconcretar(Fecha.fechaHoy(), observacionestextField_4.getText(), residuosDto, ordenDto);
        					JOptionPane.showMessageDialog(null, "registro con exito", "Visita",
        							JOptionPane.INFORMATION_MESSAGE);
        					dispose();
                        }
					}else {
						api.registrarVisitaYconcretar(Fecha.fechaHoy(), observacionestextField_4.getText(), residuosDto, ordenDto);
						JOptionPane.showMessageDialog(null, "registro con exito", "Visita",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
					
					
					
				} catch ( ApiException   e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
		});
		btnNewButton.setBounds(234, 256, 168, 23);
		contentPane.add(btnNewButton);
		
	}
}