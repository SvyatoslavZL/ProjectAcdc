package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.BaseIT;
import com.javarush.kovalinsky.config.Winter;
import com.javarush.kovalinsky.entity.Role;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.QuestService;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

class CreateQuestIT extends BaseIT {

    private final CreateQuest createQuest = Winter.find(CreateQuest.class);
    private final QuestService questService = Winter.find(QuestService.class);

    @Disabled
    @Test
    void whenCreateQuest_thenQuestsCountIncreasesByOne() {
        User admin = User.builder().id(1L).role(Role.ADMIN).build();
        doReturn(admin).when(session).getAttribute(Key.USER);
        doReturn("TestQuest").when(request).getParameter(Key.NAME);
        doReturn("1: Test OK?\n2< Да\n3< Нет\n2+ win\n3- lost\n").when(request).getParameter(Key.TEXT);

        int count = questService.getAll().size();
        assertEquals(Go.HOME, createQuest.doPost(request)); //QuestionRepository error
        assertEquals(count + 1, questService.getAll().size());
    }
}