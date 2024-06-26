package com.javarush.khmelov.cmd;

import com.javarush.khmelov.BaseIT;
import com.javarush.khmelov.config.NanoSpring;
import com.javarush.khmelov.dto.GameTo;
import com.javarush.khmelov.dto.QuestionTo;
import com.javarush.khmelov.entity.Game;
import com.javarush.khmelov.entity.Question;
import com.javarush.khmelov.util.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlayGameIT extends BaseIT {

    private final PlayGame playGame = NanoSpring.find(PlayGame.class);

    @Test
    void whenStartGame_thenSetGameAndQuestionInRequest() {
        when(session.getAttribute(Key.USER)).thenReturn(testUser);
        when(request.getParameter(Key.QUEST_ID)).thenReturn("1");
        String jspPage = playGame.doGet(request);

        assertEquals("WEB-INF/play-game.jsp", jspPage);
        verify(request).setAttribute(eq(Key.GAME), any(GameTo.class));
        verify(request).setAttribute(eq(Key.QUESTION), any(QuestionTo.class));
    }

}