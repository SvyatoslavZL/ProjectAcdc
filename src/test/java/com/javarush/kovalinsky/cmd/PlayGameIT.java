package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.BaseIT;
import com.javarush.kovalinsky.config.NanoSpring;
import com.javarush.kovalinsky.dto.GameTo;
import com.javarush.kovalinsky.dto.QuestionTo;
import com.javarush.kovalinsky.util.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class PlayGameIT extends BaseIT {

    private final PlayGame playGame = NanoSpring.find(PlayGame.class);

    @Test
    void whenStartGame_thenSetGameAndQuestionInRequest() {
        doReturn(testUser).when(session).getAttribute(Key.USER);
        doReturn("1").when(request).getParameter(Key.QUEST_ID);
        String jspPage = playGame.doGet(request);

        assertEquals("WEB-INF/play-game.jsp", jspPage);
        verify(request).setAttribute(eq(Key.GAME), any(GameTo.class));
        verify(request).setAttribute(eq(Key.QUESTION), any(QuestionTo.class));

    }
}
