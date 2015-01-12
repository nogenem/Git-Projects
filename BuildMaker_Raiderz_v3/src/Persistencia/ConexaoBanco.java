package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoBanco
{
	private static Connection conexao;
	private String file;
	
	public ConexaoBanco( String file )
	{
		this.file = file;
		this.getConexao();
	}
	
	public ConexaoBanco()
	{
		this.file = "src\\Persistencia\\SQLITE\\DataBase.db";
		this.getConexao();
	}
	
	public Connection getConexao()
	{
		if( ConexaoBanco.conexao != null )
		{
			return ConexaoBanco.conexao;
		}
		this.newConexao( this.file );
		return ConexaoBanco.conexao;
	}
	
	private void newConexao( String file )
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			ConexaoBanco.conexao = DriverManager.getConnection( "jdbc:sqlite:" +file );
		}
		catch( Exception e )
		{
			System.out.println( e );
		}
	}
}
