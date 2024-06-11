package com.javarush.khmelov.cmd;

import com.javarush.khmelov.dto.GameTo;
import com.javarush.khmelov.dto.UserTo;
import com.javarush.khmelov.dto.QuestionTo;
import com.javarush.khmelov.service.GameService;
import com.javarush.khmelov.service.QuestionService;
import com.javarush.khmelov.util.Go;
import com.javarush.khmelov.util.Key;
import com.javarush.khmelov.util.RequestHelper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

@SuppressWarnings("unused")
public class PlayGame implements Command {

    private final GameService gameService;
    private final QuestionService questionService;

    public PlayGame(GameService gameService, QuestionService questionService) {
        this.gameService = gameService;
        this.questionService = questionService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        Long questId = Long.parseLong(request.getParameter(Key.QUEST_ID));
        Optional<UserTo> user = RequestHelper.getUser(request.getSession());
        if (user.isPresent()) {
            Long userId = user.get().getId();
            Optional<GameTo> game = gameService.getGame(questId, userId);
            if (game.isPresent()) {
                showOneQuestion(request, game.get());
                return getJspPage();
            } else {
                RequestHelper.setError(request, "Нет незавершенной игры");
                return Go.HOME;
            }
        } else {
            RequestHelper.setError(request, "Сначала нужно войти в аккаунт");
            return Go.LOGIN;
        }
    }

    @Override
    public String doPost(HttpServletRequest request) {
        Long gameId = RequestHelper.getId(request);
        Long answerId = RequestHelper.getId(request, Key.ANSWER);
        Optional<GameTo> gameOptional = gameService.processOneStep(gameId, answerId);
        if (gameOptional.isPresent()) {
            if (answerId == 0 && request.getParameter("new-game") == null) {
                RequestHelper.setError(request, "Нужно выбрать какой-то ответ");
            }
            GameTo game = gameOptional.get();
            return "%s?questId=%d&id=%d".formatted(Go.PLAY_GAME, game.getQuestId(), game.getId());
        } else {
            RequestHelper.setError(request, "Нет такой игры");
            return Go.HOME;
        }
    }

    private void showOneQuestion(HttpServletRequest request, GameTo gameTo) {
        request.setAttribute(Key.GAME, gameTo);
        Optional<QuestionTo> question = questionService.get(gameTo.getCurrentQuestionId());
        request.setAttribute(Key.QUESTION, question.orElseThrow());
    }

}
