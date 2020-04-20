package androiddev.minesweeper;

import android.content.Context;
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
        tileView.setText(tile.toString());
        return tileView;
    }
}
