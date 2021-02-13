package Board;

import Utility.Place;
import java.util.ArrayList;

public class Board {
    private final int width;
    private final int height;
    public ArrayList<Tile> tiles;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new ArrayList<>();
        this.setTiles();
    }

    private void setTiles() {
        for(int i = 0; i < this.height; ++i) {
            for(int j = 0; j < this.width; ++j) {
                this.tiles.add(new Tile(new Place(i, j)));
            }
        }

    }
}
