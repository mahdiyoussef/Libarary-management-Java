package bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class livre {
	private Connection conx;
	private Statement stmt;
	private ResultSet rst;
	public livre(){
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
	public void add_livre(String nom,String auteur,int dat) {
		
		try {
			int id=1;
			rst=stmt.executeQuery("select id_livre from livre");
			while(rst.next()) {
				if(rst.isLast()) id=rst.getInt("id_livre")+1;
			}
			stmt.execute("insert into livre values("+id+",'"+nom+"','"+auteur+"',"+dat+")");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void delete_livre(int id) {
		try {
			stmt.execute("delete from livre where id_livre="+id);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public Object[][] searchfor_livre(String name) {
		try {
			int num=0;
			ResultSet nimiro=stmt.executeQuery("select count(*) as nim from livre where upper(nom_livre)='"+name.toUpperCase()+"'");
			
			while(nimiro.next()) {
				num=nimiro.getInt("nim");
			}			
			
			rst=stmt.executeQuery("select * from livre where upper(nom_livre) LIKE '%"+name.toUpperCase()+"%'");
			Object[] sub=new Object[4];
			Object[][] results=new Object[num][sub.length];
			
			int i=0;
			while(rst.next()) {
				sub[0]=rst.getString(1);
				sub[1]=rst.getString(2);
				sub[2]=rst.getString(3);
				sub[3]=rst.getString(4);
				results[i]=sub.clone();
				i++;
			}
			return results;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public  Object[][] get_data(){
		try {
			rst=stmt.executeQuery("select count(*) from livre");
			int lignes=0;
			while(rst.next()) {
				if (rst.isLast()) lignes=rst.getInt(1);
			}
			rst=stmt.executeQuery("select * from livre");
			Object[][] data=new Object[lignes][4];
			int i=0;
			while(rst.next()) {
				data[i][0]=rst.getObject(1);
				data[i][1]=rst.getObject(2);
				data[i][2]=rst.getObject(3);
				data[i][3]=rst.getObject(4);
				i++;
			}
			
			return data;
			
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
		
		
	}
}
