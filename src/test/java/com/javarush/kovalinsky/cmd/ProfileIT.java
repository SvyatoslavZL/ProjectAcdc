package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.BaseIT;
import com.javarush.kovalinsky.config.NanoSpring;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProfileIT extends BaseIT {

    private final Profile profile = NanoSpring.find(Profile.class);

    @Test
    void whenClickEditInProfile_thenGoToEditUserPage() {
        Mockito.doReturn(testUser).when(session).getAttribute(Key.USER);
        String uri = profile.doPost(request);
        Assertions.assertEquals(Go.EDIT_USER + "?id=" + testUser.getId(), uri);
    }

    @Test
    void whenClickLogout_thenGoLogout() {
        Mockito.doReturn("true").when(request).getParameter(Key.LOGOUT);
        String uri = profile.doPost(request);
        Assertions.assertEquals(Go.LOGOUT, uri);
    }
}
