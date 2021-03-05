package Utility;

import Panels.Board.Tile;
import Panels.Panel;

public class Spot {
    private int height;
    private int width;

    /**
     * Constructor for a Spot instance, which holds two integer values
     * @param height height
     * @param width width
     */
    public Spot(int height, int width) {
        this.height = height;
        this.width = width;
    }

    /**
     * Gets the value of height.
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the value of the height to a new one
     * @param height height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the value of width.
     * @return width
     */
    public int getWidth() {
        return width;
    }
    /**
     * Sets the value of the width to a new one
     * @param width width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Checks whether a this Spot instance has the same value as another passed Spot object
     * @param f The Spot, whose values will be checked
     * @return true if the values are the same, otherwise - false
     */
    public boolean hasEqualValues(Spot f) {
        return width == f.getWidth() &&
                height == f.getHeight();
    }

    /**
     * Checks whether two Spot objects have the same values
     * @param a Spot object, whose values will be checked
     * @param b Spot object, whose values will be checked
     * @return true if the values are the same, otherwise - false
     */
    public static boolean areValuesEqual(Spot a,Spot b) {
        return a.hasEqualValues(b);
    }

    /**
     * Adds another instances values to this one and return a new object with the new values
     * @param toAdd Object, whose values will be added to the current ones
     * @return new Spot, containing the new values after their calculated sum
     */
    public Spot addTo(Spot toAdd){
        return new Spot(this.getHeight() + toAdd.getHeight(),this.getWidth()+toAdd.getWidth());
    }

    /**
     * Checks whether the current values are within a panels boundaries
     * @param p Panel Object
     * @return true if the Spots values are within the boundaries of the panel
     */
    public boolean isInBound(Panel p){
        return this.getHeight()< p.getSize().getHeight() && this.getWidth()< p.getSize().getWidth() &&
                this.getWidth()>-1 && this.getHeight()>-1;
    }

    /**
     * Converts the values of a spot, containing pixel position to Tile position
     * @param p Spot, containing the position of a pixel
     * @return Spot, holding the newly converted Tile position
     */
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
