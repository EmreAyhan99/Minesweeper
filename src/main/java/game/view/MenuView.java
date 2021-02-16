package game.view;

import game.App;
import game.controller.Controller;
import game.controller.Game;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuView extends BorderPane {

    private Controller controller;


    public MenuView(Controller controller){
        Game game = new Game(App.getStage());
        this.controller = controller;

        //filemenu
        Menu fileMenu = new Menu("Files");
        Menu helpMenu = new Menu("_Help");
        Menu restartMenu = new Menu("_New game");

        //menu items
        MenuItem highscore = new MenuItem("Highscore");
        highscore.setOnAction(e -> {
            var a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Highscores");
            a.setContentText(controller.getHighScore().toString());
            a.showAndWait();
            System.out.println("Highscore");
        } );

        fileMenu.getItems().addAll(highscore);


        MenuItem info = new MenuItem("Rules");
        info.setOnAction(e->{
            var a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Rules");
            a.setContentText("In Minesweeper, mines are scattered throughout a board which is divided into cells. Cells have three states: uncovered, covered and flagged. A covered cell is blank and clickable, while an uncovered cell is exposed. Flagged cells are those marked by the player to indicate a potential mine location.\n" +
                    "\n" +
                    "A player left-clicks a cell to uncover it. If a player uncovers a mined cell, the game ends. Otherwise, the uncovered cells displays either a number, indicating the quantity of mines adjacent to it, or a blank tile (or \"0\"), and all adjacent non-mined cells will automatically be uncovered. Right-clicking on a cell will flag it, causing a flag to appear on it. Flagged cells are still covered, and a player can click on them to uncover them, although typically they must first be unflagged with an additional right-click.\n" +
                    "\n" +
                    "The first click in any game will never be a mine.\n" +
                    "\n" +
                    "To win the game, players must uncover all non-mine cells, at which point the timer is stopped. Flagging all the mined cells is not required.");
            a.showAndWait();
        });
        helpMenu.getItems().add(info);

        MenuItem hardGame = new MenuItem("Hard");
        hardGame.setOnAction(event -> {
            game.initHardGame();
        });
        MenuItem easyGame = new MenuItem("Easy");
        easyGame.setOnAction(event -> {
            game.initEasyGame();
        });

        restartMenu.getItems().addAll(easyGame,hardGame);

        //menubar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(restartMenu,fileMenu,helpMenu);

        this.setTop(menuBar);

    }

}
