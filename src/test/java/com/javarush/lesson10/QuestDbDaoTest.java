package com.javarush.lesson10;

import com.javarush.kovalinsky.entity.Quest;
import com.javarush.kovalinsky.config.SessionCreator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestDbDaoTest {

    private Session session;
    private SessionCreator sessionCreator;

    private Transaction tx;

    @BeforeEach
    void setUp() {
        sessionCreator = new SessionCreator();
        session = sessionCreator.getSession();

        tx = session.beginTransaction();
    }

    @AfterEach
    void tearDown() {
        tx.rollback();
        session.close();
        sessionCreator.close();
    }

    @Test
    void getQueryWithParam() {
        Query<Quest> questQuery = session.createNamedQuery("QUERY_MORE_THAN_ID1", Quest.class);
        questQuery.setParameter("id", 1);
        questQuery.list().forEach(System.out::println);
    }

    @Test
    void getQueryNativeSql() {
        Query<Quest> questQuery = session.createNativeQuery("SELECT * FROM quest", Quest.class);
        questQuery.list().forEach(System.out::println);
    }
}