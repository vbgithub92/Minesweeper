package androiddev.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ResultActivity extends AppCompatActivity {


    private Button restartMenuButton;
    private Button mainMenuButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        restartMenuButton = findViewById(R.id.restartButton);
        //mainMenuButton = findViewById(R.id.mainMenuButton);
    }


    public void restartButtonClicked(View view) {

        // TODO
        // Need to add difficulty to bundle so the game restarts correctly


        Intent intent = new Intent(this, GameActivity.class);

        Bundle b = new Bundle();

        //intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);

    }

    public void mainMenuButtonClicked(View view) {

        Intent intent = new Intent(this, MainActivity.class);

        Bundle b = new Bundle();

        //intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
    }
}
