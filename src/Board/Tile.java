package Board;

import Utility.Place;

public class Tile {
    public static int width;
    public static int height;

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

    public static void changeSize(int tileWidth, int tileHeight) {
        width = tileWidth;
        height = tileHeight;
    }

    public static Place convertToPos(Place p) {
        if(p.getColumn()<0) p.setColumn(-Tile.width);
        if(p.getRow()<0) p.setColumn(-Tile.height);
        return new Place(p.getRow() / height, p.getColumn() / width);
    }
}
