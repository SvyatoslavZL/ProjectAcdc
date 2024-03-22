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
public class Question implements AbstractEntity {
    private Long id;

    private Long questId;

    private String text;

    public final List<Answer> answers = new ArrayList<>();
}
