package com.javarush.kovalinsky.lesson14.entity;

import com.javarush.kovalinsky.lesson14.repository.QuestionRepository;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class SpaceQuest {

    private final String START_MESSAGE = """
            <legend>
                Ты стоишь в космическом порту и готов подняться на борт своего корабля.
                Разве ты не об этом мечтал? Стать капитаном галактического судна с экипажем,
                который будет совершать подвиги под твоим командованием.
                Так что вперёд!
            </legend>
                        
            <h2>Знакомство с экипажем</h2>
            <legend>
                Когда ты поднялся на борт корабля, тебя поприветствовала девушка с чёрной палкой в руках:
                - Здравствуйте, командир! Я Зинаида - ваша помощница. Видите? Там в углу пьёт кофе
                наш штурман - сержант Перегарный Шлейф, под штурвалом спит наш бортмеханик - Чёрный Богдан,
                а фотографирует его Сергей Стальная Пятка - наш навигатор.
                А как обращаться к вам?
            </legend>
            <br/>
            """;
    public List<Question> questions;
    private int startQuestionId = 1;

    public SpaceQuest(QuestionRepository questionRepository) {
        questions = questionRepository.getAll().stream().toList();
    }

}
