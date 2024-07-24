package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.BaseIT;
import com.javarush.kovalinsky.config.NanoSpring;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class QuestIT extends BaseIT {

    private final Quest quest = NanoSpring.find(Quest.class);

    @Test
    void whenOpenQuestPageWithCorrectId_thenGetJsp() {
        Mockito.doReturn("1").when(request).getParameter(Key.ID);
        String jsp = quest.doGet(request);
        Assertions.assertEquals("WEB-INF/quest.jsp", jsp);
    }

    @Test
    void whenAnonymousPostQuest_thenRedirectBackward() throws ServletException, IOException {
        Mockito.doReturn("1").when(request).getParameter(Key.ID);
        String uri = quest.doPost(request);
        Assertions.assertEquals(Go.QUEST, uri);
    }

    @Test
    void whenNonAdminPostQuest_thenRedirectBackward() throws ServletException, IOException {
        Mockito.doReturn(testGuest).when(session).getAttribute(Key.USER);
        Mockito.doReturn("1").when(request).getParameter(Key.ID);
        String uri = quest.doPost(request);
        Assertions.assertEquals(Go.QUEST, uri);
    }

    @Test
    void whenAdminPostQuest_thenRedirectBackward() throws ServletException, IOException {
        Mockito.doReturn(testAdmin).when(session).getAttribute(Key.USER);
        Mockito.doReturn("1").when(request).getParameter(Key.ID);
        Mockito.doReturn("1").when(request).getParameter(Key.QUEST_ID);
        Mockito.doReturn("1").when(request).getParameter(Key.QUESTION_ID);
        Mockito.doReturn("newTestTextQuestion").when(request).getParameter(Key.TEXT);
        String actualUri = quest.doPost(request);
        String expectedUri = "%s?id=%d#bookmark%d".formatted(Go.QUEST, 1, 1);
        Assertions.assertEquals(expectedUri, actualUri);
    }
}
