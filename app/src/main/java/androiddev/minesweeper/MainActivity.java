package androiddev.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androiddev.minesweeper.fragments.LeaderboardsFragment;
import androiddev.minesweeper.fragments.LogoFragment;

public class MainActivity extends AppCompatActivity {

    final static String DIFFICULTY_KEY = "DIFFICULTY_KEY";
    final static String BUNDLE_KEY = "BUNDLE_KEY";

    private String difficulty;
    private RadioButton selectedDifficulty;

    private LogoFragment logoFragment;
    private LeaderboardsFragment leaderboardsFragment;
    private Fragment currentFragment;

    private Vibrator vibe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibe  = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

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

        // TODO maybe change?
        getCurrentDifficulty(findViewById(android.R.id.content).getRootView());

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
            leaderboardsFragment.updateFragment(difficulty);
        }
    }

    public void radioButtonClicked(View view) {
        vibe.vibrate(40);
        changeDifficulty(findViewById(android.R.id.content).getRootView());
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
