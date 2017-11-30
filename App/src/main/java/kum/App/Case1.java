package kum.App;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.jdbc.Connection;

public class Case1 implements Case{

	private final  Scanner sc;
	private  final String CONNECTION_URL;
	private  final String USER;
	private  final String PASSWORD;
	
	
	

	public Case1(Scanner sc, String cONNECTION_URL, String uSER, String pASSWORD) {
		this.sc = sc;
		CONNECTION_URL = cONNECTION_URL;
		USER = uSER;
		PASSWORD = pASSWORD;
	}




	@Override
	public boolean run() {
		try(Connection connection = (Connection) DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD)){
    		Statement statement = connection.createStatement();
    		System.out.println("Enter database name: ");
    		statement.executeUpdate("CREATE DATABASE "+sc.next());
//    		statement.execute(CREATE_TABLE_USER);
//			statement.close();
    		
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}

}
