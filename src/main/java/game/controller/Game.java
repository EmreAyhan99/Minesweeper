package game.controller;

import game.model.BoardModel;
import game.view.BoardView;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Initiates hard or easy game for the player
 */

public class Game {
    Stage stage;

    /**
     * Constructor makes sure same stage is used for new games, creates instance of game
     * @param stage
     */

    public Game(Stage stage){
        this.stage = stage;
    }


    /**
     * Returns stage
     * @return
     */

    public Stage getStage() {
        return stage;
    }

    /**
     * Creates easy game with 9*9 cells
     */

    public void initEasyGame(){
        Difficulty difficulty = Difficulty.EASY;
        BoardModel boardModel = new BoardModel(difficulty);
        BoardView boardView = new BoardView(boardModel, difficulty);
        int width = boardModel.getWidth();

        var scene = new Scene(boardView.setBorderPane(), (60*width), (54*width));
        getStage().setScene(scene);
        getStage().show();
    }

    /**
     * Creates hard game with 16*16 cells
     */

    public void initHardGame(){
        Difficulty difficulty = Difficulty.HARD;
        BoardModel boardModel = new BoardModel(difficulty);
        BoardView boardView = new BoardView(boardModel, difficulty);
        int width = boardModel.getWidth();

        var scene = new Scene(boardView.setBorderPane(), (60*width), (54*width));
        getStage().setScene(scene);
        getStage().show();
    }
}