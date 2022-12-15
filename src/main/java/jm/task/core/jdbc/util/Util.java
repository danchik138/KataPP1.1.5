package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWD = "password";

    private static final String HB_DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static final String HB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HB_HBM2DDL_AUTO = "update";
    private static final String HB_SHOW_SQL = "true";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Properties prop = createHibernateProperties();
            Configuration conf = new Configuration().setProperties(prop);
            conf.addAnnotatedClass(User.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(conf.getProperties());
            sessionFactory = conf.buildSessionFactory(builder.build());
        }

        return sessionFactory;
    }

    private static Properties createHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty(Environment.DIALECT, HB_DIALECT);
        properties.setProperty(Environment.DRIVER, HB_DRIVER);
        properties.setProperty(Environment.HBM2DDL_AUTO, HB_HBM2DDL_AUTO);
        properties.setProperty(Environment.SHOW_SQL, HB_SHOW_SQL);
        properties.setProperty(Environment.URL, DB_URL);
        properties.setProperty(Environment.USER, DB_USER);
        properties.setProperty(Environment.PASS, DB_PASSWD);
        properties.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        return properties;
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.getCurrentSession().close();
            sessionFactory.close();
        }
    }

}
