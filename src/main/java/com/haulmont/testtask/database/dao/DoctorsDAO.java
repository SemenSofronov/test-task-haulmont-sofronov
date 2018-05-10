package com.haulmont.testtask.database.dao;

import com.haulmont.testtask.database.entities.DoctorsEntity;
import com.haulmont.testtask.database.entities.DoctorsStatistic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DoctorsDAO implements DAOInterface<DoctorsEntity, String> {

    private Session currentSession;

    private Transaction currentTransaction;

    public DoctorsDAO() {
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

    public void persist(DoctorsEntity entity) {
        getCurrentSession().persist(entity);
    }

    public void update(DoctorsEntity entity) {
        getCurrentSession().merge(entity);
    }

    public DoctorsEntity findById(String id) {
        DoctorsEntity doctorsEntity = (DoctorsEntity) getCurrentSession().get(DoctorsEntity.class, id);
        return doctorsEntity;
    }

    public void delete(DoctorsEntity entity) {
        getCurrentSession().delete(entity);
    }

    public List<DoctorsEntity> findAll() {
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<DoctorsEntity> q = criteriaBuilder.createQuery(DoctorsEntity.class);
        Root<DoctorsEntity> c = q.from(DoctorsEntity.class);
        q.select(c);
        TypedQuery<DoctorsEntity> queryForData = getCurrentSession().createQuery(q);
        return queryForData.getResultList();
    }

    public void deleteAll() {
        List<DoctorsEntity> entityList = findAll();
        for (DoctorsEntity entity : entityList) {
            delete(entity);
        }
    }

    public List<DoctorsStatistic> getStatistic() {
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<DoctorsStatistic> q = criteriaBuilder.createQuery(DoctorsStatistic.class);
        Root<DoctorsStatistic> c = q.from(DoctorsStatistic.class);
        q.select(c);
        TypedQuery<DoctorsStatistic> queryForData = getCurrentSession().createQuery(q);
        return queryForData.getResultList();
    }
}
