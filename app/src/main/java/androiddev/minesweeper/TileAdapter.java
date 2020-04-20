package androiddev.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androiddev.minesweeper.logic.Game;
import androiddev.minesweeper.logic.Tile;

public class TileAdapter extends BaseAdapter {

    private Context context;
    private Game game;

    public TileAdapter(Context context, Game game) {
        this.context = context;
        this.game = game;
    }


    @Override
    public int getCount() {
        return game.getBoardSize();
    }

    @Override
    public Object getItem(int position) {
        return game.getTile(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TileView tileView;

        if (convertView == null) {

            tileView = new TileView(context);
            tileView.setPadding(8, 8, 8, 8);

        } else {
            tileView = (TileView) convertView;
        }

        Tile tile = game.getTile(position);

        tileView.setText("");

        if (tile.isFlagged()) {
            tileView.setText(R.string.flag);
        }

        if (tile.isTapped()) {
            if (tile.isMine()) {
                tileView.setText(R.string.mine);
            } else {

                tileView.setBackgroundColor(Color.LTGRAY);
                int adjMines = tile.getAdjacentMinesNum();

                int textColor = Color.parseColor("#FFFFFF");

                switch(adjMines) {
                    case 0 :
                        break;
                    case 1:
                        textColor = Color.BLUE;
                        break;
                    case 2:
                        textColor = Color.parseColor("#009933"); // Dark Green
                        break;
                    case 3:
                        textColor = Color.RED;
                        break;
                    case 4:
                        textColor = Color.parseColor("#6600cc"); // Purple
                        break;
                    case 5:
                        textColor = Color.MAGENTA;
                    case 6:
                        textColor = Color.CYAN;
                    case 7:
                        textColor = Color.BLACK;
                    case 8:
                        textColor = Color.LTGRAY;
                    default:
                        break;
                }

                if(adjMines != 0) {
                    // change color
                    tileView.setTextColor(textColor);
                    tileView.setText(String.valueOf(tile.getAdjacentMinesNum()));
                }
            }
        }
        return tileView;
    }
}
