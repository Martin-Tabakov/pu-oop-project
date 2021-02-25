package Utility;

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

    public Spot add(Spot toAdd){
        return new Spot(this.getHeight() + toAdd.getHeight(),this.getWidth()+toAdd.getWidth());
    }

    public boolean isInBound(Panel p){
        return this.getHeight()< p.getSize().getHeight() && this.getWidth()< p.getSize().getWidth() &&
                this.getWidth()>-1 && this.getHeight()>-1;
    }

    @Override
    public String toString() {
        return String.format("height %d width %d", height, width);
    }
}
