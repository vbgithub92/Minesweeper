package androiddev.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androiddev.minesweeper.logic.Difficulty;
import androiddev.minesweeper.logic.Game;
import androiddev.minesweeper.logic.Tile;

public class GameActivity extends AppCompatActivity {


    final static String BUNDLE_KEY = "BUNDLE_KEY";
    final static String RESULT_KEY = "RESULT_KEY";
    final static String MINES_LEFT_KEY = "MINES_LEFT_KEY";
    final static String FINISHED_IN_KEY = "FINISHED_IN_KEY";

    private Game theGame;
    private GridView gridView;
    private TileAdapter tileAdapter;


    // For responsiveness
    private Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // For responsiveness
        vibe  = (Vibrator) GameActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

        Intent intent = getIntent();
        Bundle b = ((Intent) intent).getBundleExtra(MainActivity.BUNDLE_KEY);
        Difficulty difficulty = new Difficulty(b.getString(MainActivity.DIFFICULTY_KEY));

        theGame = new Game(difficulty);

        gridView = (GridView)findViewById(R.id.gridView);
        gridView.setNumColumns(theGame.getDifficulty().getBoardColsNum());
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        tileAdapter = new TileAdapter(this, theGame);
        gridView.setAdapter(tileAdapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vibe.vibrate(80);
                //theGame.tapTile((Tile)gridView.getAdapter().getItem(position));

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
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
    }



    public void restartIconClicked(View view) {

        // TODO
        // restart with same difficulty

        /*

        Intent intent = new Intent(this, GameActivity.class);

        Bundle b = new Bundle();

        //intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);

        */


    }
}
