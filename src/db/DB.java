package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import exception.DBException;

public class DB {
	
	private static Connection conn = null;
	
	// do connection to database
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties properties = loadProperties();
				String urlDB = properties.getProperty("dburl");
				conn = DriverManager.getConnection(urlDB, properties);
			} catch(SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
		return conn;
	}
	
	// close connection
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();	
			} catch(SQLException e) {
				throw new DBException("Error to close connection");
			}
			
		}
	}
	
	// load the properties file
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")){
			Properties properties = new Properties();
			properties.load(fs);
			return properties;
		}
		catch(IOException e) {
			throw new DBException("Arquivo não encontrado");
		}
	}
	
	// close statement
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	// close result set
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	// close PrepareStatement
	public static void closePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

}
