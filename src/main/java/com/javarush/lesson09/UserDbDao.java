package com.javarush.lesson09;

import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.exception.AppException;
import com.javarush.khmelov.repository.Repository;
import jakarta.persistence.Transient;
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
public class UserDbDao implements Repository<User> {

    private final SessionCreator sessionCreator;

    @Override
    public Collection<User> getAll() {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                Query<User> queryUser = session.createQuery("SELECT u FROM User u", User.class);
                List<User> list = queryUser.list();
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
    public Stream<User> find(User pattern) {
        Session session = sessionCreator.getSession();
        try (session) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(User.class);
            JpaRoot<User> from = criteriaQuery.from(User.class);
            var predicates = new ArrayList<Predicate>();
            for (Field field : pattern.getClass().getDeclaredFields()) {
                field.trySetAccessible();
                String name = field.getName();
                Object value = field.get(pattern);
                if (Objects.nonNull(value)
                    && !field.isAnnotationPresent(Transient.class)) {
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

    @Override
    public User get(long id) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                User user = session.find(User.class, id);
                tx.commit();
                return user;
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    @Override
    public void create(User user) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(user);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    @Override
    public void update(User user) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                session.merge(user);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    @Override
    public void delete(User user) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                session.remove(user);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }
}
