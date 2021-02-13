package Board;

import Utility.Place;

public class Tile {
    public static final int width = 75;
    public static final int height = 75;

    private final Place pos;
    private final Type type;

    public Place getPos() {
        return pos;
    }

    public Type getType() {
        return type;
    }

    public Tile(Place pos, Type type) {
        this.pos = pos;
        this.type = type;
    }
}
