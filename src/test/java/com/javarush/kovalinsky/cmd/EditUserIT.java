package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.BaseIT;
import com.javarush.kovalinsky.config.NanoSpring;
import com.javarush.kovalinsky.dto.Role;
import com.javarush.kovalinsky.dto.UserTo;
import com.javarush.kovalinsky.service.UserService;
import com.javarush.kovalinsky.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class EditUserIT extends BaseIT {

    private final EditUser editUser = NanoSpring.find(EditUser.class);
    private final UserService userService = NanoSpring.find(UserService.class);

    @Test
    void whenOpenPage_thenCommandReturnJspPage() {
        UserTo user = userService.getAll().stream().findFirst().orElseThrow();
        doReturn(user.getId().toString()).when(request).getParameter(Key.ID);

        String jspView = editUser.doGet(request);
        assertEquals("WEB-INF/edit-user.jsp", jspView);
        verify(request).setAttribute(eq(Key.USER), eq(user));
    }

    @Test
    void whenUpdateUser_thenGetPageByUserId() throws ServletException, IOException {
        doReturn("TestName").when(request).getParameter(Key.LOGIN);
        doReturn("TestPassword").when(request).getParameter(Key.PASSWORD);
        doReturn(Role.GUEST.toString()).when(request).getParameter(Key.ROLE);
        final String ID = "1";
        doReturn(ID).when(request).getParameter(Key.ID);
        String redirectUri = editUser.doPost(request);

        assertTrue(redirectUri.endsWith("?id=" + ID));
    }
}
