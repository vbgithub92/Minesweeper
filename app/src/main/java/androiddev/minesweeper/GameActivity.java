package androiddev.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import androiddev.minesweeper.logic.Difficulty;
import androiddev.minesweeper.logic.Game;
import androiddev.minesweeper.logic.Score;
import androiddev.minesweeper.logic.Tile;

public class GameActivity extends AppCompatActivity {


    final static String BUNDLE_KEY = "BUNDLE_KEY";
    final static String RESULT_KEY = "RESULT_KEY";
    final static String MINES_LEFT_KEY = "MINES_LEFT_KEY";
    final static String FINISHED_IN_KEY = "FINISHED_IN_KEY";
    final static String DIFFICULTY_KEY = "DIFFICULTY_KEY";
    final static String SCORE_KEY = "SCORE_KEY";

    private Game theGame;
    private Difficulty difficulty;
    private GridView gridView;
    private TileAdapter tileAdapter;

    private TextView minesLeftTextView;
    private TextView gameTimeTextView;

    // For responsiveness
    private Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        vibe  = (Vibrator) GameActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

        Intent intent = getIntent();
        Bundle b = ((Intent) intent).getBundleExtra(MainActivity.BUNDLE_KEY);

        difficulty = new Difficulty(b.getString(MainActivity.DIFFICULTY_KEY));
        theGame = new Game(difficulty);

        minesLeftTextView = findViewById(R.id.minesLeftTextView);
        minesLeftTextView.setText(String.valueOf(theGame.getNumOfMinesLeft()));

        gameTimeTextView = findViewById(R.id.timerTextView);

        gridView = (GridView)findViewById(R.id.gridView);
        gridView.setNumColumns(theGame.getDifficulty().getBoardColsNum());
        gridView.setVerticalSpacing(4);
        gridView.setHorizontalSpacing(4);

        tileAdapter = new TileAdapter(getApplicationContext(), theGame);
        gridView.setAdapter(tileAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!theGame.getTile(position).isTapped())
                    vibe.vibrate(80);

                Tile tappedTile = (Tile)gridView.getAdapter().getItem(position);
                theGame.tapTile(tappedTile);

                // Check game end
                if(theGame.isGameOver()) {
                    vibe.vibrate(500);
                    if(theGame.isGameWon())
                        finishGame(true);
                    else
                        finishGame(false);
                }

                tileAdapter.notifyDataSetChanged();

            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if(!theGame.getTile(position).isTapped())
                    vibe.vibrate(80);

                Tile FlaggedTile = (Tile) gridView.getAdapter().getItem(position);
                theGame.flagTile(FlaggedTile);

                // Check game end
                if (theGame.isGameOver()) {
                    if (theGame.isGameWon())
                        finishGame(true);
                    else
                        finishGame(false);
                }
                minesLeftTextView.setText(String.valueOf(theGame.getDifficulty().getNumberOfMines() - theGame.getFlaggedTilesCounter()));
                tileAdapter.notifyDataSetChanged();
                return true;
            }
        });


        updateDisplay();

        /* Dev */

    }

    public void restartIconClicked(View view) {

        vibe.vibrate(500);

        Intent intent = new Intent(this, GameActivity.class);
        Bundle b = new Bundle();
        b.putString(DIFFICULTY_KEY, difficulty.getDifficultyName());

        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);

    }

    public void finishGame(boolean wonGame) {

        Intent intent = new Intent(this, ResultActivity.class);

        // For testing
        Bundle b = new Bundle();

        String resultString;
        if(wonGame) {
            int score = calcScore();
            b.putInt(SCORE_KEY, score);
            resultString = "Win";
            theGame.flagAllMines();
        }
        else {
            resultString = "Loss";
            theGame.showAllMines();
        }
        tileAdapter.notifyDataSetChanged();

        b.putString(RESULT_KEY, resultString);
        b.putString(MINES_LEFT_KEY, String.valueOf(theGame.getNumOfMinesLeft()));
        b.putString(FINISHED_IN_KEY, formatGameTime(theGame.getGameTime()));
        b.putString(DIFFICULTY_KEY , theGame.getDifficulty().getDifficultyName());
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);

    }

    public int calcScore() {
        // TODO Maybe change?
        if(theGame.getGameTime() != 0) {
            return (int)(99999999 / theGame.getGameTime());
        }
        else
            return 0;

    }

    public String formatGameTime(long gameTime){

        int gameMinutes = (int)(gameTime / 1000)/60;
        int gameSeconds = (int)(gameTime / 1000)%60;
        String formattedString = String.format("%02d:%02d", gameMinutes, gameSeconds);
        return formattedString;
    }

    private void updateDisplay() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (!(theGame.getGameStrTime() == 0)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gameTimeTextView.setText(formatGameTime(System.currentTimeMillis() - theGame.getGameStrTime()));
                        }
                    });
                }
            }
        },0,1000);//Update text every second
    }


    // ------------------- DEV ---------------------

    public void devWinButtonPressed(View view) {

        Intent intent = new Intent(this, ResultActivity.class);

        Bundle b = new Bundle();
        b.putString(RESULT_KEY, "Win");
        b.putString(MINES_LEFT_KEY, "0");
        b.putInt(SCORE_KEY,calcScore());
        b.putString(FINISHED_IN_KEY, formatGameTime(theGame.getGameTime()));
        b.putString(DIFFICULTY_KEY, difficulty.getDifficultyName());
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);

    }


    public void devLoseButtonPressed(View view) {

        Intent intent = new Intent(this, ResultActivity.class);

        Bundle b = new Bundle();
        b.putString(RESULT_KEY, "Loss");
        b.putString(MINES_LEFT_KEY, "6");
        b.putString(FINISHED_IN_KEY, "02:35");
        b.putString(DIFFICULTY_KEY, difficulty.getDifficultyName());
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
    }


}
