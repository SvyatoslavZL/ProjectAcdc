package com.javarush.lesson08;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcPrepStatDemo {

    public static final String SQL_GET_BY_ID = """
            SELECT "id", "login", "password", "role"
            FROM "users"
            WHERE id=?
            """;

    public static void main(String[] args) throws SQLException {
        try (Connection connection = CnnPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {
            preparedStatement.setLong(1, 1L);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("login"));
            }
        }
    }
}
