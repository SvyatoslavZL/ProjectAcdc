package com.javarush.kovalinsky.entity;

import com.javarush.kovalinsky.util.Key;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Identifiable {
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
        return Key.QUESTION + "-" + id;
    }

    @Transient
    private final Collection<Answer> answers = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return 47;
    }
}
