package ar.edu.unrn.seminario.gui;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.PropietarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;

public class ListadoPropietarios extends JFrame {

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
    public ListadoPropietarios(IApi api,ResourceBundle labels){
        this.api = api;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 5, 487, 231);
        contentPane.add(scrollPane);

        this.setTitle(labels.getString("listado.propietarios.ventana"));
        
        
        table = new JTable();
        String[] titulos = { labels.getString("listado.propietarios.titulo.nombre"),
                labels.getString("listado.propietarios.titulo.apellido"),
                labels.getString("listado.propietarios.titulo.dni")};

        modelo = new DefaultTableModel(new Object[][] {}, titulos);

        // Obtiene la lista de usuarios a mostrar
        List<PropietarioDTO> propietarios;
		try {
			propietarios = api.obtenerPropietarios();
			for (PropietarioDTO p : propietarios) {

            modelo.addRow(new Object[] { p.getNombre(), p.getApellido(), p.getDni() });
        }
		} catch (ApiException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
        // Agrega los usuarios en el model
        

        table.setModel(modelo);
        scrollPane.setViewportView(table);

        panel = new Panel();
        panel.setBounds(5, 236, 790, 35);
        contentPane.add(panel);

        btnNewButton = new JButton(labels.getString("listado.propietarios.button.cerrar"));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        panel.add(btnNewButton);

        Panel panel_1 = new Panel();
        panel_1.setBounds(527, 5, 263, 231);
        contentPane.add(panel_1);
        panel_1.setLayout(null);



        JButton btnNewButton_1 = new JButton(labels.getString("listado.propietarios.button.modificar"));
        btnNewButton_1.addActionListener(e-> {

                try {

                    int tablaElegida = table.getSelectedRow();
                    if (tablaElegida >= 0) {
                        String numeroDNI = (String) table.getValueAt(tablaElegida, 2);

                        PropietarioDTO propietarioDTO = api.obtenerPropietario(numeroDNI);

                        ModificarDatosPropietario modificarDatosPropietario= new ModificarDatosPropietario(api, propietarioDTO,labels);
                        modificarDatosPropietario.setVisible(true);
                    } else {
                        throw new Exception("Ha ocurrido un error no se ha elegido ningun propietario");
                    }
                } catch (Exception e1) {
                	e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            
        });
        btnNewButton_1.setBounds(32, 159, 188, 25);
        panel_1.add(btnNewButton_1);

    }
}