package com.javarush.lesson10;

import com.javarush.khmelov.entity.Quest;
import com.javarush.khmelov.entity.User;
import com.javarush.lesson09.SessionCreator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class QuestDbDaoTest {

    private Session session;
    private SessionCreator sessionCreator;
    private QuestDbDao questDbDao;

    @BeforeEach
    void setUp() {
        sessionCreator = new SessionCreator();
        session = sessionCreator.getSession();
        questDbDao = new QuestDbDao(sessionCreator);
    }

    @AfterEach
    void tearDown() throws IOException {
        session.close();
        sessionCreator.close();
    }

    @Test
    void getQueryWithParam() {
        Transaction tx = session.beginTransaction();
        Query<Quest> questQuery = session.createNamedQuery("QUERY_MORE_ID3", Quest.class);
        questQuery.setParameter("id", 1);
        questQuery.list().forEach(System.out::println);
        tx.rollback();
    }

    @Test
    void getQueryNativeSql() {
        Transaction tx = session.beginTransaction();
        Query<Quest> questQuery = session.createNativeQuery("SELECT * FROM public.quest", Quest.class);
        questQuery.list().forEach(System.out::println);
        tx.rollback();
    }
}