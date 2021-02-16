package game.model;

public class CellModel
{
    private boolean isOpen;
    private boolean isBomb;
    private boolean isFlagged;
    private boolean isZero;
    private boolean isChecked;
    private int bombsAround;

    /**
     * Sets state of cells, initially to false, setters are used to later change its state
     */

    public CellModel()
    {
        this.isOpen = false;
        this.isBomb = false;
        this.isFlagged = false;
        this.isZero = false;
        this.isChecked = false;


    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setZero(boolean Zero){ this.isZero = Zero; }

    /**
     * Opens the cells on given x and y position, if the cell is zero then it will use sweep function,
     * this will be used recursively until sweep function no longer find cells that are zero
     * @param x
     * @param y
     * @param open
     * @param boardModel
     */

    public void setOpen(int x, int y, boolean open, BoardModel boardModel) {
        this.isOpen = open;
        if(this.isZero){
            boardModel.Sweep(x,y);
        }
    }

    public void setIsBomb(boolean isBomb) { this.isBomb = isBomb; }

    public void setFlagged(boolean flagged) { this.isFlagged = flagged; }

    public void setBombsAround(int bombsAround) { this.bombsAround = bombsAround; }

    public int getBombsAround() {
        return bombsAround;
    }

}

