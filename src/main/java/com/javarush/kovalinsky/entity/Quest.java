package com.javarush.kovalinsky.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "quest")
@BatchSize(size = 10)
public class Quest implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    @ToString.Exclude
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    @ToString.Exclude
    private User author;

    @Column(name = "start_question_id")
    private Long startQuestionId;

    @OneToMany
    @JoinColumn(name = "quest_id")
    @ToString.Exclude
    private final List<Question> questions = new ArrayList<>();
}
