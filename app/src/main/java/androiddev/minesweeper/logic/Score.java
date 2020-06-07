package androiddev.minesweeper.logic;

public class Score {
    private int score;
    private String playersName;
    private String difficulty;

    public Score(int score, String playersName, String difficulty) {
        this.score = score;
        this.playersName = playersName;
        this.difficulty = difficulty;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayersName() {
        return playersName;
    }

    public void setPlayersName(String playersName) {
        this.playersName = playersName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public boolean equals(Score o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return score == score1.score &&
                playersName.equals(score1.playersName) &&
                difficulty.equals(score1.difficulty);
    }

    public int compareTo(Score s) {
        if (this.getScore() > s.getScore())
            return 1;
        else if (this.getScore() < s.getScore())
            return -1;
        else
            return 0;
    }
}
