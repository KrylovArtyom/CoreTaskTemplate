package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
	public UserDaoHibernateImpl() {
	}

	@Override
	public void createUsersTable() {
		try (Session session = Util.getSessionFactory().getCurrentSession()) {
			session.beginTransaction();
			String sql = "CREATE TABLE IF NOT EXISTS users ("
					+ "id BIGINT PRIMARY KEY AUTO_INCREMENT, "
					+ "name VARCHAR(25) NOT NULL, "
					+ "lastName VARCHAR(25) NOT NULL, "
					+ "age SMALLINT NOT NULL"
					+ ");";
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dropUsersTable() {
		try (Session session = Util.getSessionFactory().getCurrentSession()) {
			session.beginTransaction();
			session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveUser(String name, String lastName, byte age) {
		try (Session session = Util.getSessionFactory().getCurrentSession()) {
			session.beginTransaction();
			session.persist(new User(name, lastName, age));
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeUserById(long id) {
		try (Session session = Util.getSessionFactory().getCurrentSession()) {
			session.beginTransaction();
			User user = session.get(User.class, id);
			session.delete(user);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> getAllUsers() {
		List<User> allUser = new ArrayList<>();
		try (Session session = Util.getSessionFactory().openSession()) {
			session.beginTransaction();
			Query<User> query = session.createQuery("from User", User.class);
			allUser = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return allUser;
	}

	@Override
	public void cleanUsersTable() {
		try (Session session = Util.getSessionFactory().getCurrentSession()) {
			session.beginTransaction();
			session.createQuery("delete User").executeUpdate();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}
}
