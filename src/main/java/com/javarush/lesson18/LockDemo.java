package com.javarush.lesson18;

import com.javarush.khmelov.config.NanoSpring;
import com.javarush.khmelov.config.SessionCreator;
import com.javarush.khmelov.entity.Game;
import jakarta.persistence.LockModeType;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LockDemo {
    public static void main(String[] args) {
        SessionCreator sessionCreator = NanoSpring.find(SessionCreator.class);
        Session session1 = sessionCreator.getSession();
        Session session2 = sessionCreator.getSession();
        Transaction tx1 = session1.beginTransaction();
        Transaction tx2 = session2.beginTransaction();
        Game game1 = session1.find(Game.class, 1L, LockModeType.OPTIMISTIC);
        Game game2 = session2.find(Game.class, 1L, LockModeType.OPTIMISTIC);
        System.out.println("1."+game1);
        game1.setCurrentQuestionId(1111L);
        session1.flush();
        game2.setCurrentQuestionId(2222L);
        session2.flush();
        tx1.commit();
        tx2.commit();

        System.out.println("DB."+sessionCreator.getSession().find(Game.class, 1L));

    }
}
