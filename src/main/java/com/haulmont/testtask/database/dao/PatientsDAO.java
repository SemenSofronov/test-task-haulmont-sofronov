package com.haulmont.testtask.database.dao;

import com.haulmont.testtask.database.entities.PatientsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PatientsDAO implements DAOInterface<PatientsEntity, String> {

    private Session currentSession;

    private Transaction currentTransaction;

    public PatientsDAO() {
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void persist(PatientsEntity entity) {
        getCurrentSession().persist(entity);
    }

    public void update(PatientsEntity entity) {
        getCurrentSession().merge(entity);
    }

    public PatientsEntity findById(String id) {
        PatientsEntity patientsEntity = (PatientsEntity) getCurrentSession().get(PatientsEntity.class, id);
        return patientsEntity;
    }

    public void delete(PatientsEntity entity) {
        getCurrentSession().delete(entity);
    }

    public List<PatientsEntity> findAll() {
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<PatientsEntity> q = criteriaBuilder.createQuery(PatientsEntity.class);
        Root<PatientsEntity> c = q.from(PatientsEntity.class);
        q.select(c);
        TypedQuery<PatientsEntity> queryForData = getCurrentSession().createQuery(q);
        return queryForData.getResultList();
//        return getCurrentSession().createCriteria(PatientsEntity.class).list();
    }

    public void deleteAll() {
        List<PatientsEntity> entityList = findAll();
        for (PatientsEntity entity : entityList) {
            delete(entity);
        }
    }

}
