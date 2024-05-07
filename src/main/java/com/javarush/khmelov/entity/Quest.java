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
@ToString(exclude = {"questions"})
@NamedQueries({
        @NamedQuery(name = "QUERY_MORE_ID", query = "SELECT q FROM Quest q where id>:id"),
        @NamedQuery(name = "QUERY_MORE_ID2", query = "SELECT q FROM Quest q where id>:id"),
        @NamedQuery(name = "QUERY_MORE_ID3", query = "SELECT q FROM Quest q where id=:id"),
        @NamedQuery(name = "QUERY_MORE_ID4", query = "SELECT q FROM Quest q where id>:id"),
        @NamedQuery(name = "QUERY_MORE_ID5", query = "SELECT q FROM Quest q where id>:id"),
})

public class Quest implements AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String text;

    @Column(name = "users_id")
    private Long authorId;

    @Column(name = "start_question_id")
    private Long startQuestionId;

    @Transient
    private final Collection<Question> questions = new ArrayList<>();

}
