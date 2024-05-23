package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.config.SessionCreator;
import com.javarush.kovalinsky.entity.GameState;
import com.javarush.kovalinsky.entity.Quest;
import com.javarush.kovalinsky.entity.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionRepositoryTest {

    private final QuestRepository questRepository = new QuestRepository(new SessionCreator());
    private final QuestionRepository questionRepository = new QuestionRepository(new SessionCreator());
    private Quest testQuest;
    private Question testQuestion;

    @BeforeEach
    void createQuestAndQuestion() {
        testQuest = Quest.builder()
                .name("testQuest")
                .text("testText")
                .authorId(1L)
                .build();
        questRepository.create(testQuest);
        testQuestion = Question.builder()
                .text("testQuestion")
                .gameState(GameState.PLAY)
                .questId(testQuest.getId())
                .build();
        questionRepository.create(testQuestion);
        testQuest.setStartQuestionId(testQuestion.getId());
    }

    @Test
    void get() {
        Question question = questionRepository.get(testQuestion.getId());
        assertEquals(testQuestion, question);
    }

    @Test
    void find() {
        Question pattern = Question.builder().text("testQuestion").build();
        Stream<Question> questionStream = questionRepository.find(pattern);
        assertEquals(testQuestion, questionStream.findFirst().orElseThrow());
    }

    @Test
    void update() {
        testQuestion.setText("newText");
        questionRepository.update(testQuestion);
        Question question = questionRepository.get(testQuestion.getId());
        assertEquals(testQuestion, question);
    }

    @AfterEach
    void tearDown() {
        questionRepository.delete(testQuestion);
        questRepository.delete(testQuest);
    }
}