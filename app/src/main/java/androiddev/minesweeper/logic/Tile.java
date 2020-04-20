package androiddev.minesweeper.logic;

public class Tile {
    private boolean isTapped;
    private boolean isFlagged;
    private boolean isMine;
    private int adjacentMinesNum;

    public Tile() {
    }

    public Tile(boolean isTapped, boolean isFlagged, boolean isMine, int adjacentMinesNum) {
        this.isTapped = isTapped;
        this.isFlagged = isFlagged;
        this.isMine = isMine;
        this.adjacentMinesNum = adjacentMinesNum;
    }

    public void flagTile() {
        if (!this.isTapped())
            this.setFlagged(!this.isFlagged());
    }

    public boolean tapTile() {
        isTapped = true;
        if (isMine()) return true;
        return false;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public void setAdjacentMinesNum(int adjacentMinesNum) {
        this.adjacentMinesNum = adjacentMinesNum;
    }

    public boolean isTapped() {
        return isTapped;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isMine() {
        return isMine;
    }

    public int getAdjacentMinesNum() {
        return adjacentMinesNum;
    }
}
