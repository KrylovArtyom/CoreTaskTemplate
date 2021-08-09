package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
	// реализуйте настройку соеденения с БД JDBC
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
			System.out.println("драйвер подключен");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Соелинение установлено");
		} catch (SQLException e) {
			System.out.println("Проверь с отображением БД");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Проблема с JDBC");
			e.printStackTrace();
		}
		return connection;
	}

	//открытие сессии Hibernate
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			registry = new StandardServiceRegistryBuilder()
					.configure().build();
			MetadataSources sources = new MetadataSources(registry);
			Metadata metadata = sources.getMetadataBuilder().build();
			sessionFactory = metadata.getSessionFactoryBuilder().build();
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
