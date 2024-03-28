import com.javarush.kovalinsky.lesson14.entity.Question;
import com.javarush.kovalinsky.lesson14.repository.AnswerRepository;
import com.javarush.kovalinsky.lesson14.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionRepositoryTest {

    private static QuestionRepository testQuestionRepository;

    @BeforeEach
    void setUpEach() {
        testQuestionRepository = Mockito.mock(QuestionRepository.class);
    }

    @Test
    void whenCreateQuestionRepository_returnCorrectRepository() {
        Map<Long, Question> testRepositoryMap = new HashMap<>();
        testRepositoryMap.put(1L, new Question(1L, "Ты потерял память. Принять вызов НЛО?", Collections.emptyList()));
        testRepositoryMap.put(-1L, new Question(-1L, "Ты отклонил вызов. Поражение", Collections.emptyList()));
        testRepositoryMap.put(2L, new Question(2L, "Ты принял вызов. Поднимешься на мостик к капитану?", Collections.emptyList()));
        testRepositoryMap.put(-2L, new Question(-2L, "Ты не пошёл на переговоры. Поражение", Collections.emptyList()));
        testRepositoryMap.put(3L, new Question(3L, "Ты поднялся на мостик. Кто ты?", Collections.emptyList()));
        testRepositoryMap.put(-3L, new Question(-3L, "Твою ложь разоблачили. Поражение", Collections.emptyList()));
        testRepositoryMap.put(4L, new Question(4L, "Тебя вернули домой. Победа!", Collections.emptyList()));
        Collection<Question> expectedRepositoryValues = testRepositoryMap.values();
        Mockito.doReturn(expectedRepositoryValues).when(testQuestionRepository).getAll();

        Collection<Question> actualRepositoryValues = testQuestionRepository.getAll();
        assertEquals(expectedRepositoryValues, actualRepositoryValues);
    }

    @Test
    void whenCreateQuestionRepository_invokeGetAnswersByQuestionIdOnAnswerRepository() {
        AnswerRepository testAnswerRepository = Mockito.mock(AnswerRepository.class);
        testQuestionRepository = new QuestionRepository(testAnswerRepository);
        long testQuestionId = 2L;

        Mockito.verify(testAnswerRepository).getAnswersByQuestionId(testQuestionId);
    }

    @Test
    void whenGetAllQuestions_returnCorrectMapValues() {
        Map<Long, Question> testMap = new HashMap<>();
        testMap.put(-2L, new Question(-2L, "Ты не пошёл на переговоры. Поражение", Collections.emptyList()));
        testMap.put(3L, new Question(3L, "Ты поднялся на мостик. Кто ты?", Collections.emptyList()));
        Collection<Question> expectedValues = testMap.values();
        Mockito.doReturn(expectedValues).when(testQuestionRepository).getAll();

        Collection<Question> actualValues = testQuestionRepository.getAll();
        assertEquals(expectedValues, actualValues);
    }
}
