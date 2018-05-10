package com.haulmont.testtask.database.controllers;

import com.haulmont.testtask.database.dao.RecipesDAO;
import com.haulmont.testtask.database.entities.RecipesEntity;

import java.util.List;

public class RecipesController {
    private static RecipesDAO recipesDAO;

    public RecipesController() {
        recipesDAO = new RecipesDAO();
    }

    public void persist(RecipesEntity entity) {
        recipesDAO.openCurrentSessionwithTransaction();
        recipesDAO.persist(entity);
        recipesDAO.closeCurrentSessionwithTransaction();
    }

    public void update(RecipesEntity entity) {
        recipesDAO.openCurrentSessionwithTransaction();
        recipesDAO.update(entity);
        recipesDAO.closeCurrentSessionwithTransaction();
    }

    public RecipesEntity findById(String id) {
        recipesDAO.openCurrentSession();
        RecipesEntity recipesEntity = recipesDAO.findById(id);
        recipesDAO.closeCurrentSession();
        return recipesEntity;
    }

    public void delete(RecipesEntity recipesEntity) {
        recipesDAO.openCurrentSessionwithTransaction();
        recipesDAO.delete(recipesEntity);
        recipesDAO.closeCurrentSessionwithTransaction();
    }

    public List<RecipesEntity> findAll() {
        recipesDAO.openCurrentSession();
        List<RecipesEntity> recipesEntities = recipesDAO.findAll();
        recipesDAO.closeCurrentSession();
        return recipesEntities;
    }

    public void deleteAll() {
        recipesDAO.openCurrentSessionwithTransaction();
        recipesDAO.deleteAll();
        recipesDAO.closeCurrentSessionwithTransaction();
    }

    public RecipesDAO recipesDAO() {
        return recipesDAO;
    }

    public List<RecipesEntity> getItemsByFilter(String patientFilterValue, String priorityFilterValue, String descriptionFilterValue) {
        recipesDAO.openCurrentSession();
        List<RecipesEntity> recipesEntities = recipesDAO.getItemsByFilter(patientFilterValue, priorityFilterValue, descriptionFilterValue);
        recipesDAO.closeCurrentSession();
        return recipesEntities;
    }
}
