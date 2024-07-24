package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.ContainerIT;
import com.javarush.kovalinsky.config.NanoSpring;
import com.javarush.kovalinsky.config.SessionCreator;
import com.javarush.kovalinsky.dto.GameState;
import com.javarush.kovalinsky.dto.Role;
import com.javarush.kovalinsky.entity.Quest;
import com.javarush.kovalinsky.entity.Question;
import com.javarush.kovalinsky.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionRepositoryTest extends ContainerIT {

    private final SessionCreator sessionCreator = NanoSpring.find(SessionCreator.class);
    private final QuestRepository questRepository = new QuestRepository(sessionCreator);
    private final QuestionRepository questionRepository = new QuestionRepository(sessionCreator);
    private Quest testQuest;
    private Question testQuestion;
    private User testUser;

    @BeforeEach
    void createQuestAndQuestion() {
        sessionCreator.beginTransactional();
        testUser = User.builder()
                .id(1L)
                .login("testLogin")
                .password("testPassword")
                .role(Role.ADMIN)
                .build();
        testQuest = Quest.builder()
                .name("testQuest")
                .text("testText")
                .build();
        testQuest.setAuthor(testUser);
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
        sessionCreator.endTransactional();
    }
}