package com.javarush.kovalinsky.entity;

import com.javarush.kovalinsky.dto.GameState;
import com.javarush.kovalinsky.util.Key;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "question")
public class Question implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quest_id")
    private Long questId;

    @Column(name = "text")
    private String text;

    @Column(name = "game_state")
    @Enumerated(EnumType.STRING)
    private GameState gameState;

    @OneToMany
    @JoinColumn(name = "question_id")
    @ToString.Exclude
    private final Collection<Answer> answers = new ArrayList<>();

    @Transient
    public String getImage() {
        return Key.QUESTION + "-" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Question question = (Question) o;
        return getId() != null && Objects.equals(getId(), question.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
