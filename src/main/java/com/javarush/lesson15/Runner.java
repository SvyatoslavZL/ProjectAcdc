package com.javarush.lesson15;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Runner {
    public static void main(String[] args) {
        Session session = Database.SESSION.open();
        try (session) {
            Transaction tx = session.beginTransaction();
            Customer customer = Customer.builder()
                    .name("customerName")
                    .email("customereEmail")
                    .orderCount(321)
                    .build();
            session.persist(customer);

            Seller seller = Seller.builder()
                    .name("sellerName")
                    .email("sellerEmail")
                    .profit(12345d)
                    .build();
            session.persist(seller);

            System.out.println(customer);
            session.flush();
            tx.commit();
        }

    }
}

