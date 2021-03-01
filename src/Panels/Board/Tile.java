package Panels.Board;

import Figure.Figure;
import Utility.Spot;

public class Tile {
    public static int width;
    public static int height;

    private final Spot pos;
    private final Type type;
    private Figure placedFig;

    public Spot getPos() {
        return pos;
    }

    public Figure getPlacedFig() {
        return placedFig;
    }

    public void setPlacedFig(Figure placedFig) {
        this.placedFig = placedFig;
    }

    public Type getType() {
        return type;
    }

    public Tile(Spot pos, Type type) {
        this.pos = pos;
        this.type = type;
        this.placedFig = null;
    }

    public static void changeSize( int tileHeight,int tileWidth) {
        width = tileWidth;
        height = tileHeight;
    }
}
