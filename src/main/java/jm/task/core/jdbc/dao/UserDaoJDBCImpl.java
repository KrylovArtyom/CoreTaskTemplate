package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                            + "id BIGINT PRIMARY KEY AUTO_INCREMENT, "
                            + "name VARCHAR(25) NOT NULL, "
                            + "lastName VARCHAR(25) NOT NULL, "
                            + "age SMALLINT NOT NULL"
                            + ");";
        Connection connection = Util.getMySQLConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate(createTableSQL);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getMySQLConnection();
                Statement statement = connection.createStatement()) {

            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserSQL = "INSERT INTO users(name, lastName, age) values(?, ?, ?)";
        Connection connection = Util.getMySQLConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(saveUserSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String deleteUserByIdSQL = "DELETE FROM users WHERE id";

        try (Connection connection = Util.getMySQLConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteUserByIdSQL)) {

            preparedStatement.execute(deleteUserByIdSQL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> arrUser = new ArrayList<>();
        String getAllUsersSQL = "SELECT * FROM users";

        try (Connection connection = Util.getMySQLConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getAllUsersSQL);
                ResultSet resultSet = preparedStatement.executeQuery(getAllUsersSQL)) {

            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                                     resultSet.getString("lastName"),
                                     resultSet.getByte("age"));
                arrUser.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrUser;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getMySQLConnection();
                Statement statement = connection.createStatement()) {

            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
