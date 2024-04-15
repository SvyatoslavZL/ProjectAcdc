package com.javarush.kovalinsky.config;

import com.javarush.kovalinsky.cmd.Command;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public class HttpResolver {

    private final Map<String, Command> commandMap = new HashMap<>();

    private static String convertURIStyleToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        for (char c : input.toCharArray()) {
            if (c == '-') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        return result.toString();
    }

    @SneakyThrows
    public Command resolve(String name) {
        if (commandMap.get(name) == null) {
            String simpleName = convertURIStyleToCamelCase(name);
            String className = "com.javarush.kovalinsky.cmd." + simpleName;
            var aClass = Class.forName(className);
            Command command = (Command) Winter.find(aClass);
            commandMap.put(name, command);
        }
        return commandMap.get(name);
    }
}
