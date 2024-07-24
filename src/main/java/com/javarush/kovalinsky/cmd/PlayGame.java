package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.dto.GameTo;
import com.javarush.kovalinsky.dto.UserTo;
import com.javarush.kovalinsky.dto.QuestionTo;
import com.javarush.kovalinsky.service.GameService;
import com.javarush.kovalinsky.service.QuestionService;
import com.javarush.kovalinsky.util.Err;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import com.javarush.kovalinsky.util.RequestHelper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class PlayGame implements Command {

    private final GameService gameService;
    private final QuestionService questionService;

    public PlayGame(GameService gameService, QuestionService questionService) {
        this.gameService = gameService;
        this.questionService = questionService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        Long questId = Long.parseLong(req.getParameter(Key.QUEST_ID));
        Optional<UserTo> user = RequestHelper.getUser(req.getSession());
        if (user.isPresent()) {
            Long userId = user.get().getId();
            Optional<GameTo> game = gameService.getGame(questId, userId);
            if (game.isPresent()) {
                showNextQuestion(req, game.get());
                return getJspPage();
            } else {
                RequestHelper.setError(req, Err.NO_UNFINISHED_GAME);
                return Go.HOME;
            }
        } else {
            RequestHelper.setError(req, Err.NEED_TO_LOG_IN);
            return Go.LOGIN;
        }
    }

    @Override
    public String doPost(HttpServletRequest req) {
        Long gameId = RequestHelper.getId(req);
        Long answerId = RequestHelper.getId(req, Key.ANSWER);
        Optional<GameTo> gameOptional = gameService.processOneStep(gameId, answerId);
        if (gameOptional.isPresent()) {
            if (answerId == 0 && req.getParameter(Key.NEW_GAME) == null) {
                RequestHelper.setError(req, Err.NO_ANSWER_HAS_BEEN_SELECTED);
            }
            GameTo game = gameOptional.get();
            return "%s?questId=%d&id=%d".formatted(Go.PLAY_GAME, game.getQuestId(), game.getId());
        } else {
            RequestHelper.setError(req, Err.NO_SUCH_GAME);
            return Go.HOME;
        }
    }

    private void showNextQuestion(HttpServletRequest req, GameTo gameTo) {
        req.setAttribute(Key.GAME, gameTo);
        Optional<QuestionTo> question = questionService.get(gameTo.getCurrentQuestionId());
        req.setAttribute(Key.QUESTION, question.orElseThrow());
    }
}
