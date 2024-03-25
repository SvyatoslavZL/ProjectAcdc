package com.javarush.kovalinsky.lesson14.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer implements AbstractEntity {
    private int id;

    private int questionId;

    private String text;

    private int nextQuestionId;
}
