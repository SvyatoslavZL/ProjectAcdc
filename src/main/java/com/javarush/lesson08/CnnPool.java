package com.javarush.lesson08;

import com.javarush.lesson07.Cnn;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@UtilityClass
public class CnnPool {

    public static final int SIZE = 10;

    private static final BlockingQueue<Object> proxies = new ArrayBlockingQueue<>(SIZE);
    private static final List<Connection> realConnections = new ArrayList<>(SIZE);


    private static final Cnn cnn = new Cnn();

    @SneakyThrows
    private void init() {
        for (int i = 0; i < SIZE; i++) {
            Connection realConnection = cnn.get();
            realConnections.add(realConnection);
            Object newProxy = Proxy.newProxyInstance(
                    CnnPool.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> {
                        if ("close".equals(method.getName())) {
                            return proxies.add(proxy);
                        } else {
                            return method.invoke(realConnection, args);
                        }
                    }
            );
            proxies.add(newProxy);
        }
    }

    @SneakyThrows
    public Connection get() {
        if (realConnections.isEmpty()){
            init();
        }
        return (Connection) proxies.take();
    }

    @SneakyThrows
    public static void destroy() {
        for (Connection realConnection : realConnections) {
            if (!realConnection.getAutoCommit()){
                realConnection.rollback();
            }
            realConnection.close();
        }
    }
}

