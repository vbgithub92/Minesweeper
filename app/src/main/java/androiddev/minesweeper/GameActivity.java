package androiddev.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import androiddev.minesweeper.logic.Difficulty;
import androiddev.minesweeper.logic.Game;
import androiddev.minesweeper.logic.Tile;

public class GameActivity extends AppCompatActivity {


    final static String BUNDLE_KEY = "BUNDLE_KEY";
    final static String RESULT_KEY = "RESULT_KEY";
    final static String MINES_LEFT_KEY = "MINES_LEFT_KEY";
    final static String FINISHED_IN_KEY = "FINISHED_IN_KEY";
    final static String DIFFICULTY_KEY = "DIFFICULTY_KEY";

    private Game theGame;
    private Difficulty difficulty;
    private GridView gridView;
    private TileAdapter tileAdapter;



    private TextView minesLeftTextView;
    private TextView gameTimeTextView;
    private ImageView restartIconImageView;


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

        restartIconImageView = findViewById(R.id.restartIcon);

        gridView = (GridView)findViewById(R.id.gridView);
        gridView.setNumColumns(theGame.getDifficulty().getBoardColsNum());
        gridView.setVerticalSpacing(2);
        gridView.setHorizontalSpacing(2);

        tileAdapter = new TileAdapter(getApplicationContext(), theGame);
        gridView.setAdapter(tileAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                vibe.vibrate(80);
                Log.d("TAP","Tapped tile at pos " + position);

                Tile tappedTile = (Tile)gridView.getAdapter().getItem(position);
                theGame.tapTile(tappedTile);


                // Check game end
                if(theGame.isGameOver()) {
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

                vibe.vibrate(80);
                Log.d("FlAG", "Flagged tile at pos " + position);

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


    }


    public void devWinButtonPressed(View view) {

        Intent intent = new Intent(this, ResultActivity.class);

        // For testing
        Bundle b = new Bundle();
        b.putString(RESULT_KEY, "Win");
        b.putString(MINES_LEFT_KEY, "0");
        b.putString(FINISHED_IN_KEY, "00:45");
        b.putString(DIFFICULTY_KEY, difficulty.getDifficultyName());
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);

    }


    public void devLoseButtonPressed(View view) {

        Intent intent = new Intent(this, ResultActivity.class);

        // For testing
        Bundle b = new Bundle();
        b.putString(RESULT_KEY, "Loss");
        b.putString(MINES_LEFT_KEY, "6");
        b.putString(FINISHED_IN_KEY, "02:35");
        b.putString(DIFFICULTY_KEY, difficulty.getDifficultyName());
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
    }



    public void restartIconClicked(View view) {

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
        if(wonGame)
            resultString = "Win";
        else
            resultString = "Loss";

        b.putString(RESULT_KEY, resultString);
        b.putString(MINES_LEFT_KEY, String.valueOf(theGame.getNumOfMinesLeft()));
        b.putString(FINISHED_IN_KEY, formatGameTime(theGame.getGameTime()));
        b.putString(DIFFICULTY_KEY , theGame.getDifficulty().getDifficultyName());
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);

    }

    public String formatGameTime(long gameTime){

        int gameMinutes = (int)(gameTime / 1000)/60;
        int gameSeconds = (int)(gameTime / 1000)%60;
        String formattedString = String.format("%02d:%02d", gameMinutes, gameSeconds);
        return formattedString;
    }

}
