package com.javarush.lesson18;

import com.javarush.khmelov.config.NanoSpring;
import com.javarush.khmelov.config.SessionCreator;
import com.javarush.khmelov.entity.Game;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class A_LostUpdate {
    public static void main(String[] args) {
        SessionCreator sessionCreator = NanoSpring.find(SessionCreator.class);
        Session session1 = sessionCreator.getSession();
        Session session2 = sessionCreator.getSession();
        Transaction tx1 = session1.beginTransaction();
        Transaction tx2 = session2.beginTransaction();
        Game game1 = session1.find(Game.class, 1L);
        Game game2 = session2.find(Game.class, 1L);
        game1.setCurrentQuestionId(game1.getCurrentQuestionId()+100);
        game2.setCurrentQuestionId(game2.getCurrentQuestionId()+10);
        tx1.commit();
        tx2.rollback();
        System.out.println(sessionCreator.getSession().find(Game.class, 1L));


    }
}
