package androiddev.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androiddev.minesweeper.logic.Difficulty;

public class ResultActivity extends AppCompatActivity {


    private ImageView resultLogo;
    private TextView resultTextView;
    private TextView minesLeftTextView;
    private TextView finishedInTextView;

    private Bundle bundle;

    private Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultLogo = findViewById(R.id.resultLogo);
        resultTextView = findViewById(R.id.resultTextView);
        minesLeftTextView = findViewById(R.id.minesLeftAmount);
        finishedInTextView = findViewById(R.id.finishedIn);

        vibe  = (Vibrator) ResultActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

        Intent intent = getIntent();
        bundle = ((Intent) intent).getBundleExtra(GameActivity.BUNDLE_KEY);

        initializeViews();

    }


    public void restartButtonClicked(View view) {

        vibe.vibrate(80);
        /*
        // TODO FIX!!!
        // Need to add difficulty to bundle so the game restarts correctly

        Intent intent = new Intent(this, GameActivity.class);
        Bundle b = new Bundle();
        //intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
        */

    }

    public void mainMenuButtonClicked(View view) {

        vibe.vibrate(80);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initializeViews(){

        String gameResult = bundle.getString((GameActivity.RESULT_KEY));

        if(gameResult != null && gameResult.equals("Win"))
        {
            resultLogo.setImageResource(R.drawable.win_logo);
            resultTextView.setText(R.string.victory);
        }
        else
        {
            resultLogo.setImageResource(R.drawable.lost_logo);
            resultTextView.setText(R.string.defeat);
        }

        minesLeftTextView.setText(bundle.getString(GameActivity.MINES_LEFT_KEY));
        finishedInTextView.setText(bundle.getString(GameActivity.FINISHED_IN_KEY));
    }
}
