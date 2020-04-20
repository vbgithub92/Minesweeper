package androiddev.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    final static String DIFFICULTY_KEY = "DIFFICULTY_KEY";
    final static String BUNDLE_KEY = "BUNDLE_KEY";

    private String difficulty;
    private RadioButton selectedDifficulty;


    // For responsiveness
    private Vibrator vibe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // For responsiveness
        vibe  = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void playButtonClicked(View view) {

        vibe.vibrate(80);

        RadioButton checkedButton = findViewById(((RadioGroup)findViewById(R.id.difficultyGroup)).getCheckedRadioButtonId());
        difficulty = checkedButton.getText().toString();

        Intent intent = new Intent(this, GameActivity.class);

        Bundle b = new Bundle();

        b.putString(DIFFICULTY_KEY, difficulty);

        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
    }
}
