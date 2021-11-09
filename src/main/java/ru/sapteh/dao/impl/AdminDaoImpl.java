package ru.sapteh.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Admin;

import java.util.List;

public class AdminDaoImpl implements Dao<Admin, Integer> {

    private final SessionFactory factory;

    public AdminDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Admin findById(Integer id) {
        try (Session session = factory.openSession()) {
            return session.get(Admin.class, id);
        }
    }

    @Override
    public List<Admin> findAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Admin", Admin.class).list();
        }
    }

    @Override
    public void save(Admin admin) {
        if (admin == null)
        try (Session session = factory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.save(admin);
            transaction.commit();
        }
    }

    @Override
    public void update(Admin admin) {
        if (admin == null)
        try (Session session = factory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.update(admin);
            transaction.commit();
        }
    }

    @Override
    public void delete(Admin admin) {
        if (admin == null)
        try (Session session = factory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.delete(admin);
            transaction.commit();
        }
    }
}
