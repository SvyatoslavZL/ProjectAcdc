package com.javarush.kovalinsky.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "answer")
public class Answer implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "text")
    private String text;

    @Column(name = "next_question_id")
    private Long nextQuestionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Answer answer = (Answer) o;
        return getId() != null && Objects.equals(getId(), answer.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
