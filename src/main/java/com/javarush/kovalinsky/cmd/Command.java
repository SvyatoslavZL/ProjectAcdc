package com.javarush.kovalinsky.cmd;

import jakarta.servlet.http.HttpServletRequest;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Command {

    private static String convertCamelCaseToURIStyle(String input) {
        String snakeName = input.chars()
                .mapToObj(s -> String.valueOf((char) s))
                .flatMap(s -> s.matches("[A-Z]")
                        ? Stream.of("-", s)
                        : Stream.of(s))
                .collect(Collectors.joining())
                .toLowerCase();
        return snakeName.startsWith("-")
                ? snakeName.substring(1)
                : snakeName;
    }

    default String doGet(HttpServletRequest req) {
        return getJspPage();
    }

    default String getJspPage() {
        return "WEB-INF/%s.jsp".formatted(getPage());
    }

    default String doPost(HttpServletRequest req) {
        return getPage();
    }

    default String getPage() {
        String simpleName = this.getClass().getSimpleName();
        return convertCamelCaseToURIStyle(simpleName);
    }
}
