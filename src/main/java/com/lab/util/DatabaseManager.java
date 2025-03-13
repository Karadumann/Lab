package com.lab.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Utility class for managing database operations using Hibernate.
 */
public class DatabaseManager {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to initialize Hibernate SessionFactory: " + e.getMessage());
        }
    }

    /**
     * Saves or updates an entity in the database.
     *
     * @param entity The entity to save or update
     * @return The saved or updated entity
     */
    public static <T> T saveOrUpdate(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving/updating entity: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    /**
     * Deletes an entity from the database.
     *
     * @param entity The entity to delete
     */
    public static <T> void delete(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting entity: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    /**
     * Finds an entity by its ID.
     *
     * @param entityClass The class of the entity to find
     * @param id The ID of the entity
     * @return The found entity or null if not found
     */
    public static <T> T findById(Class<T> entityClass, Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(entityClass, id);
        } finally {
            session.close();
        }
    }

    /**
     * Retrieves all entities of a given class.
     *
     * @param entityClass The class of entities to retrieve
     * @return A list of all entities of the specified class
     */
    public static <T> List<T> findAll(Class<T> entityClass) {
        Session session = sessionFactory.openSession();
        try {
            Query<T> query = session.createQuery("from " + entityClass.getName(), entityClass);
            return query.getResultList();
        } finally {
            session.close();
        }
    }

    /**
     * Closes the Hibernate SessionFactory.
     */
    public static void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
} 