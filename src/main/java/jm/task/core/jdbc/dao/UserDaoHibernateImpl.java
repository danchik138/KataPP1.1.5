package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.*;
import static jm.task.core.jdbc.dao.UserDaoJDBCImpl.*;

public class UserDaoHibernateImpl implements UserDao {

    private Session session;
    private Transaction transaction;

    public UserDaoHibernateImpl() {

    }

    private void getCurrentSessionAndBeginTransaction() {
        session = getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
    }


    @Override
    public void createUsersTable() {
        try {
            getCurrentSessionAndBeginTransaction();
            session.createNativeMutationQuery(SQL_CREATE_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            getCurrentSessionAndBeginTransaction();
            session.createNativeMutationQuery(SQL_DROP_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            getCurrentSessionAndBeginTransaction();
            User temp = new User(name, lastName, age);
            session.persist(temp);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            getCurrentSessionAndBeginTransaction();
            User temp = session.get(User.class, id);
            if (temp != null) {
                session.remove(temp);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = null;
        try {
            getCurrentSessionAndBeginTransaction();
            result = session.createQuery("from User", User.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
    }
}
