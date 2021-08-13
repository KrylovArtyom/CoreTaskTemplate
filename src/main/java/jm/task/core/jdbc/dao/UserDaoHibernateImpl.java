package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
	public UserDaoHibernateImpl() {
	}

	public void createUsersTable() {
		String sql = "CREATE TABLE IF NOT EXISTS users ("
				+ "id BIGINT PRIMARY KEY AUTO_INCREMENT, "
				+ "name VARCHAR(25) NOT NULL, "
				+ "lastName VARCHAR(25) NOT NULL, "
				+ "age SMALLINT NOT NULL"
				+ ");";
		Transaction transaction = null;
		try (Session session = Util.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.createSQLQuery(sql).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public void dropUsersTable() {
		Transaction transaction = null;
		try (Session session = Util.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
			transaction.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public void saveUser(String name, String lastName, byte age) {
		Transaction transaction = null;
		try (Session session = Util.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(new User(name, lastName, age));
			transaction.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public void removeUserById(long id) {
		Transaction transaction = null;
		try (Session session = Util.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			User user = session.get(User.class, id);
			session.delete(user);
			transaction.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public List<User> getAllUsers() {
		List<User> allUser = new ArrayList<>();
		Transaction transaction = null;
		try (Session session = Util.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			allUser = session.createQuery("from User", User.class).list();
			transaction.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return allUser;
	}

	public void cleanUsersTable() {
		Transaction transaction = null;
		try (Session session = Util.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.createQuery("delete User").executeUpdate();
			transaction.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
}
