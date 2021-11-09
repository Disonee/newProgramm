package ru.sapteh.service;

import org.hibernate.SessionFactory;
import ru.sapteh.dao.Dao;
import ru.sapteh.dao.impl.AdminDaoImpl;
import ru.sapteh.model.Admin;

import java.util.List;

public class AdminService {
    private final Dao<Admin, Integer> adminDao;

    public AdminService(SessionFactory factory){
        this.adminDao = new AdminDaoImpl(factory);
    }

    public Admin findById(int id){
        return adminDao.findById((id));
    }

    public List<Admin> findAll(){
        return adminDao.findAll();
    }

    public void save (Admin admin){
        adminDao.save(admin);
    }

    public void update (Admin admin){
        adminDao.update(admin);
    }

    public void delete (Admin admin){
        adminDao.delete(admin);
    }
}
