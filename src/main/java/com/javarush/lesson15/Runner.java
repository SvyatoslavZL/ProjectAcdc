package com.javarush.lesson15;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Runner {
    public static void main(String[] args) {
        Session session = Database.SESSION.open();
        try (session) {
            Transaction tx = session.beginTransaction();

            BaseParent baseParent = BaseParent.builder()
                    .name("baseParentName")
                    .email("baseParentEmail")
                    .build();
            session.persist(baseParent);

            Customer customer = Customer.builder()
                    .name("customerName")
                    .email("customerEmail")
                    .orderCount(321)
                    .build();
            session.persist(customer);

            Seller seller = Seller.builder()
                    .name("sellerName")
                    .email("sellerEmail")
                    .profit(12345d)
                    .build();
            session.persist(seller);

            session.createQuery("SELECT b FROM BaseParent b",BaseParent.class)
                    .list()
                    .forEach(System.out::println);
            tx.commit();
        }

    }
}

