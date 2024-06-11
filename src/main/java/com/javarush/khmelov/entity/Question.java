package com.javarush.khmelov.entity;

import com.javarush.khmelov.dto.GameState;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "question")
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

    @OneToMany
    @JoinColumn(name = "question_id")
    @ToString.Exclude
    private final Collection<Answer> answers = new ArrayList<>();

    @Transient
    public String getImage() {
        return "question-" + id;
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
