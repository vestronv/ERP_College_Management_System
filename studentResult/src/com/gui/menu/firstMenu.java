package com.gui.menu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class firstMenu extends JFrame {

	private JPanel contentPane;
	private JTextField rollNoTF;
	//private JFrame frame;
	static private String username,batchSel,branchSel="cse",shiftSel="mor",rollNo,tempSem;
	int semesterSel=1;
	static Connection connection;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					firstMenu frame = new firstMenu();					
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
	public firstMenu(String setUsername,Connection setConnection){
		username = setUsername;
		connection = setConnection;
	}
	
	public firstMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//contentPane.setVisible(true);
		
		String[] branch = new String[]{"cse","ece","eee","ice"};
		final JComboBox branchCB = new JComboBox(branch);
		branchCB.setBackground(Color.LIGHT_GRAY);
		branchCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				branchSel = (String)branchCB.getSelectedItem();
			}
		});
		branchCB.setEditable(false);
		branchCB.setBounds(40, 125, 80, 25);
		contentPane.add(branchCB);
		
		if(username==null)username="";
		JLabel lblHello = new JLabel("HELLO "+ username.toUpperCase());
		lblHello.setForeground(Color.YELLOW);
		lblHello.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblHello.setBounds(40,60,170,15);
		contentPane.add(lblHello);
		
		final JLabel lblBranch = new JLabel("Branch");
		lblBranch.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblBranch.setBounds(51, 105, 70, 15);
		contentPane.add(lblBranch);
		
		final JLabel lblShift = new JLabel("Shift");
		lblShift.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblShift.setBounds(147, 105, 70, 15);
		contentPane.add(lblShift);
		
		String[] shift = new String[]{"mor","eve"};
		final JComboBox shiftCB = new JComboBox(shift);
		shiftCB.setBackground(Color.LIGHT_GRAY);
		shiftCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				branchSel = (String)shiftCB.getSelectedItem();
			}
		});
		shiftCB.setEditable(false);
		shiftCB.setBounds(133, 125, 70, 24);
		contentPane.add(shiftCB);
		
		rollNoTF = new JTextField();
		rollNoTF.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		rollNoTF.setForeground(Color.BLUE);
		rollNoTF.setBackground(Color.LIGHT_GRAY);
		rollNoTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollNo = rollNoTF.getText();
			}
		});
		rollNoTF.setBounds(234, 125, 140, 25);
		contentPane.add(rollNoTF);
		rollNoTF.setColumns(10);
		
		final JLabel lblRollNo = new JLabel("Roll No");
		lblRollNo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblRollNo.setBounds(262, 105, 70, 15);
		contentPane.add(lblRollNo);
		
		String[] batches = new String[]{"2012","2013","2014","2015"};
		final JComboBox yearCB = new JComboBox(batches);
		yearCB.setBackground(Color.LIGHT_GRAY);
		yearCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				batchSel = (String)yearCB.getSelectedItem();
			}
		});
		yearCB.setEditable(false);
		yearCB.setBounds(400, 125, 100, 24);
		contentPane.add(yearCB);
		
		
		String[] semesters = new String[]{"1","2","3","4","5","6","7","8"};
		final JComboBox semesterCB = new JComboBox(semesters);
		semesterCB.setBackground(Color.LIGHT_GRAY);
		semesterCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tempSem = (String)semesterCB.getSelectedItem();
			}
		});
		semesterCB.setBounds(530, 125, 82, 24);
		contentPane.add(semesterCB);
		
		JLabel lblSemester = new JLabel("Semester");
		lblSemester.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblSemester.setBounds(533, 105, 70, 15);
		contentPane.add(lblSemester);
		
		JLabel lblBatch = new JLabel("Batch");
		lblBatch.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblBatch.setBounds(415, 105, 70, 15);
		contentPane.add(lblBatch);
		
		JButton showResButton = new JButton("Show Result");
		showResButton.setBackground(Color.YELLOW);
		showResButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		showResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*if(rollNo == null){
					JOptionPane.showMessageDialog(null, "Insert roll number carefully.");
				}else*///if roll=null than output whole class result... otherwise that praticular student.ROGER THAT!!
				{
					try{
						//display result window. . . ie. connect to database like cse-mor-13-17.sqlite and fetch rows DONE.
						//make a new window separate.
						semesterSel = Integer.parseInt(tempSem);
						outputFinal outw = new outputFinal(branchCB.getSelectedItem().toString(),shiftCB.getSelectedItem().toString(),yearCB.getSelectedItem().toString(),String.valueOf(rollNoTF.getText()),semesterSel);
						//outw.setVisible(true);
						outw.outwin();
						
						String query = "select * from loginCred;";
						PreparedStatement pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						//while(rs.next()){
						//	String t1=rs.getString("Username");
						//	JOptionPane.showMessageDialog(null, t1); /// here i am retrieving user name just to check...
						//}
					}catch(Exception sqlEve){
						System.out.println(sqlEve);
						JOptionPane.showMessageDialog(null, "SQL ERROR.");
						}
				}
			}
		});
		
		
		showResButton.setBounds(415, 229, 150, 25);
		contentPane.add(showResButton);
		
		final JButton uploadResultButton = new JButton("Upload Result");
		uploadResultButton.setBackground(Color.YELLOW);
		uploadResultButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		uploadResultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//uploadRes tmp = new uploadRes(connection);
				//tmp.dispose();
				uploadRes uploadFrame = new uploadRes(connection);
				uploadFrame.setVisible(true);
				uploadFrame.uploadRes111();
				
			}
		});
		uploadResultButton.setBounds(83, 229, 158, 25);
		contentPane.add(uploadResultButton);
		
		JLabel lblNewLabel = new JLabel("BHARATI VIDYAPEETH COLLEGE OF ENGINEERING");
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		lblNewLabel.setBounds(133, 12, 392, 25);
		contentPane.add(lblNewLabel);
		
		
		
		
		
		
	}
}
//------------------------------------------------------- YO VIMAL --------------------------------------------------------------