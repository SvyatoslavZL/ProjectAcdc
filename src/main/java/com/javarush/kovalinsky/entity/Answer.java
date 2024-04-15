package com.javarush.kovalinsky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer implements Identifiable {
    private Long id;
    private Long questionId;
    private String text;
    private Long nextQuestionId;
}
