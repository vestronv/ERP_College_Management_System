package com.gui.menu;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.sqlcon.*;
import java.sql.*;
import java.awt.Color;
import java.awt.Font;

public class loginWindow {

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	String usernametxt,passwordtxt;
	Connection connection = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginWindow window = new loginWindow();
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
	public loginWindow() {
		initialize();
		connection = null;
		usernametxt = "";
		passwordtxt = "";
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setForeground(Color.BLUE);
		txtUsername.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		txtUsername.setBackground(Color.LIGHT_GRAY);
		txtUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		txtUsername.setBounds(308, 86, 114, 28);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(Color.BLUE);
		passwordField.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		passwordField.setBackground(Color.LIGHT_GRAY);
		passwordField.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		passwordField.setBounds(308, 159, 114, 28);
		frame.getContentPane().add(passwordField);
		
		JLabel lblUsername = new JLabel("USERNAME :");
		lblUsername.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblUsername.setBackground(Color.ORANGE);
		lblUsername.setBounds(176, 92, 102, 15);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("PASSWORD :");
		lblPassword.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblPassword.setBackground(Color.ORANGE);
		lblPassword.setBounds(177, 158, 102, 15);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		btnLogin.setBackground(Color.YELLOW);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					usernametxt = txtUsername.getText();
					passwordtxt = String.valueOf(passwordField.getPassword());
					connection = sqlConnection.dbConnector();
					String query = "SELECT * FROM loginCred where USERNAME='"+usernametxt+"' and PASSWORD='"+passwordtxt+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					//pst.setString(1, usernametxt);
					//pst.setString(2, passwordtxt);
					ResultSet rs = pst.executeQuery();
					if(!rs.next()){
						JOptionPane.showMessageDialog(null, "Invalid Username and/or Password");
					}else{
						//if i show for limited time faltu ka code bada hoga...
						JOptionPane.showMessageDialog(null, "Logged In");
						frame.dispose();
						firstMenu tmpObj = new firstMenu(usernametxt,connection);
						firstMenu fmObj = new firstMenu();
						fmObj.setVisible(true);
						rs.close();
						//connection.close();
					}
					//fmObj.setVisible(true);
				}catch(Exception sqlError){
					sqlError.printStackTrace();
					frame.dispose();
				}finally{}
				
			}
		});
		btnLogin.setBounds(229, 229, 117, 25);
		frame.getContentPane().add(btnLogin);
		
		JLabel imglbl = new JLabel("");
		ImageIcon adminimg = new ImageIcon("./images/login1.png"); 
		imglbl.setIcon((adminimg));
		imglbl.setBounds(12, 72, 169, 152);
		frame.getContentPane().add(imglbl);
		
		JLabel lblBharatiVidyapeethCollege = new JLabel("BHARATI VIDYAPEETH COLLEGE OF ENGINEERING");
		lblBharatiVidyapeethCollege.setForeground(Color.GREEN);
		lblBharatiVidyapeethCollege.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		lblBharatiVidyapeethCollege.setBounds(28, 12, 410, 28);
		frame.getContentPane().add(lblBharatiVidyapeethCollege);
	}
}
//------------------------------------------------------- YO VIMAL --------------------------------------------------------------