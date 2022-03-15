package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import ar.edu.unrn.seminario.api.IApi;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class ListarCanjes extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelo;
	IApi api;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public ListarCanjes(IApi api,ResourceBundle labels) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 746, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGap(0, 424, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGap(0, 251, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
		
		table = new JTable();
		String[] titulos = { labels.getString("listar.canjes.fecha"),
				labels.getString("listar.canjes.nombre.beneficio"),
				labels.getString("listar.canjes.puntos") };

		modelo = new DefaultTableModel(new Object[][] {}, titulos);
		
		
	
	
	}




}
