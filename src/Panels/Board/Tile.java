package Panels.Board;

import Figure.Figure;
import Utility.Spot;

public class Tile {
    public static int width;
    public static int height;

    private final Spot pos;
    private final Type type;
    private Figure placedFig;

    /**
     * Returns the position of the tile
     * @return Spot
     */
    public Spot getPos() {
        return pos;
    }

    /**
     * Gets the figure that is standing on the tile
     * @return Figure
     */
    public Figure getPlacedFig() {
        return placedFig;
    }

    /**
     * Sets a figure, standing on top of this tile
     * @param placedFig Figure
     */
    public void setPlacedFig(Figure placedFig) {
        this.placedFig = placedFig;
    }

    /**
     * Returns the type of the tile
     * @return Type
     */
    public Type getType() {
        return type;
    }

    /**
     * Constructor for a Tile
     * @param pos Position of the tile
     * @param type Type of the tile
     */
    public Tile(Spot pos, Type type) {
        this.pos = pos;
        this.type = type;
        this.placedFig = null;
    }

    /**
     * Changes the tile size in pixels.
     * @param tileHeight Height
     * @param tileWidth Width
     */
    public static void changeSize( int tileHeight,int tileWidth) {
        width = tileWidth;
        height = tileHeight;
    }
}
