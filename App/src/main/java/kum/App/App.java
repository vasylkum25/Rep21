package kum.App;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.mysql.jdbc.Connection;

public class App {
	
	public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/?useSSL=false";
	public static final String USER = "root";
	public static final String PASSWORD = "5100117";
	public static final String CREATE_TABLE_USER= "CREATE TABLE user("
			+ "id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR (255), age INT)";
    public static void main( String[] args ){
//    	try(Connection connection = (Connection) DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD)){
//    		Statement statement = connection.createStatement();
////    		Scanner sc = new Scanner(System.in);
////    		System.out.println("Enter database name: ");
////    		statement.executeUpdate("CREATE DATABASE "+sc.next());
////    		statement.execute(CREATE_TABLE_USER);
////			statement.close();
//    		
//		}
//		catch(SQLException e){
//			e.printStackTrace();
//		}
    	
    	
    	
		Map<String, Case> menu = new HashMap<>();
		Scanner sc = new Scanner(System.in);
		menu.put("1", new Case1(sc, CONNECTION_URL, USER, PASSWORD));
		menu.put("2", new Case2(sc, USER, PASSWORD));
//		menu.put("3", new Case3(sc, map, auditorys));
//		menu.put("4", new Case4(sc, auditorys));
//		menu.put("5", new Case5(sc, map));
//		menu.put("6", new Case6( map));
		do{
			System.out.println("1. Create DB");
			System.out.println("2. Create table in DB");
			System.out.println("3. Reserv auditory");
			System.out.println("4. Delet  person");
			System.out.println("5. Delet  animal");
			System.out.println("6. Print Zoo Club");
		}while(menu.get(sc.next()).run());
	}

    }

