package com.gui.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.sql.Connection;
import java.util.Vector;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sqlcon.sqlConnection;

import net.proteanit.sql.DbUtils;

import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;


public class showSemResult extends JFrame {
	
	String tblName="",roll="";
	Integer semester=1;
	Connection connection = null;
	
	private JPanel contentPane;
	//private JTable table,table1,table2;
	//private JFrame frame;
	//private JPanel panel;
	//private JScrollPane scrollPane,scrollPane1,scrollPane2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					showSemResult obj = new showSemResult();
					//obj.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public showSemResult() {
		//getContentPane().setBackground(Color.GRAY);
		
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//		
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(82, 75, 292, 183);
//		contentPane.add(scrollPane);
//		
//		table = new JTable();
//		scrollPane.setViewportView(table);
//		
//		JLabel lblKyaHaalHai = new JLabel("kya haal hai !");
//		lblKyaHaalHai.setBounds(159, 32, 70, 15);
//		contentPane.add(lblKyaHaalHai);
		
//		frame = new JFrame();
//        
//		frame.getContentPane().setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
//		frame.getContentPane().setBackground(Color.GRAY);
//		frame.setBounds(100, 100, 500, 400);
//		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(null);
//		frame.setVisible(true);
//        
//		JLabel lblBharatiVidyapeethCollege = new JLabel("BHARATI VIDYAPEETH COLLEGE OF ENGINEERING");
//		lblBharatiVidyapeethCollege.setForeground(Color.GREEN);
//		lblBharatiVidyapeethCollege.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
//		lblBharatiVidyapeethCollege.setBounds(28, 12, 410, 28);
//		frame.getContentPane().add(lblBharatiVidyapeethCollege);
		
	}
	
	public showSemResult(String tblnm,Integer sem,String roll) {
		
		this.tblName = tblnm;
		this.semester = sem;
		this.roll = roll;
	}
	
	public void showRes(){
		
		
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
		
		//JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setBounds(82, 75, 292, 183);
		//contentPane.add(scrollPane);
		
		//table = new JTable();
		//scrollPane.setViewportView(table);
		
		//JLabel lblKyaHaalHai = new JLabel("kya haal hai !");
		//lblKyaHaalHai.setBounds(159, 32, 70, 15);
		//contentPane.add(lblKyaHaalHai);
		
		
		//roll = roll.trim();
		//System.out.println("\n"+tblName+"-"+semester+"-"+roll+"\n");
		this.connection=sqlConnection.dbConnector();
		if(roll.length()<7){roll="";
			try{
				
				String queryMain = "SELECT * FROM `"+tblName+"-"+semester+"` ;";
				
				JPanel panel = new JPanel();
				Vector columnNames = new Vector();
		        Vector data = new Vector();
				PreparedStatement pst = connection.prepareStatement(queryMain); 
				ResultSet rst = pst.executeQuery();
				ResultSetMetaData rsmd = rst.getMetaData();
				int columnsNumber = rsmd.getColumnCount();

				for (int i = 1; i <= columnsNumber; i++) {columnNames.addElement(rsmd.getColumnName(i));}
				while (rst.next()) {
	                Vector row = new Vector(columnsNumber);
	                for (int i = 1; i <= columnsNumber; i++) {
	                    row.addElement(rst.getObject(i));
	                }
	                data.addElement(row);
	            }
	            rst.close();
	            JTable table = new JTable(data, columnNames);
	            TableColumn column;
	            for (int i = 0; i < table.getColumnCount(); i++) {
	                column = table.getColumnModel().getColumn(i);
	                column.setMaxWidth(250);
	            }
	            JScrollPane scrollPane = new JScrollPane(table);        
	            panel.add(scrollPane);               
	            JFrame frame = new JFrame();
	            frame.getContentPane().add(panel);         //adding panel to the frame
	            frame.setSize(600, 600); //setting frame size
	            frame.setVisible(true);
	            
				//table.setModel(DbUtils.resultSetToTableModel(rst));
			
//			String queryTheory = "SELECT * FROM `theory-cse-mor-13-17-4`";//+tblName+"-"+semester+"` ;";
//			PreparedStatement pst1 = connection.prepareStatement(queryTheory); 
//			ResultSet rst1 = pst1.executeQuery();
//			table.setModel(DbUtils.resultSetToTableModel(rst1));
//			
//			String queryPractical = "SELECT * FROM `practical-"+tblName+"-"+semester+"` ;";
//			PreparedStatement pst2 = connection.prepareStatement(queryPractical); 
//			ResultSet rst2 = pst2.executeQuery();
//			table.setModel(DbUtils.resultSetToTableModel(rst2));
			
			
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e+" sql error1");
			}
			
		}
		else{
			try{
				//JOptionPane.showMessageDialog(null, "hamla ho gaya2-"+roll+"-");
//			String queryMain = "SELECT DISTINCT RES.NAME,DISTINCT RES.ROLL,RES.CREDITS,"
//					+ "DISTINCT theoryID,theoryMarks,DISTINCT practicalID,practicalMarks "
//					+ " FROM `"+tblName+"-"+semester+"` AS RES  JOIN `theory-"
//							+tblName+ "-"+semester+"` AS RES1 ON RES.ROLL="+"RES1.ROLL"+ " JOIN `practical-"
//									+tblName+"-"+semester+"` AS RES2 ON RES2.ROLL=\""+roll+ "\" ;";
			String queryMain = "SELECT * FROM `"+tblName+"-"+semester+"` WHERE ROLL=\""+roll+"\" ;";
			PreparedStatement pst = connection.prepareStatement(queryMain); 
			ResultSet rst = pst.executeQuery();
			//table.setModel(DbUtils.resultSetToTableModel(rst));
			
			String queryTheory = "SELECT * FROM `theory-"+tblName+"-"+semester+"` WHERE ROLL=\""+roll+"\" ;";
			PreparedStatement pst1 = connection.prepareStatement(queryTheory); 
			ResultSet rst1 = pst1.executeQuery();
			//table.setModel(DbUtils.resultSetToTableModel(rst1));
			
			String queryPractical = "SELECT * FROM `practical-"+tblName+"-"+semester+"` WHERE ROLL=\""+roll+"\" ;";
			PreparedStatement pst2 = connection.prepareStatement(queryPractical); 
			ResultSet rst2 = pst2.executeQuery();
			//table.setModel(DbUtils.resultSetToTableModel(rst2));
			
			
			JPanel panel = new JPanel();
			Vector columnNames = new Vector();
			Vector columnNames1 = new Vector();
			Vector columnNames2 = new Vector();
	        
			Vector data = new Vector();
	        Vector data1 = new Vector();
	        Vector data2 = new Vector();
			
	        ResultSetMetaData rsmd = rst.getMetaData();
	        ResultSetMetaData rsmd1 = rst1.getMetaData();
	        ResultSetMetaData rsmd2 = rst2.getMetaData();
						
			int columnsNumber1 = rsmd1.getColumnCount();
			for(int i=2; i<=columnsNumber1; i++) {columnNames1.addElement(rsmd1.getColumnName(i));}
			int columnsNumber2 = rsmd2.getColumnCount();
			for(int i=2; i<=columnsNumber2; i++) {columnNames2.addElement(rsmd2.getColumnName(i));}
			int columnsNumber = rsmd.getColumnCount();
			for (int i = 1; i <= columnsNumber; i++) {columnNames.addElement(rsmd.getColumnName(i));}

			while (rst1.next()) {
                Vector row = new Vector(
                		//columnsNumber+
                		columnsNumber1
                		//+columnsNumber2
                		);
                //for (int i = 1; i <= columnsNumber; i++) {
                //    row.addElement(rst.getObject(i));
                //}
                for (int i = 2; i <= columnsNumber1; i++) {
                    row.addElement(rst1.getObject(i));
                }
                //for (int i = 2; i <= columnsNumber2; i++) {
                //    row.addElement(rst2.getObject(i));
                //}
                data1.addElement(row);
            }
			while (rst2.next()) {
                Vector row = new Vector(columnsNumber2);
                for (int i = 2; i <= columnsNumber2; i++) {
                    row.addElement(rst2.getObject(i));
                }
                data2.addElement(row);
            }
			
			while (rst.next()) {
                Vector row = new Vector(columnsNumber);
                for (int i = 1; i <= columnsNumber; i++) {
                    row.addElement(rst.getObject(i));
                }
                data.addElement(row);
            }
			
			
            rst.close();rst1.close();rst2.close();
            //DefaultTableModel model = new DefaultTableModel();
            //JTable table = new JTable(model);
            JTable table1 = new JTable(data1, columnNames1);
            JTable table2 = new JTable(data2, columnNames2);
            JTable table = new JTable(data, columnNames);
            TableColumn column;
            for (int i = 0; i < table1.getColumnCount(); i++) {
                column = table1.getColumnModel().getColumn(i);
                column.setMaxWidth(250);
            }
            for (int i = 0; i < table2.getColumnCount(); i++) {
                column = table2.getColumnModel().getColumn(i);
                column.setMaxWidth(250);
            }
            for (int i = 0; i < table.getColumnCount(); i++) {
                column = table.getColumnModel().getColumn(i);
                column.setMaxWidth(250);
            }
            
            
            JScrollPane scrollPane = new JScrollPane(table);scrollPane.setPreferredSize(new Dimension(450,50));
            JScrollPane scrollPane1 = new JScrollPane(table1);scrollPane1.setPreferredSize(new Dimension(450,130));
            JScrollPane scrollPane2 = new JScrollPane(table2);scrollPane2.setPreferredSize(new Dimension(450,130));
            panel.add(scrollPane);
            panel.add(scrollPane1);
            panel.add(scrollPane2);
            
            JFrame frame = new JFrame();
            frame.getContentPane().add(panel);         //adding panel to the frame
            frame.setSize(500, 350); //setting frame size
            frame.setVisible(true);
            //frame.getContentPane().setLayout(null);
            
            
			
			}catch(Exception ee){
				JOptionPane.showMessageDialog(null, ee+" sql error2");
			}
			
		}
		
	}
}

