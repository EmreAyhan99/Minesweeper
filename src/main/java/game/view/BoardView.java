package game.view;

import game.controller.Controller;
import game.controller.Difficulty;
import game.controller.Game;
import game.model.BoardModel;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BoardView extends Pane
{
    private CellView[][] cells;
    private Label timeLabel;
    private MenuView menuView;
    private BorderPane borderPane;

    /**
     * Boardmodel only exists here to help create the controller, in respect of the MVC pattern
     * Boardview visualizes the boardmodel
     * @param boardModel
     * @param difficulty
     */


    public BoardView(BoardModel boardModel, Difficulty difficulty) {
        Controller controller = new Controller(boardModel,this);  // jo det är okej anders gjorde så också. använder inte modellen i vyn skapar bara en controller för att använda
        int width = 0;
        if(difficulty == Difficulty.HARD)
            width = 16;
        if(difficulty == Difficulty.EASY)
            width = 9;

        this.cells = new CellView[width][width];
        this.menuView = new MenuView(controller);
        timeLabel= new Label("timer");
        timeLabel.setLayoutY(100);
        timeLabel.setLayoutX((width*51) + 20);
        timeLabel.setFont(new Font("Arial", 30));
        for (int i = 0; i <width; i++)
        {
            for (int j = 0; j < width; j++)
            {
                this.cells[i][j] = new CellView(controller,i,j);
                this.getChildren().add(cells[i][j]);
            }
        }

        timeLabel.setTextFill(Color.DARKRED);
        this.getChildren().add(timeLabel);
        this.getChildren().add(menuView);

    }

    /**
     * Sets the menu on top of screen and game below the menu
     * @return
     */

    public BorderPane setBorderPane(){
        borderPane = new BorderPane();
        borderPane.setTop(menuView);
        borderPane.setCenter(this);

        return borderPane;
    }


    public CellView getCells(int x, int y) {
        return cells[x][y];
    }

    /**
     * Shows the current time in game
     * @param time
     */

    public void showTimeLabel(long time)
    {
        timeLabel.setText(Long.toString(time));
    }




}
