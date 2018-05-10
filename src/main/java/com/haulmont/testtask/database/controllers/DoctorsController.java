package com.haulmont.testtask.database.controllers;

import com.haulmont.testtask.database.dao.DoctorsDAO;
import com.haulmont.testtask.database.entities.DoctorsEntity;
import com.haulmont.testtask.database.entities.DoctorsStatistic;

import java.util.List;

public class DoctorsController {
    private static DoctorsDAO doctorsDAO;

    public DoctorsController() {
        doctorsDAO = new DoctorsDAO();
    }

    public void persist(DoctorsEntity entity) {
        doctorsDAO.openCurrentSessionwithTransaction();
        doctorsDAO.persist(entity);
        doctorsDAO.closeCurrentSessionwithTransaction();
    }

    public void update(DoctorsEntity entity) {
        doctorsDAO.openCurrentSessionwithTransaction();
        doctorsDAO.update(entity);
        doctorsDAO.closeCurrentSessionwithTransaction();
    }

    public DoctorsEntity findById(String id) {
        doctorsDAO.openCurrentSession();
        DoctorsEntity doctorsEntity = doctorsDAO.findById(id);
        doctorsDAO.closeCurrentSession();
        return doctorsEntity;
    }

    public void delete(DoctorsEntity doctorsEntity) {
        doctorsDAO.openCurrentSessionwithTransaction();
        doctorsDAO.delete(doctorsEntity);
        doctorsDAO.closeCurrentSessionwithTransaction();
    }

    public List<DoctorsEntity> findAll() {
        doctorsDAO.openCurrentSession();
        List<DoctorsEntity> doctorsEntities = doctorsDAO.findAll();
        doctorsDAO.closeCurrentSession();
        return doctorsEntities;
    }

    public void deleteAll() {
        doctorsDAO.openCurrentSessionwithTransaction();
        doctorsDAO.deleteAll();
        doctorsDAO.closeCurrentSessionwithTransaction();
    }

    public DoctorsDAO doctorsDAO() {
        return doctorsDAO;
    }

    public List<DoctorsStatistic> getStatistic() {
        doctorsDAO.openCurrentSession();
        List<DoctorsStatistic> doctorsStatistics = doctorsDAO.getStatistic();
        doctorsDAO.closeCurrentSession();
        return doctorsStatistics;
    }

}
