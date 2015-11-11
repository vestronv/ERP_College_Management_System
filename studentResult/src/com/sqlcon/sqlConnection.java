package com.sqlcon;

import java.sql.*;
import javax.swing.*;

public class sqlConnection {
	Connection conn=null;
	public sqlConnection() {
		conn=null;
	}
	public static Connection dbConnector(){
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/vetron/workspace/studentResult/allDB.sqlite");
			//JOptionPane.showMessageDialog(null, "Succesfull Connection");
			return conn;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
	}

}
//------------------------------------------------------- YO VIMAL --------------------------------------------------------------