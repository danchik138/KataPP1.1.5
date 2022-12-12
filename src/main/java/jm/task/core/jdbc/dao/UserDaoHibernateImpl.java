package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.*;
import static jm.task.core.jdbc.dao.UserDaoJDBCImpl.*;

public class UserDaoHibernateImpl implements UserDao {

    private Session session;
    private Transaction transaction;

    private SessionFactory sessionFactory = getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    private void getCurrentSessionAndBeginTransaction() {
        session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();
    }


    @Override
    public void createUsersTable() {
        getCurrentSessionAndBeginTransaction();
        session.createNativeMutationQuery(SQL_CREATE_TABLE).executeUpdate();
        transaction.commit();
    }

    @Override
    public void dropUsersTable() {
        getCurrentSessionAndBeginTransaction();
        session.createNativeMutationQuery(SQL_DROP_TABLE).executeUpdate();
        transaction.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        getCurrentSessionAndBeginTransaction();
        User temp = new User(name, lastName, age);
        session.persist(temp);
        transaction.commit();
    }

    @Override
    public void removeUserById(long id) {
        getCurrentSessionAndBeginTransaction();
        User temp = session.get(User.class, id);
        if (temp != null) {
            session.remove(temp);
        }
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result;
        getCurrentSessionAndBeginTransaction();
        result = session.createQuery("from User", User.class).list();
        transaction.commit();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
    }
}
