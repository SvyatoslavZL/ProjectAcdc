package com.javarush.khmelov.repository;

import com.javarush.khmelov.exception.AppException;
import com.javarush.khmelov.config.SessionCreator;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.JpaRoot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class BaseRepository<T> implements Repository<T> {

    private final Class<T> entityClass;

    private final SessionCreator sessionCreator;

    @Override
    public Collection<T> getAll() {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                Query<T> query = session.createQuery(
                        "SELECT e FROM %s e".formatted(entityClass.getName()),
                        entityClass
                );
                List<T> list = query.list();
                tx.commit();
                return list;
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    /* session->cb->cq->root
     * c <- filter fields and add cb.equals(root.get(name), value)
     * cq.select(root).where(predicates);
     * result <- session.createQuery(cq).list(); */
    @Override
    public Stream<T> find(T pattern) {
        Session session = sessionCreator.getSession();
        try (session) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(entityClass);
            JpaRoot<T> from = criteriaQuery.from(entityClass);
            var predicates = new ArrayList<Predicate>();
            for (Field field : pattern.getClass().getDeclaredFields()) {
                field.trySetAccessible();
                String name = field.getName();
                Object value = field.get(pattern);
                if (isPredacate(field, value)) {
                    var predicate = criteriaBuilder.equal(from.get(name), value);
                    predicates.add(predicate);
                }
            }
            criteriaQuery.select(from);
            criteriaQuery.where(predicates.toArray(Predicate[]::new));
            return session.createQuery(criteriaQuery).list().stream();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isPredacate(Field field, Object value) {
        return Objects.nonNull(value)
               && !field.isAnnotationPresent(Transient.class)
               && !field.isAnnotationPresent(OneToMany.class)
               && !field.isAnnotationPresent(ManyToOne.class)
               && !field.isAnnotationPresent(OneToOne.class)
               && !field.isAnnotationPresent(ManyToMany.class);
    }

    @Override
    public T get(long id) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                T entity = session.find(entityClass, id);
                tx.commit();
                return entity;
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    @Override
    public void create(T entity) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(entity);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    @Override
    public void update(T entity) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                session.merge(entity);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    @Override
    public void delete(T entity) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                session.remove(entity);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }
}
