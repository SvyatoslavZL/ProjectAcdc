package com.javarush.khmelov.entity;

import lombok.*;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FetchProfile(name = Quest.LAZY_QUESTIONS_AND_JOIN_AUTHOR,
        fetchOverrides={
                @FetchProfile.FetchOverride(
                        entity = Quest.class,
                        association = "questions",
                        mode = FetchMode.JOIN //тут и ниже надо было JOIN
                ),
                @FetchProfile.FetchOverride(
                        entity = Quest.class,
                        association ="author",
                        mode = FetchMode.JOIN
                )
        }
)
public class Quest implements AbstractEntity {
    public static final String LAZY_QUESTIONS_AND_JOIN_AUTHOR = "lazy_questions_and_join_author";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

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
//    @Fetch(FetchMode.SUBSELECT)
    private List<Question> questions;


    @ManyToMany
    @JoinTable(name = "game",
            joinColumns = @JoinColumn(name = "quest_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    final Collection<User> players=new ArrayList<>();

}
