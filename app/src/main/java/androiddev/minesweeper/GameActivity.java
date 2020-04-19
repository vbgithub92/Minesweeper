package androiddev.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {


    private Button devButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        devButton = findViewById(R.id.devGameResultButton);
    }


    public void restartIconClicked(View view) {

        // TODO
        // restart with same difficulty

        Intent intent = new Intent(this, GameActivity.class);

        Bundle b = new Bundle();

        //intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);

    }



    public void devGameResultButtonPressed(View view) {

        Intent intent = new Intent(this, ResultActivity.class);

        Bundle b = new Bundle();

        //intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
    }
}
