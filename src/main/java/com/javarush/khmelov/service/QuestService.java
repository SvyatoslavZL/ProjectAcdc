package com.javarush.khmelov.service;

import com.javarush.khmelov.dto.GameState;
import com.javarush.khmelov.dto.QuestTo;
import com.javarush.khmelov.entity.*;
import com.javarush.khmelov.exception.AppException;
import com.javarush.khmelov.mapping.Dto;
import com.javarush.khmelov.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Transactional
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



    public Collection<Quest> getAll() {
        return questRepository.getAll();
    }

    public Optional<QuestTo> get(long id) {
        return Optional.ofNullable(questRepository.get(id)).map(Dto.MAPPER::from);
    }

    public Optional<Quest> create(String name, String text, Long userId) {
        Map<Long, Question> map = fillDraftMap(text);
        if (map.isEmpty()) {
            return Optional.empty();
        }
        Quest quest = Quest.builder()
                .author(userRepository.get(userId))
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
        throw new AppException("not found start index in text");
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
            String partText = parts[index++].strip();
            Optional<Question> newQuestion = fillQuestion(question, key, type, partText);
            if (newQuestion.isPresent()) {
                question = newQuestion.get();
                map.put(key, question);
            }
        }
        return map;
    }

    private Optional<Question> fillQuestion(Question currentQuestion, long key, String type, String partText) {
        currentQuestion = switch (type) {
            case QUEST_SYMBOL -> Question.builder().text(partText).gameState(GameState.PLAY).build();
            case WIN_SYMBOL -> Question.builder().text(partText).gameState(GameState.WIN).build();
            case LOST_SYMBOL -> Question.builder().text(partText).gameState(GameState.LOST).build();
            case LINK_SYMBOL -> {
                Answer build = Answer.builder()
                        .nextQuestionId(key)
                        .questionId(0L)
                        .text(partText)
                        .build();
                currentQuestion.getAnswers().add(build);
                yield null;
            }
            default -> throw new AppException("incorrect parsing");
        };
        return Optional.ofNullable(currentQuestion);
    }

    private void updateLinksAndId(Map<Long, Question> map, Quest quest) {
        for (Question question : map.values()) {
            question.setQuestId(quest.getId());
            quest.getQuestions().add(question);
            for (Answer answer : question.getAnswers()) {
                answer.setQuestionId(question.getId());
                Long key = answer.getNextQuestionId(); //label (index in text)
                if (map.containsKey(key)) {
                    answer.setNextQuestionId(map.get(key).getId()); //real index
                }
            }
        }
    }


}
