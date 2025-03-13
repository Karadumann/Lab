package com.lab.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

/**
 * DatabaseManager class handles all database operations using Hibernate.
 */
public class DatabaseManager {
    private static final SessionFactory sessionFactory;
    
    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to initialize Hibernate SessionFactory: " + e);
        }
    }

    /**
     * Save or update an entity in the database
     * @param entity the entity to save or update
     * @return the saved or updated entity
     */
    public static <T> T saveOrUpdate(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(entity);
                transaction.commit();
                return entity;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    /**
     * Delete an entity from the database
     * @param entity the entity to delete
     */
    public static <T> void delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.remove(entity);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    /**
     * Find an entity by its ID
     * @param entityClass the class of the entity
     * @param id the ID of the entity
     * @return the found entity or null if not found
     */
    public static <T> T findById(Class<T> entityClass, Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(entityClass, id);
        }
    }

    /**
     * Find all entities of a given class
     * @param entityClass the class of the entities
     * @return list of all entities
     */
    public static <T> List<T> findAll(Class<T> entityClass) {
        try (Session session = sessionFactory.openSession()) {
            Query<T> query = session.createQuery("from " + entityClass.getName(), entityClass);
            return query.getResultList();
        }
    }

    /**
     * Close the SessionFactory
     */
    public static void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
} 