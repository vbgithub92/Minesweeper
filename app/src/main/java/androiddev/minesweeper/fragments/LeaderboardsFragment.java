package androiddev.minesweeper.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androiddev.minesweeper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardsFragment extends Fragment {

    private String difficulty = "NO DIFFICULTY";

    public LeaderboardsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboards, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateFragment(difficulty);
    }

    public void setDifficulty(String difficulty){
        this.difficulty = difficulty;
    }

    public void updateFragment(String difficulty) {

        int i;

        TextView leaderBoardsDifficulty = getView().findViewById(R.id.leaderboardsDifficulty);
        leaderBoardsDifficulty.setText(difficulty);

        // TODO update the fragment according to the shared preferences high scores

        List<TextView> playerNamesTextViews = new ArrayList<TextView>();
        playerNamesTextViews.add((TextView)getView().findViewById(R.id.firstPlaceName));
        playerNamesTextViews.add((TextView)getView().findViewById(R.id.secondPlaceName));
        playerNamesTextViews.add((TextView)getView().findViewById(R.id.thirdPlaceName));

        String[] names = {"Saggi","Vlad","Shmulik"};

        for(i = 0 ; i < playerNamesTextViews.size() ; i++){
            playerNamesTextViews.get(i).setText(names[i]);
        }

        List<TextView> playerScoresTextViews = new ArrayList<TextView>();
        playerScoresTextViews.add((TextView)getView().findViewById(R.id.firstPlaceScore));
        playerScoresTextViews.add((TextView)getView().findViewById(R.id.secondPlaceScore));
        playerScoresTextViews.add((TextView)getView().findViewById(R.id.thirdPlaceScore));

        int[] scores = {999,666,42};

        for(i = 0 ; i < playerScoresTextViews.size() ; i++){
            playerScoresTextViews.get(i).setText(String.valueOf(scores[i]));
        }

    }
}
