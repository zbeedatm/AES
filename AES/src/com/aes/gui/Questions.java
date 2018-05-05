package com.aes.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTable;

import com.aes.connection.MysqlConnection;
import com.aes.utils.Helper;

import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Questions {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Questions window = new Questions();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Questions() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		BorderLayout borderLayout = (BorderLayout) frame.getContentPane().getLayout();
		frame.setBounds(100, 100, 962, 645);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pnlHeader = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlHeader.getLayout();
		pnlHeader.setBackground(Color.WHITE);
		frame.getContentPane().add(pnlHeader, BorderLayout.NORTH);
		
		table = new JTable();
		ResultSet rs = MysqlConnection.getQuestions();

	    // It creates and displays the table
	    JTable table = null;
		try {
			table = new JTable(Helper.buildTableModel(rs));
			
			JOptionPane.showMessageDialog(null, new JScrollPane(table));
			frame.getContentPane().add(table, BorderLayout.CENTER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		JPanel pnlAnswers = new JPanel();
		frame.getContentPane().add(pnlAnswers, BorderLayout.SOUTH);
	}

}
