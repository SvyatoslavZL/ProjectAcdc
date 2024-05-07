package com.javarush.lesson09;

import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.exception.AppException;
import com.javarush.khmelov.repository.Repository;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        Transaction tx = null;
        try (session) {
            Class<? extends User> patternClass = pattern.getClass();
            tx = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = query.from(User.class);
            Field[] fields = patternClass.getDeclaredFields();
            List<Predicate> predicates = new ArrayList<>();
            for (Field field : fields) {
                field.trySetAccessible();
                String name = field.getName();
                Object value = field.get(pattern);
                if (value != null && !field.isAnnotationPresent(Transient.class)) {
                    Predicate predicate = criteriaBuilder.equal(userRoot.get(name), value);
                    predicates.add(predicate);
                }
            }
            query.select(userRoot)
                    .where(predicates.toArray(Predicate[]::new));
            Query<User> userQuery = session.createQuery(query);
            List<User> list = userQuery.stream().toList();
            tx.commit();
            return list.stream();
        } catch (Exception e) {
            tx.rollback();
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
