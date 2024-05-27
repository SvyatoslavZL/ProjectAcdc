package com.javarush.khmelov.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@ToString
public class User implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public String getImage() { //TODO move to DTO
        return "user-" + id;
    }

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private final Collection<Quest> quests = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    @ToString.Exclude
    UserData userData;

    @Transient
    @ToString.Exclude
    private final Collection<Game> games = new ArrayList<>();

    public void addQuest(Quest quest){
        quest.setAuthor(this);
        quests.add(quest);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id!=null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 42;
    }

    @ManyToMany
    @JoinTable(name = "game",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "quest_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    final Collection<Quest> gameQuests = new ArrayList<>();
}
