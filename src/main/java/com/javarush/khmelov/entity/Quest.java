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

    @Transient
    private Long authorId; //not found //todo refactor

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User author;

    @Column(name = "start_question_id")
    private Long startQuestionId;

    @ManyToMany
    @JoinTable(name = "game",
            joinColumns = @JoinColumn(name = "quest_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    final Collection<User> gamers = new ArrayList<>();

    @Transient
    private final Collection<Question> questions = new ArrayList<>();

}
