package com.javarush.khmelov.entity;

import com.javarush.khmelov.dto.GameState;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Game implements AbstractEntity, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quest_id")
    private Long questId;

    @Column(name = "users_id")
    private Long userId;

    @Column(name = "current_question_id")
    private Long currentQuestionId;

    @Column(name = "game_state")
    @Enumerated(EnumType.STRING)
    private GameState gameState;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Game game = (Game) o;
        return getId() != null && Objects.equals(getId(), game.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
