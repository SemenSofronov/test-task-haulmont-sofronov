package com.haulmont.testtask.database.dao;

import com.haulmont.testtask.database.entities.PrioritiesEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PrioritiesDAO implements DAOInterface<PrioritiesEntity, String> {

    private Session currentSession;

    private Transaction currentTransaction;

    public PrioritiesDAO() {
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

    public void persist(PrioritiesEntity entity) {
        getCurrentSession().persist(entity);
    }

    public void update(PrioritiesEntity entity) {
        getCurrentSession().merge(entity);
    }

    public PrioritiesEntity findById(String id) {
        PrioritiesEntity prioritiesEntity = (PrioritiesEntity) getCurrentSession().get(PrioritiesEntity.class, id);
        return prioritiesEntity;
    }

    public void delete(PrioritiesEntity entity) {
        getCurrentSession().delete(entity);
    }

    public List<PrioritiesEntity> findAll() {
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<PrioritiesEntity> q = criteriaBuilder.createQuery(PrioritiesEntity.class);
        Root<PrioritiesEntity> c = q.from(PrioritiesEntity.class);
        q.select(c);
        TypedQuery<PrioritiesEntity> queryForData = getCurrentSession().createQuery(q);
        return queryForData.getResultList();
//        return getCurrentSession().createCriteria(PatientsEntity.class).list();
    }

    public void deleteAll() {
        List<PrioritiesEntity> entityList = findAll();
        for (PrioritiesEntity entity : entityList) {
            delete(entity);
        }
    }

}
