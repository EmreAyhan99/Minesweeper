
package game.controller;

import game.model.BoardModel;
import game.model.CellModel;
import game.view.BoardView;
import game.view.CellView;
import game.view.HighScore;
import game.view.HighScoreIO;
import javafx.application.Platform;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 *Controller acts as the middle hand between view and model
 */
public class Controller
{
    private BoardModel boardModel;
    private BoardView boardView;
    private  CellModel[][] grid;
    private boolean gameOver = false;
    private HighScore highScore;

    /**
     * Creates instance of controller that has access to boardmodel and boardview
     * Controller also has the timer and file management
     * @param boardModel
     * @param boardView
     */

    public Controller(BoardModel boardModel, BoardView boardView)
    {
        this.boardModel = boardModel;
        this.grid = boardModel.getGrid();
        this.boardView = boardView;
        this.highScore = new HighScore();
        try {
            highScore.readDataFromFile("HighScore.ser");
        } catch (IOException | ClassNotFoundException e)
        {
            System.out.println("couldnt read from file");
            e.printStackTrace();
        }


        Thread thread = new Thread(() ->{
            while(!boardModel.isGameOver())
            {

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        boardView.showTimeLabel(boardModel.getTime());

                    }
                });
            }
            //this.gameTimeScore = boardModel.getTime();

        });thread.start();

    }

    /**
     *Reveals the cells when a sweep is made
     */
    public void removeRects(){
        for(int i=0; i<boardModel.getWidth(); i++){
            for(int j=0; j<boardModel.getWidth(); j++){
                if(grid[i][j].isOpen()){
                    boardView.getCells(i,j).removeRect();
                }
            }
        }
    }

    /**
     * Reveals entire board when game is over
     */

    public void removeAllRects(){
        for(int i=0; i<boardModel.getWidth(); i++){
            for(int j=0; j<boardModel.getWidth(); j++){
                boardView.getCells(i,j).removeRect();
            }
        }
    }

    /**
     * Checks if game is over when a cell is clicked, if player wins, it reads to file
     * @param x
     * @param y
     */

    public void gameOnClickedTime(int x, int y)  {
        System.out.println(x);
        System.out.println(y);
        if (!boardModel.check(x,y))
        {
            if (boardModel.checkIfWin())  //boardModel.checkIfWin()
            {
                TextInputDialog  textInputDialog = new TextInputDialog("enter youre name");
                textInputDialog.showAndWait();
                while (textInputDialog.getResult() == null || textInputDialog.getResult().compareTo("") == 0)
                {
                    textInputDialog.showAndWait();

                }
                highScore.playerNameAndTime(textInputDialog.getResult(),boardModel.getTime());
                try {
                    highScore.readDataToFile("HighScore.ser");

                }catch (IOException e){
                    System.out.println("couldnt write to file");
                }

            }



        }

    }

    /**
     * Checks if cell has bomb, if bomb is clicked then game is over
     * @param x
     * @param y
     * @return
     */

    public boolean BombChecking(int x, int y)
    {
        if (grid[x][y].isBomb())
        {
            return true;
        }
        return false;

    }


    /**
     * Prints the value for "bombsaround" in a cell, if bombsaround is 0, then cell is blank
     * @param x
     * @param y
     * @return
     */

    public String onClickedCellBombsAround(int x, int y)
    {
        if (boardModel.checkBombsAround(x,y) == 0)
        {
            return " ";
        }
        else
        {
            return String.valueOf(boardModel.checkBombsAround(x,y));
        }
    }

    /**
     * Sets a cell to open, lets Cellview set cells open
     * @param x
     * @param y
     */


    public void setIsOpen(int x, int y)
    {
        grid[x][y].setOpen(x,y,true,boardModel);
    }

    /**
     * Checks if cell is flagged
     * @param x
     * @param y
     * @return
     */

    public boolean isFlagged(int x, int y)
    {
        if(grid[x][y].isFlagged())
        {
            grid[x][y].setFlagged(true);
            return true;
        }
        return false;
    }

    /**
     * Removes flag from cell
     * @param x
     * @param y
     */

    public void removeFlag(int x, int y)
    {
        grid[x][y].setFlagged(false);
    }

    /**
     * Sets flag in cell
     * @param x
     * @param y
     */
    public void setFlag(int x, int y)
    {

        grid[x][y].setFlagged(true);
    }

    /**
     * Checks if cell is opened
     * @param x
     * @param y
     * @return
     */


    public boolean isOpen(int x, int y)
    {
        if (grid[x][y].isOpen())
        {
            return true;
        }
        return false;
    }

    /**
     * Gives list of highscores
     * @return
     */

    public HighScore getHighScore() {
        return highScore;
    }
}
