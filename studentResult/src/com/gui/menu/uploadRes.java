package com.gui.menu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sqlcon.sqlConnection;

import pdfParser.parse;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

public class uploadRes extends JFrame {

	private JPanel contentPane;
	private static Connection connection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					uploadRes frame = new uploadRes();
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
	static private String username="",batchSel="12",branchSel="cse",shiftSel="mor",semesterSel="1";
	public uploadRes(Connection connection){
		this.connection = connection;
	}
	public uploadRes(){
		//uploadRes111();    //uncomment to see GUI on running this .java file
	}
	public void uploadRes111() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] branch = new String[]{"cse","ece","eee","ice"};
		final JComboBox branchCB = new JComboBox(branch);
		branchCB.setBackground(Color.LIGHT_GRAY);
		branchCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				branchSel = (String)branchCB.getSelectedItem();
			}
		});
		branchCB.setBounds(34, 105, 75, 24);
		contentPane.add(branchCB);
		
		String[] shift = new String[]{"mor","eve"};
		final JComboBox shiftCB = new JComboBox(shift);
		shiftCB.setBackground(Color.LIGHT_GRAY);
		shiftCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shiftSel = (String)shiftCB.getSelectedItem();
			}
		});
		shiftCB.setBounds(140, 105, 75, 24);
		contentPane.add(shiftCB);
		
		String[] batches = new String[]{"2012","2013","2014","2015"};
		final JComboBox batchCB = new JComboBox(batches);
		batchCB.setBackground(Color.LIGHT_GRAY);
		batchCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				batchSel = (String)batchCB.getSelectedItem();
			}
		});
		batchCB.setBounds(250, 105, 75, 24);
		contentPane.add(batchCB);
		
		String[] semesters = new String[]{"1","2","3","4","5","6","7","8"};
		final JComboBox semesterCB = new JComboBox(semesters);
		semesterCB.setBackground(Color.LIGHT_GRAY);
		semesterCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				semesterSel = (String)semesterCB.getSelectedItem();
			}
		});
		semesterCB.setBounds(351, 105, 75, 24);
		contentPane.add(semesterCB);
		
		
		JButton btnUpload = new JButton("Upload");
		btnUpload.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		btnUpload.setBackground(Color.YELLOW);
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFile = new JFileChooser();
                openFile.showOpenDialog(null);
                String absPath = openFile.getSelectedFile().getPath();
                /*
                 * (1)->create table "branch+shift+batch"
                 * (2)->call the parse hard.
                 */
                //StringBuilder sBatch = new StringBuilder();
                StringBuilder eBatch = new StringBuilder();
                int ss=0,ee=0;
                String sBatch="";if(batchSel!="12")sBatch=batchSel.subSequence(2, 4).toString();//pp+=batchSel.charAt(3);
                else sBatch=batchSel;
                ss = Integer.parseInt(sBatch);
                ee=ss+4;
                //JOptionPane.showMessageDialog(null, ee);
                //sBatch.append(ss);
                eBatch.append(ee);
                String tblName = branchSel+"-"+shiftSel+"-"+sBatch.toString()+"-"+eBatch.toString()+"-"+semesterSel;
                //JOptionPane.showMessageDialog(null, tblName+"\n\n"+tblName.replaceFirst("mor", "eve"));
                //System.exit(0);
                String theorytblName = "theory-"+tblName;
                
                String theory = "CREATE TABLE `"+theorytblName+"` (ROLL varchar(20), theoryID INT, theoryMarks INT);";
                
                String practicaltblName = "practical-"+tblName;
                
                String practical = "CREATE TABLE `"+practicaltblName+"` (ROLL varchar(20), practicalID INT, practicalMarks INT);";
                
                String schema = " (ROLL varchar(20) PRIMARY KEY, NAME varchar(20),CREDITS INT); ";
                String query = "CREATE TABLE `"+tblName+"` "+schema;
                //CREATE  TABLE "main"."sfsd" ("sdfs" INTEGER PRIMARY KEY  NOT NULL )
                
                //System.out.println(Query);
                JOptionPane.showMessageDialog(null, query);
                try{
                	connection = sqlConnection.dbConnector();
                	PreparedStatement pst = connection.prepareStatement(query);
                	pst.executeUpdate();//created morning shift table..
                	pst = connection.prepareStatement(theory);pst.executeUpdate();
                	pst = connection.prepareStatement(practical);pst.executeUpdate();
                	
                	
                	pst = connection.prepareStatement(query.replaceFirst("mor", "eve"));
                	pst.executeUpdate();//created eve shift table ie. my TABLE :D
                	pst = connection.prepareStatement(theory.replaceFirst("mor", "eve"));pst.executeUpdate();
                	pst = connection.prepareStatement(practical.replaceFirst("mor", "eve"));pst.executeUpdate();
                	
                	parse parser = new parse(connection);
                    parser.parseHard(absPath,tblName);
                    
                }catch(Exception eee){JOptionPane.showMessageDialog(null, eee);}
                
			}
		});
		btnUpload.setBounds(160, 195, 117, 25);
		contentPane.add(btnUpload);
		
		JLabel lblBranch = new JLabel("Branch");
		lblBranch.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblBranch.setBounds(45, 90, 70, 15);
		contentPane.add(lblBranch);
		
		JLabel lblShift = new JLabel("Shift");
		lblShift.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblShift.setBounds(160, 90, 70, 15);
		contentPane.add(lblShift);
		
		JLabel lblBatch = new JLabel("Batch");
		lblBatch.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblBatch.setBounds(265, 90, 70, 15);
		contentPane.add(lblBatch);
		
		JLabel lblSemester = new JLabel("Semester");
		lblSemester.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblSemester.setBounds(355, 90, 70, 15);
		contentPane.add(lblSemester);
		
		JLabel lblNewLabel = new JLabel("BHARATI VIDYAPEETH COLLEGE OF ENGINEERING");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setBounds(23, 20, 392, 24);
		contentPane.add(lblNewLabel);
		
		
	}
}
//------------------------------------------------------- YO VIMAL --------------------------------------------------------------