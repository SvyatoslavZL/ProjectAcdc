package com.javarush.lesson14;

import com.javarush.khmelov.config.SessionCreator;
import com.javarush.khmelov.entity.Quest;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.exception.AppException;
import jakarta.persistence.Subgraph;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

public class PerformanceTest {


    @Test
    public void read() throws Exception {
        @Cleanup SessionCreator sessionCreator = new SessionCreator();
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            //session.enableFetchProfile(Quest.LAZY_QUESTIONS_AND_JOIN_AUTHOR_FETCH);
            try {
                Query<Quest> questQuery = session.createQuery("SELECT q from Quest q", Quest.class);
                String hintName = GraphSemantic.FETCH.getJakartaHintName();

                RootGraph<Quest> codeGraph = session.createEntityGraph(Quest.class);
                codeGraph.addAttributeNodes("questions", "author");
                Subgraph<User> userSubgraph = codeGraph.addSubgraph("author", User.class);
                userSubgraph.addAttributeNodes("userInfo");

                RootGraph<?> annoGraph = session.createEntityGraph(Quest.GRAPH_QUEST_QUESTIONS_AUTHOR_FETCH);
                questQuery.setHint(hintName,codeGraph);
                for (Quest quest : questQuery.list()) {
                    User author = quest.getAuthor();
                    System.out.println(author.getLogin() + " " + author.getUserInfo() + " " + quest.getId() + ": " + quest.getQuestions().size());
                }

                String s1="1";
                String s2="2";
                String s3;
                System.out.println(s3=s1+""+s2+(false && !false));


                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }

    }

}
