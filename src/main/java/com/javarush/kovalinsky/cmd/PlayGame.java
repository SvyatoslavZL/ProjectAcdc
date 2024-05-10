package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.entity.Game;
import com.javarush.kovalinsky.entity.Question;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.GameService;
import com.javarush.kovalinsky.service.QuestionService;
import com.javarush.kovalinsky.util.Err;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import com.javarush.kovalinsky.util.RequestHelper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

@SuppressWarnings("unused")
public class PlayGame implements Command {

    private final GameService gameService;
    private final QuestionService questionService;

    public PlayGame(GameService gameService, QuestionService questService) {
        this.gameService = gameService;
        this.questionService = questService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        Long questId = Long.parseLong(req.getParameter(Key.QUEST_ID));
        Optional<User> user = RequestHelper.getUser(req.getSession());
        if (user.isPresent()) {
            Long userId = user.get().getId();
            Optional<Game> game = gameService.getGame(questId, userId);
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
        Optional<Game> gameOptional = gameService.processOneStep(gameId, answerId);
        if (gameOptional.isPresent()) {
            if (answerId == 0 && req.getParameter(Key.NEW_GAME) == null) {
                RequestHelper.setError(req, Err.NEED_TO_SELECT_ANSWER);
            }
            Game game = gameOptional.get();
            return "%s?questId=%d&id=%d".formatted(Go.PLAY_GAME, game.getQuestId(), game.getId());
        } else {
            RequestHelper.setError(req, Err.NO_SUCH_GAME);
            return Go.HOME;
        }
    }

    private void showNextQuestion(HttpServletRequest req, Game game) {
        req.setAttribute(Key.GAME, game);
        Optional<Question> question = questionService.get(game.getCurrentQuestionId());
        req.setAttribute(Key.QUESTION, question.orElseThrow());
    }
}
