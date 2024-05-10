package com.javarush.khmelov.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question implements AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quest_id")
    private Long questId;

    private String text;

    @Column(name = "game_state")
    @Enumerated(EnumType.STRING)
    private GameState gameState;

    public String getImage() {
        return "question-" + id;
    }

    @Transient
    private final Collection<Answer> answers = new ArrayList<>();
}
