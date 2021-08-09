package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
	// реализуйте настройку соеденения с БД
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String HOSTNAME = "localhost";
	private static final String DBNAME = "java_project";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "sdf23mSF_13421";
	private static final String URL = "jdbc:mysql://" + HOSTNAME + ":3306/" + DBNAME + "?autoReconnect=true&useSSL=false";

	public static Connection getMySQLConnection() {
		Connection connection = null;
		try{
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			connection.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

}
