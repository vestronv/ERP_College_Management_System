package pdfParser;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.sqlcon.*;



public class parse {
	static Connection connection;
	//static String tblName;
	public parse(Connection conn) {
		connection = conn;
	}
	//user friendly using JFileChooser--doit brooo..  DONE :D
	/*FILE CHOOSER CODE with path extraction---
	 * JFileChooser chooser = new JFileChooser();
	 * File f = chooser.getSelectedFile();
	 * String fileName = f.getAbsolutePath().toStrin(); 
	 *  DDDDD OOOOO NNNNN EEEEE
	 * */
	public void parseHard(String absPathFile,String tblName){
		
		//connection = sqlConnection.dbConnector();
		/*Fill these two string by gettng branch roll marks etc
		 * from the constructor but make overloaded constructors as in some cases we might need to 
		 * show result of whole class not of specific roll_no.
		 * */
		String tableInfo,tableMarks;
		int stop=0;
		try{
			//connection = sqlConnection.dbConnector("");
			PdfTextExtractor parser =new PdfTextExtractor(new PdfReader(absPathFile));
			PdfReader reader = new PdfReader(new FileInputStream(absPathFile));
			
			boolean once=false,jump=true,gudone=false;
			int shiftDistinguish=0,theorySubjects=0,practicalSubjects=0;
			for(int pageNum=1;pageNum<290/*reader.getNumberOfPages()*/;pageNum++){//EACH PAGE 10 STUDENT
				String page = parser.getTextFromPage(pageNum);
				//System.out.println(page.length()+"len");
				if( !(page.toLowerCase().contains("ABSENT".toLowerCase()) 
						&& (page.toLowerCase().contains("BHARATI VIDYA".toLowerCase())) ) )
					{gudone=false;
					if(shiftDistinguish>50){
						
						
						//String query = "select * from studInfo join theory on studInfo.ROLL=theory.ROLL join practical on studInfo.ROLL=practical.ROLL";
						//PreparedStatement pst = connection.prepareStatement(query);
						//pst.executeUpdate();
						//////////////////////////////////connection.close();
						//System.exit(0);
						try{
							stop+=1;if(stop>1){
								System.out.println("aa gaya stop pe");
								break;
								// PLZZ SOME 1 CLOSE THE CONNECTION  //connection.close();
							}
							/////////////////////////////////////////connection = sqlConnection.dbConnector();
						}catch(Exception sqlError){JOptionPane.showMessageDialog(null, sqlError);}
						/***********************
						 * Put here code for changing to new table...ie now the table will be different
						 * like cse-mor-12-16 will be changed to cse-eve-12-16 ...inside same DB.
						 * 1 more thing is that when we add marks there will be 2 tables we will be dealing
						 * like cse-eve-12-16-info and cse-eve-12-16-marks
						 * so make a string global curTable string and set it to cur table we are working on
						 * actually make 2 strings 1 for marks and another for info and change them when we are
						 * over with old shift ie when new shift starts...
						 */
						//code-started   for above...
						tblName = tblName.replaceFirst("mor", "eve");
						JOptionPane.showMessageDialog(null, tblName);
						//code-end
						System.out.println("\nnew shift \n");
						shiftDistinguish=0;
					}
					continue;
					}
				else {gudone=true;}
				//if(jump && gudone)System.out.println("\n\n"+pageNum+"<<-------------New Shift------------->>\n\n");
				jump=false;
				gudone=false;
				List<String> myList = new ArrayList<String>(Arrays.asList(page.split("\n")));
				
				//for(int i=0;i<myList.size();i++){
				//	System.out.println("<"+i+">"+myList.get(i));
				//}
				//System.exit(0);
				
				for(int i=37;i<myList.size();){
					shiftDistinguish+=1;
					int numSubjects=0;
					//System.out.println("<"+i+">"+myList.get(i));
					String roll="",name="",SID="",credits="";
					ArrayList<String> total,theoryID,practicalID,theoryMarks,practicalMarks;
					total = new ArrayList<String>();
					theoryID = new ArrayList<String>();
					practicalID = new ArrayList<String>();
					theoryMarks = new ArrayList<String>();
					practicalMarks = new ArrayList<String>();
					
					
					for(int j=1;j<=38&&(i<myList.size());j++){//prepares 1 student result
						if(j==1){//ROLL
							roll = myList.get(i);
							//System.out.println(myList.get(i));
							if(roll.length()<5){}//over over over n out
							
						}
						else if(j==2){
							name=myList.get(i);//NAME
						}
						else if(j==3){
							SID=myList.get(i);//SID
							
						}
						else if(j==5){//subID + total_marks + credits
							while(i<myList.size()){
								String temp = myList.get(i+2);
								if(temp.matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*")&&temp.length()>3){//now the new student
									credits = (myList.get(i));j=39;
									//System.out.println(credits+"breaking"+i);
									break;
								}
								else{
									String pp = ((myList.get(i).split("\\(")[0]));
									temp = myList.get(i).split("\\(")[1];
									if(temp.charAt(0)=='1'){//practical has credit = 1
										practicalID.add(pp);
									}
									else theoryID.add(pp);
									//System.out.println(myList.get(i).split("\\(")[0]);
									i=i+2;
									if(temp.charAt(0)=='1'){
										practicalMarks.add(myList.get(i));
									}
									else theoryMarks.add(myList.get(i));
									++i;
									if(i+2>myList.size()){//last line..... :D
										credits=myList.get(i);
										//System.out.println("i="+i);
										numSubjects++;
										break;
									}
								numSubjects++;
								}
							}
						}
						
						i++;
						once=true;
					}
					
					
					if(numSubjects>5)if(!theoryMarks.get(0).contains("A") && 
							!theoryMarks.get(1).contains("A") && 
							!theoryMarks.get(2).contains("A") )
						{
						System.out.println(name);
						String query = "insert into `"+tblName+"` values(?, ?, ?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1,roll);
						pst.setString(2,name);
						//pst.setString(3,credits); //here we input credits as string so change it to INT below.. same with marks..
						credits = credits.trim();
						while(credits.contains("*"))credits.replaceAll("\\*", "");
						pst.setInt(3, Integer.parseInt(credits));
						pst.executeUpdate();
						
						for(int kk=0;kk<theoryID.size();kk++){
							query = "insert into `theory-"+tblName+"` values(?, ?, ?)";
							pst = connection.prepareStatement(query);
							pst.setString(1, roll);
							//pst.setString(2, theoryID.get(kk));
							//pst.setString(3, theoryMarks.get(kk));
							String id = theoryID.get(kk).trim().replaceAll("\\*", "");
							String marks = theoryMarks.get(kk).trim().replaceAll("\\*", "");
							if(marks.contains("C") || marks.contains("c"))marks = "0";
							pst.setInt(2, Integer.parseInt(id));
							pst.setInt(3, Integer.parseInt(marks));
							pst.executeUpdate();
						}
						for(int kk=0;kk<practicalID.size();kk++){
							query = "insert into `practical-"+tblName+"` values(?, ?, ?)";
							pst = connection.prepareStatement(query);
							pst.setString(1, roll);
							String id = practicalID.get(kk).trim().replaceAll("\\*", "");
							String marks = practicalMarks.get(kk).trim().replaceAll("\\*", "");
							if(marks.contains("C") || marks.contains("c"))marks = "0";
							pst.setInt(2, Integer.parseInt(id));
							pst.setInt(3, Integer.parseInt(marks));
							pst.executeUpdate();
						}
						
						}
						//System.out.println(numSubjects);
					//	for(int kk=0;kk<theoryID.size();kk++){
					//		System.out.println(theoryMarks.get(kk));
					//	}
					//System.exit(0);
					if(jump)break;	
				}		
			}//FOR KA END
		}catch(Exception e){
				JOptionPane.showMessageDialog(null, e);		
		}
		
	}


}
// ------------------------------------------------------- YO VIMAL --------------------------------------------------------------