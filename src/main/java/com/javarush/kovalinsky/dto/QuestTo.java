package com.javarush.kovalinsky.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class QuestTo {
    Long id;
    Long userId;
    String name;
    String text;
    Long startQuestionId;
    Collection<QuestionTo> questions;
}
