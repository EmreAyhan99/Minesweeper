package game;

import game.controller.Difficulty;
import game.controller.Game;
import game.model.BoardModel;
//import game.controller.Controller.Difficulty;
import game.view.BoardView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage)
    {
        this.stage = stage;
        Game game = new Game(this.stage);
        game.initEasyGame();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }

}