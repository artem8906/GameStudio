package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.kamene.core.Field;
import sk.tuke.gamestudio.kamene.core.GameState;
import sk.tuke.gamestudio.kamene.core.Tile;
import sk.tuke.gamestudio.service.GameStudioException;
import sk.tuke.gamestudio.service.ScoreServiceJPA;
import java.util.Date;

@Controller
@RequestMapping("/kamene")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class KameneController {
    @Autowired
    ScoreServiceJPA scoreServiceJPA;

    private Field field = new Field(4);

    private boolean isAddScore = false;


    @RequestMapping("/new")
    public String newGame() {
        field = new Field(4);
        return "kamene";
    }


    @RequestMapping
    public String kamene(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model) {
        if (row != null && column != null) {
//            field.move();
        }
        prepareModel(model);
        if (field.getGameState().equals(GameState.SOLVED) && (! isAddScore) ) {
            scoreServiceJPA.addScore(new Score("Kamene", "Anonym", field.getScore(), new Date()));
            isAddScore=true;
        }
        return "kamene";
    }


    public String getFieldAsHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");

        for (int row = 0; row < 4; row++) {
            sb.append("<table class='minefield'>\n");

            for (int col = 0; col < 4; col++) {
                Tile tile = field.getTile(row, col);
//                sb.append("<td class='" + getTileClass(tile) + "'> ");
                    sb.append("<a href='/kamene/?row=" + row + "&column=" + col + "'> ");
                    sb.append("<span>" + tile.getValue() + "</span>");
                    sb.append(" </a>\n");
                    sb.append(" </td>\n");
                }
                sb.append("</tr>\n");
            }
            sb.append("</table>");


        return sb.toString();
    }



    public void prepareModel(Model model) {
//        model.addAttribute("gameState", field.getState());
//        model.addAttribute("minesweeperField", field.getTiles());
        model.addAttribute("bestScores", scoreServiceJPA.getBestScores("Kamene"));
        model.addAttribute("currentScore", field.getScore());
    }


}
