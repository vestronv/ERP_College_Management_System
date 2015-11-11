package com.gui.menu;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sqlcon.sqlConnection;

import pdfParser.*;

public class outputFinal extends JFrame {

	private JPanel contentPane;
	static String branchSel="cse",shiftSel="mor",batch,roll="";
	int semester=1;
	Connection connection = sqlConnection.dbConnector();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					outputFinal frame = new outputFinal();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public outputFinal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
	}
	public outputFinal(String branch, String shift, String batch,String roll,Integer sem) {
		this.branchSel = branch;
		this.shiftSel = shift;
		this.batch = batch;
		this.roll = roll;
		if(roll.length()<4)roll="";
		this.semester = sem;
	}
	
	public boolean tblExists(String table,int i) {
	    try {
	    	String query = "SELECT 1 FROM `"+table+"-"+i+"` LIMIT 1";
	    	//System.out.println(query);
	    	PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rst = pst.executeQuery();
	        if(rst.next())return true;
	        else return false;
	    } catch (SQLException e) {
	         return false;
	    }
	}
	
	public void outwin(){
		//System.out.println(roll+branch+shift+batch);
		int batchStart = Integer.parseInt((String)batch.subSequence(2, 4));//batch.charAt(2)*10+batch.charAt(3);
		int batchEnd = batchStart+4;
		try{
			//rollNoInt = Integer.parseInt(roll);
		}catch(Exception ee){JOptionPane.showMessageDialog(null, ee);return;}
		Connection connection = sqlConnection.dbConnector();
		
		String tblName = branchSel+"-"+shiftSel+"-"+batchStart+"-"+batchEnd; // remember that we neeed to append semester in table name..
		
		try{
			
			//System.out.printf("CHAL PADA..");
			String query = "";
			int i=semester;
			//while( tblExists(tblName,i) ){i+=1;}
			if(tblExists(tblName,i)){
				try{
					//System.out.println("\nuff\n");
					showSemResult ssr = new showSemResult(tblName,semester,roll);
					//ssr.setVisible(true);
					ssr.showRes();
				}catch(Exception e){}
				
			}
//			PreparedStatement pst = connection.prepareStatement(query);
//			ResultSet rst = pst.executeQuery();
//			ResultSetMetaData rsmd = rst.getMetaData();
//			int columnsNumber = rsmd.getColumnCount();
//			for(int i=1; i<=columnsNumber;i++){System.out.printf(rsmd.getColumnName(i));System.out.print("\t");}
//			System.out.printf("\n\n");
//			while (rst.next()) {
//				for (int i = 1; i <= columnsNumber; i++) {
//					if (i > 1) System.out.print(",  ");
//					String columnValue = rst.getString(i);
//					System.out.print(columnValue + " " );
//				}
//				System.out.println("");
//			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			
		}
	}
}
//------------------------------------------------------- YO VIMAL --------------------------------------------------------------