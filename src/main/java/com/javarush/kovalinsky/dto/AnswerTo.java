package com.javarush.kovalinsky.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerTo {
    Long id;
    String text;
    Long nextQuestionId;
}
