package com.javarush.kovalinsky.service;

import com.javarush.kovalinsky.entity.*;
import com.javarush.kovalinsky.exception.AppException;
import com.javarush.kovalinsky.repository.*;
import com.javarush.kovalinsky.util.Err;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestService {

    public static final String QUEST_SYMBOL = ":";
    public static final String WIN_SYMBOL = "+";
    public static final String LOST_SYMBOL = "-";
    public static final String LINK_SYMBOL = "<";
    public static final String DIGITS = "\\d+";

    private final Repository<User> userRepository;
    private final Repository<Quest> questRepository;
    private final Repository<Question> questionRepository;
    private final Repository<Answer> answerRepository;

    public QuestService(UserRepository userRepository, QuestRepository questRepository,
                        QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.userRepository = userRepository;
        this.questRepository = questRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Collection<Quest> getAll() {
        return questRepository.getAll();
    }

    public Optional<Quest> get(long id) {
        return Optional.ofNullable(questRepository.get(id));
    }

    public Optional<Quest> create(String name, String text, Long userId) {
        Map<Long, Question> map = fillDraftMap(text);
        if (map.isEmpty()) {
            return Optional.empty();
        }
        Quest quest = Quest.builder()
                .authorId(userId)
                .name(name)
                .text(text)
                .startQuestionId(0L)
                .build();
        questRepository.create(quest);
        User user = userRepository.get(userId);
        Collection<Quest> quests = user.getQuests();
        quests.add(quest);
        questRepository.update(quest);
        for (Question question : map.values()) {
            question.setQuestId(quest.getId());
            questionRepository.create(question);
        }

        Long startKey = findStartQuestionLabel(text);
        Long startId = map.get(startKey).getId();
        quest.setStartQuestionId(startId);

        updateLinksAndId(map, quest);
        map.values().stream()
                .flatMap(q -> q.getAnswers().stream())
                .forEach(answerRepository::create);
        return Optional.of(quest);
    }

    private Long findStartQuestionLabel(String text) {
        Matcher matcher = Pattern.compile(DIGITS).matcher(text);
        if (matcher.find()) {
            return Long.parseLong(matcher.group());
        }
        throw new AppException(Err.START_INDEX_NOT_FOUND);
    }

    private Map<Long, Question> fillDraftMap(String text) {
        Map<Long, Question> map = new TreeMap<>();
        text = "\n" + text;
        String pattern = "\n(%s)([:<+-])".formatted(DIGITS);
        String[] parts = text.split(pattern);
        int index = 1;
        Matcher labelIterator = Pattern.compile(pattern).matcher(text);
        Question question = new Question();
        while (labelIterator.find()) {
            long key = Long.parseLong(labelIterator.group(1));
            String type = labelIterator.group(2);
            String textPart = parts[index++].strip();
            Optional<Question> newQuestion = fillQuestion(question, key, type, textPart);
            if (newQuestion.isPresent()) {
                question = newQuestion.get();
                map.put(key, question);
            }
        }
        return map;
    }

    private Optional<Question> fillQuestion(Question currentQuestion, long key, String type, String textPart) {
        currentQuestion = switch (type) {
            case QUEST_SYMBOL -> Question.builder().text(textPart).gameState(GameState.PLAY).build();
            case WIN_SYMBOL -> Question.builder().text(textPart).gameState(GameState.WIN).build();
            case LOST_SYMBOL -> Question.builder().text(textPart).gameState(GameState.LOST).build();
            case LINK_SYMBOL -> {
                Answer built = Answer.builder()
                        .nextQuestionId(key)
                        .questionId(0L)
                        .text(textPart)
                        .build();
                currentQuestion.getAnswers().add(built);
                yield null;
            }
            default -> throw new AppException(Err.INCORRECT_PARSING);
        };
        return Optional.ofNullable(currentQuestion);
    }

    private void updateLinksAndId(Map<Long, Question> map, Quest quest) {
        for (Question question : map.values()) {
            question.setId(quest.getId());
            quest.getQuestions().add(question);
            for (Answer answer : question.getAnswers()) {
                answer.setQuestionId(question.getId());
                Long key = answer.getNextQuestionId();
                if (map.containsKey(key)) {
                    answer.setNextQuestionId(map.get(key).getId());
                }
            }
        }
    }
}
