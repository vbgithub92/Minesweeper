package androiddev.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import androiddev.minesweeper.fragments.LeaderboardsFragment;
import androiddev.minesweeper.fragments.LogoFragment;
import androiddev.minesweeper.logic.Scoreboard;

public class MainActivity extends AppCompatActivity {

    final static String DIFFICULTY_KEY = "DIFFICULTY_KEY";
    final static String BUNDLE_KEY = "BUNDLE_KEY";

    private String difficulty;
    private RadioButton selectedDifficulty;

    private LogoFragment logoFragment;
    private LeaderboardsFragment leaderboardsFragment;
    private Fragment currentFragment;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Scoreboard scoreboard;
    private Gson gson;

    private Vibrator vibe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibe  = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

        // Shared pref
        sharedPreferences = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getDefaultDifficulty();

        // Get scoreboard from DB
        String scoreboardJSON = sharedPreferences.getString(getString(R.string.scoreboard_key) , "");
        gson = new Gson();

        if(scoreboardJSON != null && !scoreboardJSON.isEmpty()) {
            scoreboard = gson.fromJson(scoreboardJSON, Scoreboard.class);
            Log.d("Scoreboard Exists", scoreboardJSON);
        }
        else {
            scoreboard = new Scoreboard();
            scoreboardJSON = gson.toJson(scoreboard);
            editor.putString(getString(R.string.scoreboard_key), scoreboardJSON);
            editor.apply();
        }


        // Fragments
        logoFragment = new LogoFragment();
        leaderboardsFragment = new LeaderboardsFragment();
        currentFragment = logoFragment;

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainActivityTopFragment, currentFragment)
                .commit();

    }



    public void playButtonClicked(View view) {

        vibe.vibrate(80);

        getCurrentDifficulty(findViewById(android.R.id.content).getRootView());
        saveDefaultDifficulty();

        Intent intent = new Intent(this, GameActivity.class);
        Bundle b = new Bundle();
        b.putString(DIFFICULTY_KEY, difficulty);
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
    }

    public void leaderBoardsButtonClicked(View view) {

        vibe.vibrate(80);

        if(currentFragment == logoFragment) {
            // For first time click
            getCurrentDifficulty(findViewById(android.R.id.content).getRootView());
            leaderboardsFragment.setDifficulty(difficulty);
            currentFragment = leaderboardsFragment;
        }

        else
            currentFragment = logoFragment;

        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityTopFragment, currentFragment).commit();

    }

    public void getCurrentDifficulty(View view) {
        selectedDifficulty = findViewById(((RadioGroup)findViewById(R.id.difficultyGroup)).getCheckedRadioButtonId());
        difficulty = selectedDifficulty.getText().toString();
    }


    public void changeDifficulty(View view) {
        getCurrentDifficulty(findViewById(android.R.id.content).getRootView());

        if(currentFragment == leaderboardsFragment) {
            leaderboardsFragment.updateFragment(difficulty, scoreboard);
        }
    }

    public void radioButtonClicked(View view) {
        vibe.vibrate(40);
        changeDifficulty(findViewById(android.R.id.content).getRootView());
    }

    public void saveDefaultDifficulty() {
        editor = sharedPreferences.edit();
        editor.putString(getString(R.string.default_difficulty_key), difficulty);
        editor.commit();
    }

    private void getDefaultDifficulty() {

        // TODO Maybe change?

        String defaultDifficulty = sharedPreferences.getString(getString(R.string.default_difficulty_key), "");

        if(!defaultDifficulty.isEmpty()) {
            int[] difficulties = {
                    R.string.easy,
                    R.string.normal,
                    R.string.hard
            };

            int[] rButtons = {
                    R.id.radioButtonEasy,
                    R.id.radioButtonNormal,
                    R.id.radioButtonHard
            };

            RadioGroup rGroup = findViewById(R.id.difficultyGroup);

            for(int i  = 0 ; i < difficulties.length ; i++) {
                if(getString(difficulties[i]).equals(defaultDifficulty)) {
                    rGroup.check(rButtons[i]);
                }
            }
        }
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

}


/* **************************************** DEV ****************************************** */
    /*
        Context context = getApplicationContext();
        selectedDifficulty = findViewById(((RadioGroup)findViewById(R.id.difficultyGroup)).getCheckedRadioButtonId());
        CharSequence text = selectedDifficulty.getText().toString();

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

         */
