package androiddev.minesweeper.logic;

import java.util.ArrayList;
import java.util.Collections;

public class Scoreboard {
    private ArrayList<Score> scores;

    public Scoreboard() {
        scores = new ArrayList<Score>();
    }

    public Scoreboard(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public void addScore(Score score) {
        this.scores.add(score);
    }

    public void addScore(int score, String playersName, String difficulty) {
        Score theScore = new Score(score, playersName, difficulty);
        this.scores.add(theScore);
    }

    public Score getHighScoreByPlayersName(String playersName) {
        Score highScore = null;
        for (Score score : this.scores) {
            if (score.getPlayersName() == playersName) {
                if (highScore == null || score.getScore() > highScore.getScore()) {
                    highScore = score;
                }
            }
        }
        return highScore;
    }

    public Score getHighestScore(String difficulty) {
        Score highScore = null;
        if(this.scores != null) {
            for (Score score : this.scores) {
                if (score.getDifficulty().equals(difficulty)) {
                    if (highScore == null || score.getScore() > highScore.getScore()) {
                        highScore = score;
                    }
                }
            }
        }

        return highScore;
    }

    public Score[] getTop3HighScores(String difficulty) {
        Score[] topThree = new Score[3];
        Score firstPlace, secondPlace = null, thirdPlace = null;

        firstPlace = getHighestScore(difficulty);
        if(this.scores != null) {
            for (Score score : this.scores) {
                if (score.getDifficulty().equals(difficulty)) {
                    if (secondPlace == null || score.getScore() > secondPlace.getScore()) {
                        if (!score.equals(firstPlace))
                            secondPlace = score;
                    }
                }
            }
            for (Score score : this.scores) {
                if (score.getDifficulty().equals(difficulty)) {
                    if (thirdPlace == null || score.getScore() > thirdPlace.getScore()) {
                        if (!score.equals(firstPlace) && !score.equals(secondPlace))
                            thirdPlace = score;
                    }
                }
            }

        }

        topThree[0] = firstPlace;
        topThree[1] = secondPlace;
        topThree[2] = thirdPlace;
        return topThree;
    }

    public boolean isInTopThree(int score, String difficulty) {
        Score[] topThree = getTop3HighScores(difficulty);

        if (topThree[2] == null || topThree[2].getScore() < score) {
            return true;
        } else {
            return false;
        }
    }

    public void purgeScoreboard(int numOfTopScores) {
        Collections.sort(scores, new ScoreComparator());
        ArrayList<Score> tempScores = new ArrayList<>();
        int i = 0;
        for (Score s : this.scores) {
            if (i < numOfTopScores) {
                tempScores.add(s);
                i++;
            }
        }
        this.setScores(tempScores);
    }
}
