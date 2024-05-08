package com.javarush.kovalinsky.filter;

import com.javarush.kovalinsky.entity.Role;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.util.Err;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import com.javarush.kovalinsky.util.RequestHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {
        Go.INDEX, Go.HOME,
        Go.SIGNUP, Go.LOGIN, Go.LOGOUT,
        Go.PROFILE, Go.LIST_USER, Go.EDIT_USER,

        Go.PLAY_GAME
})
public class AuthorizationFilter extends HttpFilter {
    private final Map<Role, List<String>> uriMap = Map.of(
            Role.GUEST, List.of(
                    Go.HOME, Go.INDEX, Go.SIGNUP, Go.LOGIN
            ),
            Role.USER, List.of(
                    Go.HOME, Go.INDEX, Go.SIGNUP, Go.LOGIN,
                    Go.PROFILE, Go.EDIT_USER, Go.LOGOUT, Go.PLAY_GAME
            ),
            Role.ADMIN, List.of(
                    Go.HOME, Go.INDEX, Go.SIGNUP, Go.LOGIN,
                    Go.PROFILE, Go.EDIT_USER, Go.LOGOUT, Go.PLAY_GAME,
                    Go.LIST_USER
            )
    );

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Optional<User> user = RequestHelper.getUser(req.getSession());
        Role role = user.isEmpty()
                ? Role.GUEST
                : user.get().getRole();
        String cmdUri = RequestHelper.getCommand(req);
        if (uriMap.get(role).contains(cmdUri)) {
            chain.doFilter(req, res);
        } else {
            RequestHelper.setError(req, Err.NO_PERMISSIONS_FOR_OPERATION + Key.ROLE + ": " + role);
            res.sendRedirect(Go.HOME);
        }
    }
}
