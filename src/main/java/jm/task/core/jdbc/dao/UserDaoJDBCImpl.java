package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import static jm.task.core.jdbc.util.Util.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = getConnection()) {
            String req =
                    "CREATE TABLE IF NOT EXISTS User (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT, " +
                    "  `name` VARCHAR(256) NULL, " +
                    "  `lastName` VARCHAR(256) NULL, " +
                    "  `age` INT(3) NULL, " +
                    "  PRIMARY KEY (`id`));";
            Statement statement = connection.createStatement();
            statement.execute(req);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = getConnection()) {
            String req = "DROP TABLE IF EXISTS User";
            Statement statement = connection.createStatement();
            statement.execute(req);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = getConnection()) {
            String req = "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = getConnection()) {
            String req = "DELETE FROM User WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String req = "SELECT * FROM User";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(req);
            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = getConnection()) {
            String req = "TRUNCATE TABLE User";
            Statement statement = connection.createStatement();
            statement.execute(req);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
