package Board;

import Utility.Place;

public class Tile {
    public Place pos;
    public static final int width = 75;
    public static final int height = 75;

    public Tile(Place pos) {
        this.pos = pos;
    }
}
