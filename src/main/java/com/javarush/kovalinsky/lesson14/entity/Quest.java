package com.javarush.kovalinsky.lesson14.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quest implements AbstractEntity {
    private Long id;

    private String name;

    private Long startQuestionId = 0L;

    public final List<Question> questions = new ArrayList<>();
}
