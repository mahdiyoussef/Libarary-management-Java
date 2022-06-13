package bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class retard {
	private Connection conx;
	private Statement stmt;
	private ResultSet rst;
	public retard(){
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
	private void update_retard() {
		try {
			List<Integer> ids=delayed_ids();
			int id_emp;
			for(int i=0;i<ids.size();i++) {
				id_emp=ids.get(i);
stmt.execute("insert into retard VALUES((SELECT nom from adherant,emprunt where adherant.N_Apoge=emprunt.NAP and emprunt.id_emprunt="+id_emp+"),(SELECT prenom from adherant,emprunt where adherant.N_Apoge=emprunt.NAP and emprunt.id_emprunt="+id_emp+"),(select abs(trunc(sysdate-date_retour)) from emprunt where id_emprunt="+id_emp+" ),(SELECT nom_livre from livre ,emprunt where id_emprunt="+id_emp+" and emprunt.id_livre=livre.id_livre ),"+id_emp+")");
			}
			stmt.execute("UPDATE retard set retardement=trunc(sysdate-(select date_retour from emprunt where retard.id_emprunt=emprunt.id_emprunt))");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private List<Integer> delayed_ids() {
		try {
			rst=stmt.executeQuery("SELECT id_emprunt FROM emprunt where date_retour<sysdate and id_emprunt not in (select id_emprunt from retard)");
			List<Integer> ids=new ArrayList<Integer>();
			while(rst.next()) ids.add(rst.getInt(1));
			return ids;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public  Object[][] get_data(){
		try {
			update_retard();
			rst=stmt.executeQuery("select count(*) from retard");
			int lignes=0;
			while(rst.next()) {
				if (rst.isLast()) lignes=rst.getInt(1);
			}
			rst=stmt.executeQuery("select * from retard");
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
        public  Object[][] search_data(int id){
		try {
			update_retard();
			rst=stmt.executeQuery("select count(*) from retard where id_emprunt="+id);
			int lignes=0;
			while(rst.next()) {
				if (rst.isLast()) lignes=rst.getInt(1);
			}
			rst=stmt.executeQuery("select count(*) from retard where id_emprunt="+id); 
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
