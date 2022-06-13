package bl;

import java.sql.*;
public class adherant {
	private Connection conx;
	private Statement stmt;
	private ResultSet rst;
	public adherant(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conx=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","pass");
			stmt=conx.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void add_adherant(int numAP,String nom,String prenom,String filiere,String nivo) {
		try {
			stmt.executeUpdate("insert into adherant values ("+numAP+",'"+nom+"','"+prenom+"','"+filiere.toUpperCase()+"','"+nivo+"')");
                        
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void delete_adherant(int id) {
		try {
			stmt.execute("delete from adherant where N_Apoge="+id);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public Object[][] searchfor_adherant(int id) {
		try {
			rst=stmt.executeQuery("select * from adherant where n_apoge="+id);
			Object[][] data=new Object[1][5];
			while(rst.next()) {
				if(rst.getInt(1)==id) {
				data[0][0]=rst.getObject(1);
				data[0][1]=rst.getObject(2);
				data[0][2]=rst.getObject(3);
				data[0][3]=rst.getObject(4);
				data[0][4]=rst.getObject(5);
				}
				
			}
			return data;
		} catch (Exception e) {
			System.out.println(e);
			
		}return null;
	}
	public  Object[][] get_data(){
		try {
			rst=stmt.executeQuery("select count(*) from adherant");
			int lignes=0;
			while(rst.next()) {
				if (rst.isLast()) lignes=rst.getInt(1);
			}
			rst=stmt.executeQuery("select * from adherant");
			Object[][] data=new Object[lignes][5];
			int i=0;
			while(rst.next()) {
				data[i][0]=rst.getObject(1);
				data[i][1]=rst.getObject(2);
				data[i][2]=rst.getObject(3);
				data[i][3]=rst.getObject(4);
				data[i][4]=rst.getObject(5);
				i++;
			}
			return data;
			
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
		
		
	}
	
	
}
