package com.javarush.kovalinsky.util;

import com.javarush.kovalinsky.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

import java.net.URISyntaxException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHelper {

    public static final Pattern CMD_URI_PATTERN = Pattern.compile(".*(/[a-z-]*)");

    private RequestHelper() {
    }

    @SneakyThrows
    public static String getCommand(HttpServletRequest req) {
        String uri = req.getRequestURI();
        Matcher matcher = CMD_URI_PATTERN.matcher(uri);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new URISyntaxException(uri, " - incorrect uri"); //TODO AppException
        }
    }

    public static Long getId(HttpServletRequest req) {
        return getId(req, "id");
    }

    public static Long getId(HttpServletRequest req, String key) {
        String id = req.getParameter(key);
        return id != null && !id.isBlank()
                ? Long.parseLong(id)
                : 0L;
    }

    public static Optional<User> getUser(HttpSession session) {
        Object user = session.getAttribute("user");
        return user != null
                ? Optional.of((User) user)
                : Optional.empty();
    }

}
