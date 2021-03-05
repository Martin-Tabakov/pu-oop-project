package Panels;

import Panels.Board.Tile;
import Utility.Spot;

public abstract class Panel {

    private final Spot position;
    private final Spot size;

    /**
     * Returns the position on which the top left tile of the panel is located on
     *
     * @return Spot
     */
    public Spot getPosition() {
        return position;
    }

    /**
     * Returns the size of the panel measured in tiles.
     *
     * @return Spot
     */
    public Spot getSize() {
        return size;
    }

    /**
     * Returns the distance of the top left corner of the panel relative from the left edge of the jFrame
     *
     * @return int
     */
    public int getPixelPosW() {
        return position.getWidth() * Tile.width;
    }

    /**
     * Returns the distance of the top left corner of the panel relative from the top edge of the jFrame
     *
     * @return int
     */
    public int getPixelPosH() {
        return position.getHeight() * Tile.height;
    }

    /**
     * Returns the height of the panel in pixels
     *
     * @return int
     */
    public int getPixelH() {
        return size.getHeight() * Tile.height;
    }

    /**
     * Returns the width of the panel in pixels
     *
     * @return int
     */
    public int getPixelW() {
        return size.getWidth() * Tile.width;
    }

    /**
     * Constructor
     *
     * @param position Position of the top left tile of the panel
     * @param size     The size of the panel measured in tiles.
     */
    public Panel(Spot position, Spot size) {
        this.position = position;
        this.size = size;
    }

    /**
     * Returns a normalized value of a click measured in pixels relative to the panel
     *
     * @param p Location of the click in pixels
     * @return Spot
     */
    public Spot normalizePlace(Spot p) {
        return new Spot(p.getHeight() - getPixelPosH(), p.getWidth() - getPixelPosW());
    }
}
