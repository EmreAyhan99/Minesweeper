package game.model;


import game.controller.Difficulty;
import game.view.BoardView;
import game.view.CellView;
import javafx.application.Platform;

import java.io.*;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Boardmodel is the brain of the game, controls amount of cells and their characteristics
 */

public class BoardModel
{
    private final CellModel[][] grid;
    private boolean treadStart = false;
    private int x;
    private int y;
    private long timeInSec;
    private  long elapsedSeconds;
    private boolean gameOver;
    private int time;
    private int width;
    private int nrOfBombs;

    /**
     * Creates and instance of the boardmodel with difficulty set as hard or easy, difficulty sets width and amount of bombs
     * Boardmodel randomizes bombs and sets cells without bombs adjacent to "isZero", makes it easier to use Sweep function
     * @param difficulty
     */

    public BoardModel(Difficulty difficulty){

        this.gameOver = false;
        this.time = 0;
        this.width = 0;
        this.nrOfBombs = 0;

        /////////////////////////////////////////////////////

        if(difficulty == Difficulty.EASY){
            width = 9;
            nrOfBombs = 10;
        }
        if(difficulty == Difficulty.HARD){
            width = 16;
            nrOfBombs = 40;
        }

        grid = new CellModel[width][width];

        for(int i=0; i<width; i++){
            for(int j=0; j<width; j++){
                setGrid(i,j,new CellModel());
            }
        }
        Random rand = new Random();

        for (int i = 0; i < nrOfBombs; i++)
        {
            int x = rand.nextInt(width);
            int y = rand.nextInt(width);

            if (!this.grid[x][y].isBomb())
            {
                this.grid[x][y].setIsBomb(true);
            }
            else {
                i = i - 1;
            }
        }

        for(int i = 0; i < width; i++)
        {
            for (int j = 0; j < width; j++)
            {
                int bombsFound = this.checkBombsAround(i,j);
                grid[i][j].setBombsAround(bombsFound);
            }
        }

        for(int i = 0; i < width; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if(grid[i][j].getBombsAround() == 0 && !grid[i][j].isBomb()){
                    grid[i][j].setZero(true);
                }
            }
        }
    }


    /**
     * Creates a cell at given x and y coordinates
     * @param x
     * @param y
     * @param val
     */

    public void setGrid(int x, int y, CellModel val) {
        grid[x][y] = val;
    }



    /**
     * Returns grid
     * @return
     */

    public CellModel[][] getGrid()
    {
        return grid;
    }


    /**
     * Checks bombs around the clicked cell, returns number as integer
     * @param x
     * @param y
     * @return
     * @throws ArrayIndexOutOfBoundsException
     */
    public int checkBombsAround(int x, int y) throws ArrayIndexOutOfBoundsException
    {

        int bombsFound = 0;

        if (!this.grid[x][y].isBomb())
        {
            for (int i = -1; i < 2; i++)
            {
                for (int j = -1; j < 2; j++)
                {
                    try {

                        if (this.grid[x+i][y+j].isBomb())
                        {
                            bombsFound++;
                        }
                        else
                        {
                        }
                    }catch (ArrayIndexOutOfBoundsException e)
                    {
                    }

                }

            }

        }

        return bombsFound;
    }

    /**
     * Checks if game is lost (if bomb is clicked)
     * @param x
     * @param y
     * @return
     */


    public boolean check(int x, int y)
    {

        this.x = x;
        this.y = y;
        if (!grid[this.x][this.y].isBomb())
        {
            this.gameOver = false;
            return false;
        }
        else {

            this.gameOver = true;
            return true;
        }
    }

    /**
     * Sweep is used if a cell is set to zero (no bombs adjacent)
     * Sweep will keep opening cells around until a cell has bombs around, it also keeps track of cells that are opened
     * so it wont get caught in an infinite loop
     * The row and col values is the coordinates for the currently checked cell, if all conditions are met then it will open the cell
     * @param x
     * @param y
     */



    public void Sweep(int x, int y){
        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                int row = x + i;
                int col = y + j;
                if(row > -1 && row < getWidth() && col > -1 && col < getWidth()){
                    if(row != x || col != y){
                        CellModel adjacentCell = grid[row][col];
                        if(!adjacentCell.isBomb() && !adjacentCell.isOpen()){
                            adjacentCell.setOpen(row,col,true,this);
                        }
                    }
                }
            }
        }
    }

    /**
     * returns game over
     * @return
     */


    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Increments time each second, when game is over it returns current time
     * @return
     */

    public int getTime()
    {
        if (!gameOver)
        {
            return time++;
        }
        else{
            return time;
        }
    }

    /**
     * Returns width for current board
     * @return
     */

    public int getWidth() {
        return width;
    }

    /**
     * Checks if all cells that dont have bombs are opened, if true then game is won
     * @return
     */

    public boolean checkIfWin()
    {
        int opened = 1;
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (grid[i][j].isOpen()){
                    opened++;
                    if (grid[i][j].isBomb())
                    {
                        return false;
                    }
                }
            }
        }
        if (opened == (width*width)-nrOfBombs)
        {
            return true;
        }
        else
            return false;
    }


}

