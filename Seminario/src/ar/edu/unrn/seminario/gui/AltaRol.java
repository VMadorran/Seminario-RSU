package ar.edu.unrn.seminario.gui;
import ar.edu.unrn.seminario.api.IApi;

import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.exception.ApiException;


public class AltaRol extends JFrame {

    private JPanel contentPane;
    private JTextField NombreRoltextField;

    /**
     * Launch the application.
     */
    /*
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AltaRol frame = new AltaRol();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    /**
     * Create the frame.
     */
    public AltaRol(IApi api,ResourceBundle labels) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
       
        JLabel lblNewLabel = new JLabel(labels.getString("alta.rol.label.titulo"));
        lblNewLabel.setBounds(10, 11, 161, 14);
        contentPane.add(lblNewLabel);
        
        this.setTitle(labels.getString("alta.rol.ventana"));

        JLabel NombreRolNewLabel_1 = new JLabel(labels.getString("alta.rol.label.nombre"));
        NombreRolNewLabel_1.setBounds(10, 64, 46, 14);
        contentPane.add(NombreRolNewLabel_1);

        JLabel EstadoRollblNewLabel_2 = new JLabel(labels.getString("alta.rol.label.estado"));
        EstadoRollblNewLabel_2.setBounds(10, 103, 46, 14);
        contentPane.add(EstadoRollblNewLabel_2);

        NombreRoltextField = new JTextField();
        NombreRoltextField.setBounds(66, 61, 86, 20);
        contentPane.add(NombreRoltextField);
        NombreRoltextField.setColumns(10);

        JButton CancelarNewButton_1 = new JButton(labels.getString("alta.rol.label.boton.cancelar"));
        CancelarNewButton_1.addActionListener(e-> dispose());
        CancelarNewButton_1.setBounds(319, 238, 89, 23);
        contentPane.add(CancelarNewButton_1);

        JCheckBox NewCheckBox = new JCheckBox(labels.getString("alta.rol.label.check"));
        NewCheckBox.setBounds(62, 99, 97, 23);
        contentPane.add(NewCheckBox);

        JButton RegistrarNewButton = new JButton(labels.getString("alta.rol.label.boton.registrar"));
        RegistrarNewButton.addActionListener(e->{
                try {
					api.guardarRol(NombreRoltextField.getText(), NewCheckBox.isSelected());
				} catch (ApiException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);//
				}
                JOptionPane.showMessageDialog(null, "Rol registrado con exito!", "Info", JOptionPane.INFORMATION_MESSAGE);//
                setVisible(false);
                dispose();
        }
        );
        RegistrarNewButton.setBounds(214, 238, 89, 23);
        contentPane.add(RegistrarNewButton);
    }
}