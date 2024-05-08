package com.javarush.kovalinsky.entity;

import com.javarush.kovalinsky.util.Key;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question implements Identifiable {

    private final Collection<Answer> answers = new ArrayList<>();

    private Long id;
    private Long questId;
    private String text;
    private GameState gameState;

    public String getImage() {
        return Key.QUESTION + "-" + id;
    }
}
