package com.javarush.kovalinsky.entity;

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
@ToString(exclude = {"questions"})
@NamedQuery(name = "QUERY_MORE_THAN_ID1", query = "SELECT q FROM Quest q WHERE id > :id")
public class Quest implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String text;

    @Column(name = "users_id")
    private Long authorId;

    //todo
    @ManyToOne
    @ToString.Exclude
    @Column(name = "users_id")
    private User author;

    @Column(name = "start_question_id")
    private Long startQuestionId;

    @Transient
    private final Collection<Question> questions = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quest quest = (Quest) o;
        return Objects.equals(id, quest.id);
    }

    @Override
    public int hashCode() {
        return 46;
    }
}
