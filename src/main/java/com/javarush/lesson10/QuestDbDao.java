package com.javarush.lesson10;

import com.javarush.kovalinsky.entity.Quest;
import com.javarush.kovalinsky.exception.AppException;
import com.javarush.kovalinsky.repository.Repository;
import com.javarush.kovalinsky.config.SessionCreator;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class QuestDbDao implements Repository<Quest> {

    private final SessionCreator sessionCreator;

    @Override
    public Collection<Quest> getAll() {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                Query<Quest> questQuery = session.createQuery("SELECT q FROM Quest q", Quest.class);
                List<Quest> list = questQuery.list();
                tx.commit();
                return list;
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    @Override
    public Stream<Quest> find(Quest pattern) {
        //Criteria API
        return null;
    }

    @Override
    public Quest get(long id) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                Quest quest = session.find(Quest.class, id);
                tx.commit();
                return quest;
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    @Override
    public void create(Quest quest) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(quest);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    @Override
    public void update(Quest quest) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                session.merge(quest);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    @Override
    public void delete(Quest quest) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                session.remove(quest);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }
}
