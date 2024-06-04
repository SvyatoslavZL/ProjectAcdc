package com.javarush.khmelov.mapping;

import com.javarush.khmelov.dto.*;
import com.javarush.khmelov.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Dto {

    Dto MAPPER = Mappers.getMapper(Dto.class);

    UserTo from(User user);

    @Mapping(target = "userId", source = "author.id")
    QuestTo from(Quest quest);

    QuestionTo from(Question question);

    AnswerTo from(Answer answer);

    GameTo from(Game game);

}
