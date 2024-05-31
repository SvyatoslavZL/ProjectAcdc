package com.javarush.khmelov.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
@Table(name = "users")
public class User implements AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private List<Quest> quests;

    @OneToMany
    @JoinColumn(name = "users_id")
    @ToString.Exclude
    private final Collection<Game> games = new ArrayList<>();

    public String getImage() { //TODO move to DTO
        return "user-" + id;
    }


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInfo userInfo;


    @ManyToMany
    @JoinTable(name = "game",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "quest_id", referencedColumnName = "id"))
    @ToString.Exclude
    final Collection<Quest> questsInGame = new ArrayList<>();



}
