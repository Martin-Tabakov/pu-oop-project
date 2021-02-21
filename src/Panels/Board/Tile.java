package Panels.Board;

import Utility.Spot;

public class Tile {
    public static int width;
    public static int height;

    private final Spot pos;
    private final Type type;

    public Spot getPos() {
        return pos;
    }

    public Type getType() {
        return type;
    }

    public Tile(Spot pos, Type type) {
        this.pos = pos;
        this.type = type;
    }

    public static void changeSize( int tileHeight,int tileWidth) {
        width = tileWidth;
        height = tileHeight;
    }

    public static Spot convertToPos(Spot p) {
        if(p.getWidth()<0) p.setWidth(-Tile.width);
        if(p.getHeight()<0) p.setWidth(-Tile.height);
        return new Spot(p.getHeight() / height, p.getWidth() / width);
    }
}
