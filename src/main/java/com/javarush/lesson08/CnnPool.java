package com.javarush.lesson08;

import com.javarush.lesson07.Cnn;
import lombok.experimental.UtilityClass;

import java.sql.Connection;

@UtilityClass
public class CnnPool {

    private static final Cnn cnn = new Cnn();

    public Connection get() {
        return cnn.get();
    }

    public static void destroy() {
    }
}

