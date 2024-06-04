package com.javarush.khmelov.repository;

import com.javarush.khmelov.config.SessionCreator;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.JpaRoot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class BaseRepository<T> implements Repository<T> {

    private final Class<T> entityClass;

    private final SessionCreator sessionCreator;

    @Override
    public Collection<T> getAll() {
        Session session = sessionCreator.getSession();
        Query<T> query = session.createQuery(
                "SELECT e FROM %s e".formatted(entityClass.getName()),
                entityClass
        );
        return query.list();

    }

    /* session->cb->cq->root
     * c <- filter fields and add cb.equals(root.get(name), value)
     * cq.select(root).where(predicates);
     * result <- session.createQuery(cq).list(); */
    @SneakyThrows
    @Override
    public Stream<T> find(T pattern) {
        Session session = sessionCreator.getSession();
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
        return session.find(entityClass, id);
    }

    @Override
    public void create(T entity) {
        Session session = sessionCreator.getSession();
        session.saveOrUpdate(entity);
    }

    @Override
    public void update(T entity) {
        Session session = sessionCreator.getSession();
        session.merge(entity);
    }

    @Override
    public void delete(T entity) {
        Session session = sessionCreator.getSession();
        session.remove(entity);
    }
}
