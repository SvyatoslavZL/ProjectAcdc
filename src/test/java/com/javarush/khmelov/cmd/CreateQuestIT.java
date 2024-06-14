package com.javarush.khmelov.cmd;

import com.javarush.khmelov.BaseIT;
import com.javarush.khmelov.config.NanoSpring;
import com.javarush.khmelov.dto.Role;
import com.javarush.khmelov.dto.UserTo;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.service.QuestService;
import com.javarush.khmelov.util.Go;
import com.javarush.khmelov.util.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CreateQuestIT extends BaseIT {

    private final CreateQuest createQuest = NanoSpring.find(CreateQuest.class);
    private final QuestService questService = NanoSpring.find(QuestService.class);


    @Test
    void whenCreateQuest_thenQuestsCountIncreaseByOne() {
        UserTo admin = UserTo.builder().id(1L).role(Role.ADMIN).build();
        when(session.getAttribute(Key.USER)).thenReturn(admin);
        when(request.getParameter(Key.NAME)).thenReturn("TestQuest");
        when(request.getParameter(Key.TEXT)).thenReturn("1: Test OK?\n2< Да\n3< Нет\n2+ win\n3- lost\n");

        int count = questService.getAll().size();
        assertEquals(Go.HOME, createQuest.doPost(request));
        assertEquals(count + 1, questService.getAll().size());
    }

}