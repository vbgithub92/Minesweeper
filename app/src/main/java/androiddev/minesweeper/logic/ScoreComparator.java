package androiddev.minesweeper.logic;
import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
    public int compare(Score s1, Score s2) {
        return s1.compareTo(s2);
    }
}

