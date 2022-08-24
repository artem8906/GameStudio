package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.kamene.core.Field;
import sk.tuke.gamestudio.kamene.core.GameState;
import sk.tuke.gamestudio.kamene.core.Tile;
import sk.tuke.gamestudio.service.*;

import java.util.Date;

@Controller
@RequestMapping("/kamene")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class KameneController {

    private final String NAME_GAME = "Kamene";
    @Autowired
    UserController userController;
    @Autowired
    ScoreService scoreService;

    @Autowired
    CommentService commentService;

    @Autowired
    RatingService ratingService;

    private Field field = new Field(4);

    private boolean isAddScore = false;


    @RequestMapping("/new")
    public String newGame(Model model) {
        field = new Field(4);
        prepareModel(model);
        return "kamene";
    }


    @RequestMapping
    public String kamene(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model) {
        if (row != null && column != null) {
//            field.move();
        }
        prepareModel(model);
        if (field.getGameState().equals(GameState.SOLVED) && (! isAddScore) ) {
            scoreService.addScore(new Score("Kamene", "Anonym", field.getScore(), new Date()));
            isAddScore=true;
        }
        return "kamene";
    }

    @RequestMapping("/comment")
    public String comment(String comment, Model model) {
        if(userController.isLogged()) {
            Comment newComment = new Comment(NAME_GAME,userController.getLoggedUser(),comment,new Date());
            commentService.addComment(newComment);
        } else {
            Comment newComment = new Comment(NAME_GAME,"Anonym",comment,new Date());
            commentService.addComment(newComment);
        }
        prepareModel(model);
        return "kamene";
    }

    @RequestMapping("/rating")
    public String rating(int rating, Model model) {
        Rating newRating;
        if (userController.isLogged()) {
            newRating = new Rating(NAME_GAME, userController.getLoggedUser(), rating, new Date());
            ratingService.setRating(newRating);
        } else {
            newRating = new Rating(NAME_GAME, "Anonym", rating, new Date());
            ratingService.setRating(newRating);
        }
        prepareModel(model);
        return "kamene";
    }


    public String getTileText(Tile tile) {
        if (tile.getValue()==0) return "";
        else
        return String.valueOf(tile.getValue());
    }

    @RequestMapping("/up")
    public String moveUp(Model model){
        prepareModel(model);
        field.move("UP");
        return "kamene";
    }

    @RequestMapping("/down")
    public String moveDown(Model model){
        prepareModel(model);
        field.move("DOWN");
        return "kamene";
    }

    @RequestMapping("/right")
    public String moveRight(Model model){
        field.move("RIGHT");
        prepareModel(model);
        return "kamene";
    }

    @RequestMapping("/left")
    public String moveLeft(Model model){
        prepareModel(model);
        field.move("LEFT");
        return "kamene";
    }


    public void prepareModel(Model model) {
        model.addAttribute("kameneField", field.getTiles());
        model.addAttribute("comments", commentService.getComments(NAME_GAME));
        model.addAttribute("avgRating", ratingService.getAverageRating(NAME_GAME));
        model.addAttribute("bestScores", scoreService.getBestScores(NAME_GAME));


    }

}
