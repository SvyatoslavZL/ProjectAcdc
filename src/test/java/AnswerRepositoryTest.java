import com.javarush.kovalinsky.lesson14.entity.Answer;
import com.javarush.kovalinsky.lesson14.repository.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnswerRepositoryTest {

    private static AnswerRepository testAnswerRepository;

    @BeforeEach
    void setUpEach() {
        testAnswerRepository = Mockito.mock(AnswerRepository.class);
    }

    @Test
    void whenCreateAnswerRepository_returnCorrectRepository() {
        Map<Long, Answer> testMap = new HashMap<>();
        testMap.put(1L, new Answer(1L, 1L, "Принять вызов", 2L));
        testMap.put(2L, new Answer(2L, 1L, "Отклонить вызов", -1L));
        testMap.put(3L, new Answer(3L, 2L, "Подняться на мостик", 3L));
        testMap.put(4L, new Answer(4L, 2L, "Отказаться подниматься на мостик", -2L));
        testMap.put(5L, new Answer(5L, 3L, "Рассказать о себе правду", 4L));
        testMap.put(6L, new Answer(6L, 3L, "Солгать о себе", -3L));
        Collection<Answer> expectedRepositoryValues = testMap.values();
        Mockito.doReturn(expectedRepositoryValues).when(testAnswerRepository).getAll();

        Collection<Answer> actualRepositoryValues = testAnswerRepository.getAll();
        assertEquals(expectedRepositoryValues, actualRepositoryValues);
    }

    @Test
    void whenGetAllAnswers_returnCorrectMapValues() {
        Map<Long, Answer> testMap = new HashMap<>();
        testMap.put(3L, new Answer(3L, 2L, "Подняться на мостик", 3L));
        testMap.put(6L, new Answer(6L, 3L, "Солгать о себе", -3L));
        Collection<Answer> expectedValues = testMap.values();
        Mockito.doReturn(expectedValues).when(testAnswerRepository).getAll();

        Collection<Answer> actualValues = testAnswerRepository.getAll();
        assertEquals(expectedValues, actualValues);
    }

    @Test
    void whenGetAnswersByQuestionId_returnCorrectAnswers() {
        //given
        Map<Long, Answer> testMap = new HashMap<>();
        testMap.put(1L, new Answer(1L, 1L, "Принять вызов", 2L));
        testMap.put(2L, new Answer(2L, 1L, "Отклонить вызов", -1L));
        testMap.put(5L, new Answer(5L, 3L, "Рассказать о себе правду", 4L));
        testMap.put(6L, new Answer(6L, 3L, "Солгать о себе", -3L));
        testMap.put(7L, new Answer(7L, 4L, "testText", -3L));
        Collection<Answer> expectedAnswersToQuestionWith3Id = new ArrayList<>();
        expectedAnswersToQuestionWith3Id.add(testMap.get(5L));
        expectedAnswersToQuestionWith3Id.add(testMap.get(6L));
        Mockito.doReturn(expectedAnswersToQuestionWith3Id).when(testAnswerRepository).getAnswersByQuestionId(3L);
        //when
        Collection<Answer> actualAnswersToQuestionWith3Id = testAnswerRepository.getAnswersByQuestionId(3L);
        //then
        assertEquals(expectedAnswersToQuestionWith3Id, actualAnswersToQuestionWith3Id);
    }
}
