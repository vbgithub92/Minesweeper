package androiddev.minesweeper.logic;

public class Difficulty {


    static final int ROWS_EASY = 6;
    static final int COLS_EASY = 7;
    static final int MINES_EASY = 6;
    static final int ROWS_NORMAL = 9;
    static final int COLS_NORMAL = 11;
    static final int MINES_NORMAL = 16;
    static final int ROWS_HARD = 12;
    static final int COLS_HARD = 14;
    static final int MINES_HARD = 28;

    private int boardRowsNum;
    private int boardColsNum;
    private int numberOfMines;

    private String difficultyName;

    public Difficulty(String difficultyLevel) {
        setDifficulty(difficultyLevel);
    }

    public void setDifficulty(String difficultyLevel) {

        switch(difficultyLevel)
        {
            case "EASY":
                setBoardRowsNum(ROWS_EASY);
                setBoardColsNum(COLS_EASY);
                setNumberOfMines(MINES_EASY);
                setDifficultyName("Easy");
                break;
            case "NORMAL":
                setBoardRowsNum(ROWS_NORMAL);
                setBoardColsNum(COLS_NORMAL);
                setNumberOfMines(MINES_NORMAL);
                setDifficultyName("Normal");
                break;
            case "HARD":
                setBoardRowsNum(ROWS_HARD);
                setBoardColsNum(COLS_HARD);
                setNumberOfMines(MINES_HARD);
                setDifficultyName("Hard");
                break;
            default:
                break;

        }

    }


    public void setBoardRowsNum(int boardRowsNum) {
        this.boardRowsNum = boardRowsNum;
    }

    public void setBoardColsNum(int boardColsNum) {
        this.boardColsNum = boardColsNum;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public void setDifficultyName(String difficultyName) {
        this.difficultyName = difficultyName;
    }


    public int getBoardRowsNum() {
        return boardRowsNum;
    }

    public int getBoardColsNum() {
        return boardColsNum;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public String getDifficultyName() {
        return difficultyName;
    }
}
