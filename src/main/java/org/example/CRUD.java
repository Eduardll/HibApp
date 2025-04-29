package org.example;

import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CRUD<T> implements InterfaceCRUD {
    public SessionFactory sessionFactory;
    private final Class<T> user;
    private final Logger logger = LoggerFactory.getLogger(CRUD.class);

    public CRUD(Class<T> user) {
        this.user = user;
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void create(Object entity){
        Session session = getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }catch(Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            logger.warn("ошибка в create");
            throw e;
        }
    }
    @Override
    public T readById(Long id){
        Session session = getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            T entity = getCurrentSession().get(user,id);
            transaction.commit();
            return entity;
        }catch (Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            logger.warn("ошибка в readById");
            throw e;
        }
    }
    @Override
    public List<T> readall(){
        Session session = getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            String hql = "From " + user.getSimpleName();
            List<T> resultList = getCurrentSession().createQuery(hql, user).getResultList();
            transaction.commit();
            return resultList;
        }catch (Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            logger.warn("ошибка в readAll");
            throw e;
        }
    }
    @Override
    public void update(Object entity){
        Session session = getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();

        }catch (Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            logger.warn("ошибка в update");
            throw e;
        }
    }
    private void delete(T entity){
        Session session = getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();

        }catch (Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            logger.warn("ошибка в delete");
            throw e;
        }
    }
    @Override
    public void deleteById(Long id){
        T entity = readById(id);
        if (entity == null) {
            logger.warn("ошибка в deleteById");
            throw new IllegalArgumentException("Данные по этому id: " + id + " не найдены");
        }
        delete(entity);
    }
    public int deleteAll(){
        Session session = getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            String hql = "delete from " + user.getSimpleName();
            int resultList = session.createQuery(hql).executeUpdate();
            transaction.commit();
            return resultList;
        }catch(Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
