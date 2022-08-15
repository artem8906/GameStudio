package sk.tuke.gamestudio.kamene.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.kamene.core.Field;
import sk.tuke.gamestudio.service.ScoreService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConsoleUI {

    private Field field;
    private String name;
    private final String game = "Kamene";
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    @Autowired
    ScoreService scoreService;

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void play() {
        this.field = new Field(4);
        System.out.println("Input your name");
        name = readLine();
        field.generate();


        do {
            update();
            processInput();
            field.savefield();

            if (field.isSolved()) {
                int timeOfPlay = (int) (System.currentTimeMillis() - field.getStartTime())/1000000;
                int score = field.getSize() * 1000 - timeOfPlay;
                System.out.println("You win. Your score is "+ score);
//                scoreService.addScore(new Score(game, name, score, new Date()));
                System.out.println(scoreService.getBestScores(game));
                System.exit(1);
            }
        } while (true);
    }



    public void update() {
        field.updateState();
        for (int i = 0; i < field.getTiles().length; i++) {
            for (int j = 0; j < field.getTiles().length; j++) {
                System.out.print(field.getTiles()[i][j]);
            }
            System.out.println();
        }
    }

    private void processInput() {
        System.out.println("Press 'w', 's', 'a', 'd' for moving tiles, 'new' for new game or 'exit' for exit");
        String line = readLine();

        try {
            Input(line);
        } catch (WrongUserInputException e) {
            System.out.println("Try again!");
            processInput();
        }
    }

    private void Input(String input) throws WrongUserInputException {
        String action = input;
        switch (action) {
            case "w":
                field.move("UP");
                break;

            case "s":
                field.move("DOWN");
                break;

            case "a":
                field.move("LEFT");
                break;

            case "d":
                field.move("RIGHT");
                break;

            case "new":
                play();
                break;
            case "exit":
                System.exit(1);
                break;

            default:
                throw new WrongUserInputException();
        }
    }
}


