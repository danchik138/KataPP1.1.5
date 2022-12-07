package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_DRIVER = "jdbc";
    private static final String DB_TYPE = "mysql";
    private static final String DB_ADDR = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "test1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWD = "¯\\_(ツ)_//¯";

    public static Connection getConnection() throws SQLException {
        String url = String.format(
                "%s:%s://%s:%s/%s",
                DB_DRIVER,
                DB_TYPE,
                DB_ADDR,
                DB_PORT,
                DB_NAME);
        return DriverManager.getConnection(url, DB_USER, DB_PASSWD);
    }
}
