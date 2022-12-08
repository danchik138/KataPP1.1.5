package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import static jm.task.core.jdbc.util.Util.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS User (" +
            "  `id` BIGINT NOT NULL AUTO_INCREMENT, " +
            "  `name` VARCHAR(256) NULL, " +
            "  `lastName` VARCHAR(256) NULL, " +
            "  `age` INT(3) NULL, " +
            "  PRIMARY KEY (`id`));";
    private static final String SQL_INSERT_USER = "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?);";
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS User";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM User WHERE id = ?";
    private static final String SQL_GET_ALL = "SELECT * FROM User";
    private static final String SQL_TRUCATE = "TRUNCATE TABLE User";



    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQL_CREATE_TABLE);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQL_DROP_TABLE);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_USER);
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
        try {
            connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BY_ID);
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_GET_ALL);
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
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQL_TRUCATE);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
