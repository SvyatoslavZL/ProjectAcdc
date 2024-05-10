package com.javarush.khmelov.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game implements AbstractEntity {
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
}
