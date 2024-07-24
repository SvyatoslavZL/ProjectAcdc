package com.javarush.kovalinsky.entity;

import com.javarush.kovalinsky.dto.Role;
import com.javarush.kovalinsky.util.Key;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "users")
public class User implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
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
        return Key.USER + "-" + id;
    }
}


