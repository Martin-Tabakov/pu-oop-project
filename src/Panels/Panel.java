package Panels;

import Panels.Board.Tile;
import Utility.Spot;

public abstract class Panel {

    private final Spot position;
    private final Spot size;

    public Spot getPosition() {
        return position;
    }

    public Spot getSize() {
        return size;
    }

    public int getPixelPosW() {
        return position.getWidth() * Tile.width;
    }

    public int getPixelPosH() {
        return position.getHeight() * Tile.height;
    }

    public int getPixelH() {
        return size.getHeight()* Tile.height;
    }

    public int getPixelW() {
        return size.getWidth() * Tile.width;
    }

    public Panel(Spot position, Spot size) {
        this.position = position;
        this.size = size;
    }

    public Spot normalizePlace(Spot p) {
        return new Spot(p.getHeight() - getPixelPosH(), p.getWidth() - getPixelPosW());
    }
}
