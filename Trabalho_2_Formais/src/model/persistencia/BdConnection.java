package model.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class BdConnection {
	private static Connection CON;
	private static final String file = ":resource:DataBase.db"; //":resource:DataBase.db"; //"lib\\DataBase.db";
	
	public static Connection getConnection() {
		if(CON == null){
			try {
				Class.forName("org.sqlite.JDBC");
				CON = DriverManager.getConnection( "jdbc:sqlite:" +file );
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado com o banco de dados.");
			}
		}
		return CON;
	}
}
