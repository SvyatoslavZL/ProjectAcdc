package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.BaseIT;
import com.javarush.kovalinsky.config.NanoSpring;
import com.javarush.kovalinsky.util.Key;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class ListUserIT extends BaseIT {

    private final ListUser listUser = NanoSpring.find(ListUser.class);

    @Test
    void whenGetListUsers_thenReturnJspPage() {
        String jspPage = listUser.doGet(request);

        assertEquals("WEB-INF/list-user.jsp", jspPage);
        verify(request).setAttribute(eq(Key.USERS), any(Collection.class));
    }
}
