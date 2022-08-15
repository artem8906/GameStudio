package sk.tuke.gamestudio.kamene;

import sk.tuke.gamestudio.kamene.consoleui.ConsoleUI;

public class Kamene {

    private static Kamene instance;

    public static Kamene getInstance() {
        if (instance==null) instance = new Kamene();
        return instance;
    }

    private Kamene() {

        instance = this;
        final ConsoleUI userInterface = new ConsoleUI();
        userInterface.play();
    }

    public static void main(String[] args) {
        getInstance();
    }
}

