package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.entity.Game;
import com.javarush.kovalinsky.entity.Question;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.GameService;
import com.javarush.kovalinsky.service.QuestionService;
import com.javarush.kovalinsky.util.RequestHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
//        Long questId = Long.parseLong(req.getParameter("questId"));
        Long questId = 1L; //test TODO change
        HttpSession session = req.getSession();
        Optional<User> user = RequestHelper.getUser(req.getSession());
        if (user.isPresent()) {
            Long userId = user.get().getId();
            Optional<Game> game = gameService.getGame(questId, userId);
            if (game.isPresent()) {
                showNextQuestion(req, game.get());
                return getJspPage();
            } else {
                session.setAttribute("errorMessage", "Нет незавершённой игры");
                return "/home";
            }
        } else {
            session.setAttribute("errorMessage", "Сначала нужно войти в аккаунт");
            return "/login";
        }
    }

    @Override
    public String doPost(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long gameId = RequestHelper.getId(req);
        Long answerId = RequestHelper.getId(req, "answer");
        Optional<Game> gameOptional = gameService.processOneStep(gameId, answerId);
        if (gameOptional.isPresent()) {
            if (answerId == 0 && req.getParameter("new-game") == null) {
                session.setAttribute("errorMessage", "Необходимо выбрать ответ");
            }
            Game game = gameOptional.get();
            return "%s?questId=%d&id=%d".formatted("/play-game", game.getQuestId(), game.getId());
        } else {
            session.setAttribute("errorMessage", "Такой игры нет");
            return "/home";
        }
    }

    private void showNextQuestion(HttpServletRequest req, Game game) {
        req.setAttribute("game", game);
        Optional<Question> question = questionService.get(game.getCurrentQuestionId());
        req.setAttribute("question", question.orElseThrow());
    }
}
