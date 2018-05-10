package com.haulmont.testtask.database.controllers;

import com.haulmont.testtask.database.dao.PatientsDAO;
import com.haulmont.testtask.database.entities.PatientsEntity;

import java.util.List;

public class PatientsController {
    private static PatientsDAO patientsDAO;

    public PatientsController() {
        patientsDAO = new PatientsDAO();
    }

    public void persist(PatientsEntity entity) {
        patientsDAO.openCurrentSessionwithTransaction();
        patientsDAO.persist(entity);
        patientsDAO.closeCurrentSessionwithTransaction();
    }

    public void update(PatientsEntity entity) {
        patientsDAO.openCurrentSessionwithTransaction();
        patientsDAO.update(entity);
        patientsDAO.closeCurrentSessionwithTransaction();
    }

    public PatientsEntity findById(String id) {
        patientsDAO.openCurrentSession();
        PatientsEntity patientsEntity = patientsDAO.findById(id);
        patientsDAO.closeCurrentSession();
        return patientsEntity;
    }

    public void delete(PatientsEntity entity) {
        patientsDAO.openCurrentSessionwithTransaction();
        patientsDAO.delete(entity);
        patientsDAO.closeCurrentSessionwithTransaction();
    }

    public List<PatientsEntity> findAll() {
        patientsDAO.openCurrentSession();
        List<PatientsEntity> patientsEntities = patientsDAO.findAll();
        patientsDAO.closeCurrentSession();
        return patientsEntities;
    }

    public void deleteAll() {
        patientsDAO.openCurrentSessionwithTransaction();
        patientsDAO.deleteAll();
        patientsDAO.closeCurrentSessionwithTransaction();
    }

    public PatientsDAO patientDAO() {
        return patientsDAO;
    }

}
