package com.javarush.lesson07;

import com.javarush.kovalinsky.config.Cnn;

import java.sql.*;

public class JdbcDemo {

    public static final String READ_ALL_USERS_SQL = "SELECT id, login, password, role FROM users";
    public static final String ADD_NEW_USERS_SQL = """
            INSERT INTO users (id, login, password, role)
            VALUES (DEFAULT, 'ZipL1', 'admin', 'ADMIN'),
                   (DEFAULT, 'Alisa1', 'qwerty', 'USER'),
                   (DEFAULT, 'Bob1', '123', 'GUEST');
                   """;
    public static final String DELETE_USERS_MORE_THAN_ID3 = "DELETE FROM users WHERE id > 38;";

    public static void main(String[] args) throws SQLException {
        Cnn connector = new Cnn();

        try (Connection connection = connector.get();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(READ_ALL_USERS_SQL);
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                String name = metaData.getColumnName(i + 1);
                String type = metaData.getColumnTypeName(i + 1);
                System.out.printf("%7s:%7s", name, type);
            }
            System.out.println();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                int row = resultSet.getRow();
                System.out.printf("%15s%15s%15s%15s%15d%n", id, login, password, role, row);
            }

//            int count = statement.executeUpdate(addUsersSql, Statement.RETURN_GENERATED_KEYS);
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            while (generatedKeys.next()) {
//                System.out.println(generatedKeys.getLong("id"));
//            }
//            System.out.println(count);
//            statement.executeUpdate(DELETE_USERS_MORE_THAN_ID3);
        }
    }
}
