package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.*;
import static jm.task.core.jdbc.dao.UserDaoJDBCImpl.*;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory = getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction;
            try {
                transaction = session.beginTransaction();
                session.createNativeMutationQuery(SQL_CREATE_TABLE).executeUpdate();
                transaction.commit();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction;
            try {
                transaction = session.beginTransaction();
                session.createNativeMutationQuery(SQL_DROP_TABLE).executeUpdate();
                transaction.commit();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                User temp = new User(name, lastName, age);
                session.persist(temp);
                transaction.commit();
            }
            catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                User temp = session.get(User.class, id);
                if (temp != null) {
                    session.remove(temp);
                }
                transaction.commit();
            }
            catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = null;
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                result = session.createQuery("from User", User.class).list();
                transaction.commit();
            }
            catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createMutationQuery("delete from User").executeUpdate();
                transaction.commit();
            }
            catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }
}
