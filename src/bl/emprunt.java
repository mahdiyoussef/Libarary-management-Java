package bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class emprunt {
	private Connection conx;
	private Statement stmt;
	private ResultSet rst;
	public emprunt(){
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
	public void add_emprunt(int NAP,int id_Livre) {
		try {
			int id=1;
			rst=stmt.executeQuery("select id_emprunt from emprunt");
			while(rst.next()) {
				if(rst.isLast()) id=rst.getInt("id_emprunt")+1;
			}
			stmt.execute("insert into emprunt values("+id+",DEFAULT,DEFAULT,"+NAP+","+id_Livre+")");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void delete_emprunt(int id) {
		try {
			stmt.execute("delete from emprunt where id_emprunt="+id);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public Object[][] searchfor_emprunt(int id) {
		try {
			rst=stmt.executeQuery("select count(*) from emprunt where nap="+id);
			int lignes=0;
			while(rst.next()) {
				if (rst.isLast()) lignes=rst.getInt(1);
			}
			rst=stmt.executeQuery("select * from emprunt where nap="+id);
			Object[][] data=new Object[lignes][5];
			int i=0;
			while(rst.next()) {
				if(id==rst.getInt(4)) {
					data[i][0]=rst.getObject(1);
					data[i][1]=rst.getObject(2);
					data[i][2]=rst.getObject(3);
					data[i][3]=rst.getObject(4);
					data[i][4]=rst.getObject(5);
				}
				i++;
			}
			return data;
			
		} catch (Exception e) {
			System.out.println(e);
			
		}
		return null;
	}
	/*public  Object[][] get_data(){
            int lignes=0;
            Object[][] data=new Object[lignes][5];
                try {
			rst=stmt.executeQuery("select count(*) from emprunt");
			
			while(rst.next()) {
				if (rst.isLast()) lignes=rst.getInt(1);
			}
			rst=stmt.executeQuery("select * from emprunt");
			String[] Onlydates;
			
			int i=0;
			while(rst.next()) {
				data[i][0]=rst.getObject(1);
				Onlydates=rst.getString(2).split(" ");
				data[i][1]=Onlydates[0];
				Onlydates=rst.getString(3).split(" ");
				data[i][2]=Onlydates[0];
				data[i][3]=rst.getObject(4);
				data[i][4]=rst.getObject(5);
				i++;
			}
                        
			
			
		} catch (SQLException e) {
			System.out.println(e);
			//return null;
		}
        
            return data;
            
		
		
	}*/
        public  Object[][] get_data(){
		try {
			rst=stmt.executeQuery("select count(*) from emprunt");
			int lignes=0;
			while(rst.next()) {
				if (rst.isLast()) lignes=rst.getInt(1);
			}
			rst=stmt.executeQuery("select * from emprunt");
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
