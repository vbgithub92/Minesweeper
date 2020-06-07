package androiddev.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import androiddev.minesweeper.logic.Difficulty;
import androiddev.minesweeper.logic.Score;
import androiddev.minesweeper.logic.Scoreboard;

public class ResultActivity extends AppCompatActivity {


    final static String BUNDLE_KEY = "BUNDLE_KEY";
    final static String DIFFICULTY_KEY = "DIFFICULTY_KEY";

    private ImageView resultLogo;
    private TextView resultTextView;
    private TextView endGameStatsPromptTextView;
    private TextView endGameStatsTextView;
    private TextView finishedInTextView;
    private Difficulty difficulty;
    private String gameResult;

    private Bundle bundle;

    private Vibrator vibe;

    private Dialog highScoreDialog;
    private EditText playerNameEditText;
    private Button submitButton;
    private String playerName;
    private int playerScore;

    private Scoreboard scoreboard;
    private String scoreboardJSON;
    private Gson gson;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultLogo = findViewById(R.id.resultLogo);
        resultTextView = findViewById(R.id.resultTextView);
        endGameStatsPromptTextView = findViewById(R.id.endGameStatsPrompt);
        endGameStatsTextView = findViewById(R.id.endGameStats);
        finishedInTextView = findViewById(R.id.finishedIn);

        vibe  = (Vibrator) ResultActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

        // Shared pref
        sharedPreferences = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Intent intent = getIntent();
        bundle = ((Intent) intent).getBundleExtra(GameActivity.BUNDLE_KEY);
        difficulty = new Difficulty(bundle.getString(DIFFICULTY_KEY));
        playerScore = bundle.getInt(GameActivity.SCORE_KEY);

        initializeViews();

        if(gameResult.equals("Win")) {
            initScoreboard();
            if (scoreboard.isInTopThree(playerScore, difficulty.getDifficultyName())) {
                openHighScoreDialog();
            }
        }

       /* DEV */

        resultLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    resultLogo.animate().rotationBy(360);
                                }
                            });
                    }
                },0,1000);//Update text every second
            }
        });
/*
        if(checkHighScore(playerScore)) {
            openHighScoreDialog();
        }*/

    }


    public void restartButtonClicked(View view) {
        vibe.vibrate(80);
        restart();
    }

    public void exitButtonClicked(View view) {

        vibe.vibrate(80);
        exit();
    }

    private void initializeViews(){

        gameResult = bundle.getString((GameActivity.RESULT_KEY));

        if(gameResult != null && gameResult.equals("Win"))
        {
            resultLogo.setImageResource(R.drawable.win_logo);
            resultTextView.setText(R.string.victory);
            endGameStatsPromptTextView.setText(R.string.score);
            endGameStatsTextView.setText(String.valueOf(playerScore));

        }
        else
        {
            resultLogo.setImageResource(R.drawable.lost_logo);
            resultTextView.setText(R.string.defeat);
            endGameStatsTextView.setText(bundle.getString(GameActivity.MINES_LEFT_KEY));
        }
        finishedInTextView.setText(bundle.getString(GameActivity.FINISHED_IN_KEY));
    }


    public void restart() {
        Intent intent = new Intent(this, GameActivity.class);
        Bundle b = new Bundle();
        b.putString(DIFFICULTY_KEY, difficulty.getDifficultyName());

        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);

    }

    public void exit(){
        finishAffinity();
        System.exit(0);
    }

    /* DEV */

    public boolean checkHighScore(int score, Difficulty difficulty) {
        // if highscore - balagan
        if(true)
            return true;
        return false;
    }

    public void openHighScoreDialog() {

        highScoreDialog = new Dialog(this);

        highScoreDialog.setContentView(R.layout.layout_highscoredialog);

        playerNameEditText = highScoreDialog.findViewById(R.id.playerName);

        submitButton = highScoreDialog.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playerName = playerNameEditText.getText().toString();

                if(playerName != null && !playerName.isEmpty()) {
                    highScoreDialog.dismiss();
                    updateScoreboard();
                }
                else {
                    Context context = getApplicationContext();
                    CharSequence text = getString(R.string.missing_player_name_prompt);

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

            }
        });

        highScoreDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        highScoreDialog.show();

    }

    public void updateScoreboard() {
        scoreboard.addScore(playerScore, playerName, difficulty.getDifficultyName());
        scoreboardJSON = gson.toJson(scoreboard);
        editor.putString(getString(R.string.scoreboard_key), scoreboardJSON);
        editor.apply();
    }

    public void initScoreboard() {
        scoreboardJSON = sharedPreferences.getString(getString(R.string.scoreboard_key) , "");
        gson = new Gson();

        if(scoreboardJSON != null && !scoreboardJSON.isEmpty()) {
            scoreboard = gson.fromJson(scoreboardJSON, Scoreboard.class);
        }
    }

}
