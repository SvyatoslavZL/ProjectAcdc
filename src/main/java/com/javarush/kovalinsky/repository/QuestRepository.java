package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.entity.Quest;

import java.util.stream.Stream;

public class QuestRepository extends BaseRepository<Quest> {

    @Override
    public Stream<Quest> find(Quest pattern) {
        return map.values()
                .stream()
                .filter(repoQuest -> nullOrEquals(pattern.getId(), repoQuest.getId()))
                .filter(repoQuest -> nullOrEquals(pattern.getText(), repoQuest.getText()))
                .filter(repoQuest -> nullOrEquals(pattern.getName(), repoQuest.getName()))
                .filter(repoQuest -> nullOrEquals(pattern.getAuthorId(), repoQuest.getAuthorId()))
                .filter(repoQuest -> nullOrEquals(pattern.getStartQuestionId(), repoQuest.getStartQuestionId()));
    }
}
