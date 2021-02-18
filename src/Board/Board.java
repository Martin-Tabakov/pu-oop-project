package Board;

import Utility.Place;

import java.util.ArrayList;

public class Board {
    private final int width;
    private final int height;
    public ArrayList<Tile> tiles;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new ArrayList<>();
        this.setTiles();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void setTiles() {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < width; col++) {
                if (row % 2 == 0)
                    tiles.add((col % 2 == 0) ? new Tile(new Place(row, col), Type.Castle) : new Tile(new Place(row, col), Type.Castle2));
                if (row % 2 != 0)
                    tiles.add((col % 2 != 0) ? new Tile(new Place(row, col), Type.Castle) : new Tile(new Place(row, col), Type.Castle2));
            }
        }

        for (int row = 2; row < height-2; row++) {
            for (int col = 0; col < width; col++) {
                if(row == 2 && (col >0 && col<3)) tiles.add(new Tile(new Place(row,col), Type.Obstacle));
                else tiles.add(new Tile(new Place(row, col), Type.Field));
            }
        }

        for (int row = height-2; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row % 2 == 0)
                    tiles.add((col % 2 == 0) ? new Tile(new Place(row, col), Type.Castle) : new Tile(new Place(row, col), Type.Castle2));
                if (row % 2 != 0)
                    tiles.add((col % 2 != 0) ? new Tile(new Place(row, col), Type.Castle) : new Tile(new Place(row, col), Type.Castle2));
            }
        }

    }
}
