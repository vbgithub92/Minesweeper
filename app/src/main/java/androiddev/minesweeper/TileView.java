package androiddev.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatButton;

public class TileView extends AppCompatButton {


    public TileView(Context context) {

        super(context);

        setTextSize(12);
        setText("X");
        setBackgroundColor(Color.GREEN);
        setTextColor(Color.BLACK);
        setGravity(Gravity.CENTER);
        //setTextAlignment(TEXT_ALIGNMENT_CENTER);

    }
}
