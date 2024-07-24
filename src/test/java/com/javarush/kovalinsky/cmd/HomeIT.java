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

class HomeIT extends BaseIT {

    @Test
    void whenOpenPage_thenCommandReturnJspPage() {
        Home home = NanoSpring.find(Home.class);
        String jsp = home.doGet(request);

        assertEquals("WEB-INF/home.jsp", jsp);
        verify(request).setAttribute(eq(Key.QUESTS), any(Collection.class));
    }
}
