package com.javarush.khmelov;

import com.javarush.khmelov.config.Configurator;
import com.javarush.khmelov.config.NanoSpring;
import com.javarush.khmelov.dto.Role;
import com.javarush.khmelov.dto.UserTo;
import com.javarush.khmelov.entity.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseIT extends ContainerIT{
    protected final HttpServletRequest request;
    protected final HttpServletResponse response;
    protected final HttpSession session;
    protected final Configurator configurator;
    protected final ServletConfig servletConfig;
    protected final ServletContext servletContext;
    protected final UserTo testAdmin;
    protected final UserTo testUser;
    protected final UserTo testGuest;

    protected BaseIT() {
        //app configurator
        configurator = NanoSpring.find(Configurator.class);
        configurator.fillStartData();
        //servlet configurator
        servletConfig = mock(ServletConfig.class);
        servletContext = mock(ServletContext.class);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        //current op
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        //test data
        testAdmin = UserTo.builder()
                .id(1L)
                .login("testAdmin")
                .password("testAdmin")
                .role(Role.ADMIN)
                .build();
        testUser = UserTo.builder()
                .id(2L)
                .login("testUser")
                .password("testUser")
                .role(Role.USER)
                .build();
        testGuest = UserTo.builder()
                .id(3L)
                .login("testGuest")
                .password("testGuest")
                .role(Role.USER)
                .build();
    }
}
