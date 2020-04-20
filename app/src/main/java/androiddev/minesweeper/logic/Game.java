package androiddev.minesweeper.logic;

import android.util.Log;

import java.util.Random;

public class Game {
    private Tile[][] tiles;
    private int numOfMinesLeft;
    private int flaggedTilesCounter = 0;
    private boolean isFirstMove = true;
    private long gameStrTime = 0;
    private long gameTime = 0;

    private Difficulty difficulty;

    public Game(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.numOfMinesLeft = difficulty.getNumberOfMines();
        this.tiles = new Tile[difficulty.getBoardRowsNum()][difficulty.getBoardColsNum()];
        initializeTiles(tiles);
    }

    public long getGameTime() {
        return gameTime;
    }

    public void flagTile(Tile tile) {
        tile.flagTile();
        flaggedTilesCounter++;
        if (tile.isMine())
            numOfMinesLeft--;
        if (flaggedTilesCounter == numOfMinesLeft)
            endGame(true);
    }

    public void tapTile(Tile tile) {
        if (!tile.isTapped()) {
            if (isFirstMove) {
                isFirstMove = false;
                tile.tapTile();
                setMines(difficulty.getNumberOfMines());
                gameStrTime = System.currentTimeMillis();
            }
            if (tile.tapTile()) // hit a mine
                endGame(false);
            else if (tile.getAdjacentMinesNum() == 0) {
                int row = -1;
                int col = -1;
                for (int i = 0; i < tiles.length; i++) {
                    for (int j = 0; j < tiles[0].length; j++) {
                        if (tile.equals(tiles[i][j])) {
                            row = i;
                            col = j;
                        }
                    }
                }
                if (row != -1 && col != -1) {
                    for (int i = -1; i <= 1; i++) {
                        if (isValidIndex(row - 1, col + i))
                            if (!tiles[row - 1][col + i].isMine())
                                tapTile(tiles[row - 1][col + i]);
                    }
                    for (int i = -1; i <= 1; i++) {
                        if (i != 0 && isValidIndex(row, col + i))
                            if (!tiles[row][col + i].isMine())
                                tapTile(tiles[row][col + i]);
                    }
                    for (int i = -1; i <= 1; i++) {
                        if (isValidIndex(row + 1, col + i))
                            if (!tiles[row + 1][col + i].isMine())
                                tapTile(tiles[row + 1][col + i]);
                    }
                }
            }
        }
    }

    public void restartGame() {
        this.numOfMinesLeft = difficulty.getNumberOfMines();
        this.tiles = new Tile[difficulty.getBoardRowsNum()][difficulty.getBoardColsNum()];
        this.flaggedTilesCounter = 0;
        this.isFirstMove = true;
        this.gameTime = 0;
        this.gameStrTime = 0;
    }

    private void setMines(int numOfMines) {
        while (numOfMines > 0) {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[0].length; j++) {
                    if (!tiles[i][j].isTapped() && !tiles[i][j].isMine() && numOfMines > 0) {
                        if (new Random().nextInt(100) < 25) {
                            tiles[i][j].setMine(true);
                            numOfMines--;
                        }
                    }
                }
            }
        }
        setAdjacentMinesCount();
    }

    private void setAdjacentMinesCount() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].setAdjacentMinesNum(countAdjacentMines(i, j));
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int AdjacentMines = 0;

        for (int i = -1; i <= 1; i++) {
            if (isValidIndex(row - 1, col + i))
                if (tiles[row - 1][col + i].isMine())
                    AdjacentMines++;
        }

        for (int i = -1; i <= 1; i++) {
            if (i != 0 && isValidIndex(row, col + i))
                if (tiles[row][col + i].isMine())
                    AdjacentMines++;
        }

        for (int i = -1; i <= 1; i++) {
            if (isValidIndex(row + 1, col + i))
                if (tiles[row + 1][col + i].isMine())
                    AdjacentMines++;
        }

        return AdjacentMines;
    }

    private boolean isValidIndex(int row, int col) {
        if (row < 0 || col < 0)
            return false;
        if (row > tiles.length - 1 || col > tiles[0].length - 1)
            return false;
        return true;
    }

    private void endGame(boolean victory) {
        gameTime = System.currentTimeMillis() - gameStrTime;
        if (victory) {
            // victory logic
        } else {
            // loosing logic
        }
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getBoardSize() {
        return difficulty.getBoardRowsNum() * difficulty.getBoardColsNum();
    }

    public Tile getTile(int position) {
        return tiles[position % tiles.length][position/tiles.length];
    }

    private void initializeTiles(Tile[][] tiles) {
        for(int i = 0 ; i < difficulty.getBoardRowsNum(); i++)
            for (int j = 0 ; j < difficulty.getBoardColsNum();j++)
                tiles[i][j] = new Tile(false,false,false,0);
    }

    public int getNumOfMinesLeft() {
        return this.numOfMinesLeft;
    }

}
