package com.javarush.kovalinsky.entity;

import com.javarush.kovalinsky.util.Key;
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
@Table(name = "users")
@ToString
public class User implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public String getImage() {
        return Key.USER + "-" + id;
    }

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private final Collection<Quest> quests = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    @JoinColumn(name = "users_id")
    private final Collection<Game> games = new ArrayList<>();

    public void addQuest(Quest quest) {
        quest.setAuthor(this);
        quests.add(quest);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 42;
    }
}


