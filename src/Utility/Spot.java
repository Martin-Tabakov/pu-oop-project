package Utility;

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

    @Override
    public String toString() {
        return String.format("height %d width %d", height, width);
    }
}
