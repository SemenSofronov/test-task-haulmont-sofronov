package com.haulmont.testtask.database.dao;

import com.haulmont.testtask.database.entities.RecipesEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class RecipesDAO implements DAOInterface<RecipesEntity, String> {

    private Session currentSession;

    private Transaction currentTransaction;

    public RecipesDAO() {
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

    public void persist(RecipesEntity entity) {
        getCurrentSession().persist(entity);
    }

    public void update(RecipesEntity entity) {
        getCurrentSession().merge(entity);
    }

    public RecipesEntity findById(String id) {
        RecipesEntity recipesEntity = (RecipesEntity) getCurrentSession().get(RecipesEntity.class, id);
        return recipesEntity;
    }

    public void delete(RecipesEntity entity) {
        getCurrentSession().delete(entity);
    }

    public List<RecipesEntity> findAll() {
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<RecipesEntity> q = criteriaBuilder.createQuery(RecipesEntity.class);
        Root<RecipesEntity> c = q.from(RecipesEntity.class);
        q.select(c);
        TypedQuery<RecipesEntity> queryForData = getCurrentSession().createQuery(q);
        return queryForData.getResultList();
    }

    public void deleteAll() {
        List<RecipesEntity> entityList = findAll();
        for (RecipesEntity entity : entityList) {
            delete(entity);
        }
    }

    public List<RecipesEntity> getItemsByFilter(String patientFilterValue, String priorityFilterValue, String descriptionFilterValue) {
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<RecipesEntity> query = criteriaBuilder.createQuery(RecipesEntity.class);
        Root<RecipesEntity> root = query.from(RecipesEntity.class);
        Predicate descriptionClause = addLikeValueToBuilderDesription(criteriaBuilder, root, "description", descriptionFilterValue);

        Predicate priorityClause = addLikeValueToBuilderPriority(criteriaBuilder, root, "priority", priorityFilterValue);
        Predicate patientClause = addLikeValueToBuilderPatient(criteriaBuilder, root, "patient", patientFilterValue);
        query.where(criteriaBuilder.and(descriptionClause, priorityClause, patientClause));
        query.select(root);
        TypedQuery<RecipesEntity> queryForData = getCurrentSession().createQuery(query);
        return queryForData.getResultList();
    }


    protected Predicate addLikeValueToBuilderDesription(CriteriaBuilder builder, Root root, String column, String value) {
        Predicate likeClause = builder.or(builder.isNotNull(root.get(column)), builder.isNull(root.get(column)));
        if (value != null && !value.trim().isEmpty()) {
            likeClause = builder.like(builder.lower(root.get(column)), "%" + value.toLowerCase() + "%");
        }
        return likeClause;
    }

    protected Predicate addLikeValueToBuilderPriority(CriteriaBuilder builder, Root root, String column, String value) {
        Predicate likeClause = builder.or(builder.isNotNull(root.get(column).get("name")), builder.isNull(root.get(column).get("name")));
        if (value != null && !value.trim().isEmpty()) {
            likeClause = builder.like(builder.lower(root.get(column).get("name")), "%" + value.toLowerCase() + "%");
        }
        return likeClause;
    }

    protected Predicate addLikeValueToBuilderPatient(CriteriaBuilder builder, Root root, String column, String value) {
        Predicate likeClause = builder.or(builder.isNotNull(root.get(column).get("surname")), builder.isNull(root.get(column).get("surname")));
        if (value != null && !value.trim().isEmpty()) {
            likeClause = builder.like(builder.lower(root.get(column).get("surname")), "%" + value.toLowerCase() + "%");
        }
        return likeClause;
    }
}
