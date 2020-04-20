package androiddev.minesweeper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;

public class TileView extends AppCompatButton {

    public TileView(Context context) {
        super(context);

        setTextSize(16);
        setTextColor(Color.BLACK);
        setGravity(Gravity.CENTER);
        setBackgroundColor(Color.WHITE);

    }

}
