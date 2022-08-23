package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.minesweeper.core.Clue;
import sk.tuke.gamestudio.minesweeper.core.Field;
import sk.tuke.gamestudio.minesweeper.core.GameState;
import sk.tuke.gamestudio.minesweeper.core.Tile;
import sk.tuke.gamestudio.service.GameStudioException;
import sk.tuke.gamestudio.service.ScoreServiceJPA;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/minesweeper")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesweeperController {

    @Autowired
    UserController userController;

    @Autowired
    ScoreServiceJPA scoreServiceJPA;

    private Field field = new Field(9, 9, 10);

    private boolean marking = false;
    private boolean isAddScore = false;


    public boolean getMarking() {
        return marking;
    }

    @RequestMapping("/new")
    public String newGame() {
        field = new Field(9, 9, 10);
        return "minesweeper";
    }


    @RequestMapping
    public String minesweeper(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model) {
        if (row != null && column != null && field.getState().equals(GameState.PLAYING)) {
            if (marking) {
                field.markTile(row, column);
            } else {
                field.openTile(row, column);
            }
        }


        prepareModel(model);
        if (field.getState().equals(GameState.SOLVED) && (! isAddScore) && userController.isLogged()) {
            scoreServiceJPA.addScore(new Score("MinesSweeper", "Anonym", field.getScore(), new Date()));
            isAddScore=true;
        }
        return "minesweeper";
    }

    @RequestMapping("/mark")
    public String changeMarking() {
        marking = !marking;
        return "minesweeper";
    }


    public String getFieldAsHtml() {
        int rowCount = field.getRowCount();
        int columnCount = field.getColumnCount();
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");

        for (int row = 0; row < rowCount; row++) {
            sb.append("<table class='minefield'>\n");

            for (int col = 0; col < columnCount; col++) {
                Tile tile = field.getTile(row, col);
                sb.append("<td class='" + getTileClass(tile) + "'> ");
                sb.append("<a href='/minesweeper/?row=" + row + "&column=" + col + "'> ");
                sb.append("<span>" + getTileText(tile) + "</span>");
                sb.append(" </a>\n");
                sb.append(" </td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>");
        return sb.toString();
    }

    public String getTileText(Tile tile) {
        switch (tile.getState()) {
            case CLOSED:
                return "-";
            case MARKED:
                return "M";
            case OPEN:
                if (tile instanceof Clue) {
                    return String.valueOf(((Clue) tile).getValue());
                } else {
                    return "X";
                }
            default:
                throw new GameStudioException("Unsupported tile state " + tile.getState());
        }
    }

    public String getTileClass(Tile tile) {
        switch (tile.getState()) {
            case OPEN:
                if (tile instanceof Clue)
                    return "open" + ((Clue) tile).getValue();
                else
                    return "mine";
            case CLOSED:
                return "closed";
            case MARKED:
                return "marked";
            default:
                throw new RuntimeException("Unexpected tile state");
        }
    }

    public void prepareModel(Model model) {

        model.addAttribute("gameState", field.getState());
        model.addAttribute("minesweeperField", field.getTiles());
        model.addAttribute("bestScores", scoreServiceJPA.getBestScores("MinesSweeper"));
        model.addAttribute("currentScore", field.getScore());

    }


}
