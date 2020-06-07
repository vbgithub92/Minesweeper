package androiddev.minesweeper.logic;

public class Player {

    private String name;
    private String lastDifficulty;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, String lastDifficulty) {
        this.name = name;
        this.lastDifficulty = lastDifficulty;
    }

    public String getLastDifficulty() {
        return lastDifficulty;
    }

    public void setLastDifficulty(String lastDifficulty) {
        this.lastDifficulty = lastDifficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
