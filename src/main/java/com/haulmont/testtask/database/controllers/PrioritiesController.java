package com.haulmont.testtask.database.controllers;

import com.haulmont.testtask.database.dao.PatientsDAO;
import com.haulmont.testtask.database.dao.PrioritiesDAO;
import com.haulmont.testtask.database.entities.PatientsEntity;
import com.haulmont.testtask.database.entities.PrioritiesEntity;

import java.util.List;

public class PrioritiesController {
    private static PrioritiesDAO prioritiesDAO;

    public PrioritiesController() {
        prioritiesDAO = new PrioritiesDAO();
    }

    public void persist(PrioritiesEntity entity) {
        prioritiesDAO.openCurrentSessionwithTransaction();
        prioritiesDAO.persist(entity);
        prioritiesDAO.closeCurrentSessionwithTransaction();
    }

    public void update(PrioritiesEntity entity) {
        prioritiesDAO.openCurrentSessionwithTransaction();
        prioritiesDAO.update(entity);
        prioritiesDAO.closeCurrentSessionwithTransaction();
    }

    public PrioritiesEntity findById(String id) {
        prioritiesDAO.openCurrentSession();
        PrioritiesEntity prioritiesEntity = prioritiesDAO.findById(id);
        prioritiesDAO.closeCurrentSession();
        return prioritiesEntity;
    }

    public void delete(String id) {
        prioritiesDAO.openCurrentSessionwithTransaction();
        PrioritiesEntity prioritiesEntity = prioritiesDAO.findById(id);
        prioritiesDAO.delete(prioritiesEntity);
        prioritiesDAO.closeCurrentSessionwithTransaction();
    }

    public List<PrioritiesEntity> findAll() {
        prioritiesDAO.openCurrentSession();
        List<PrioritiesEntity> patientsEntities = prioritiesDAO.findAll();
        prioritiesDAO.closeCurrentSession();
        return patientsEntities;
    }

    public void deleteAll() {
        prioritiesDAO.openCurrentSessionwithTransaction();
        prioritiesDAO.deleteAll();
        prioritiesDAO.closeCurrentSessionwithTransaction();
    }

    public PrioritiesDAO prioritiesDAO() {
        return prioritiesDAO;
    }

}
