package com.javarush.kovalinsky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Identifiable {

    private final Collection<Quest> quests = new ArrayList<>();
    private final Collection<Game> games = new ArrayList<>();

    private Long id;
    private String login;
    private String password;
    private Role role;

    //TODO image
}