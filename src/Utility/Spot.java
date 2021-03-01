package Utility;

import Panels.Board.Tile;
import Panels.Panel;

public class Spot {
    private int height;
    private int width;

    public Spot(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean hasEqualValues(Spot f) {
        return width == f.getWidth() &&
                height == f.getHeight();
    }

    public static boolean areValuesEqual(Spot a,Spot b) {
        return a.hasEqualValues(b);
    }

    public Spot addTo(Spot toAdd){
        return new Spot(this.getHeight() + toAdd.getHeight(),this.getWidth()+toAdd.getWidth());
    }

    public boolean isInBound(Panel p){
        return this.getHeight()< p.getSize().getHeight() && this.getWidth()< p.getSize().getWidth() &&
                this.getWidth()>-1 && this.getHeight()>-1;
    }
    public static Spot convertToPos(Spot p) {
        if(p.getWidth()<0) p.setWidth(-Tile.width);
        if(p.getHeight()<0) p.setWidth(-Tile.height);
        return new Spot(p.getHeight() / Tile.height, p.getWidth() / Tile.width);
    }

    @Override
    public String toString() {
        return String.format("height %d width %d", height, width);
    }
}
