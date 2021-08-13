package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	//открытие сессии Hibernate
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Properties properties = new Properties();
				properties.put(Environment.DRIVER, DRIVER);
				properties.put(Environment.USER, USERNAME);
				properties.put(Environment.PASS, PASSWORD);
				properties.put(Environment.URL, URL);
				properties.put(Environment.SHOW_SQL, "true");
				properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				Configuration configuration = new Configuration()
						.setProperties(properties)
						.addAnnotatedClass(User.class);
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}
