package Panels.Board;

import Panels.Panel;
import Utility.Spot;

import java.util.ArrayList;

public class Board extends Panel {
    public ArrayList<Tile> tiles;

    public Board(Spot position, Spot size) {
        super(position,size);
        this.tiles = new ArrayList<>();
        this.setTiles();
    }


    private void setTiles() {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < getSize().getWidth(); col++) {
                if (row % 2 == 0)
                    tiles.add((col % 2 == 0) ? new Tile(new Spot(row, col), Type.Castle) : new Tile(new Spot(row, col), Type.Castle2));
                if (row % 2 != 0)
                    tiles.add((col % 2 != 0) ? new Tile(new Spot(row, col), Type.Castle) : new Tile(new Spot(row, col), Type.Castle2));
            }
        }

        for (int row = 2; row < getSize().getHeight()-2; row++) {
            for (int col = 0; col < getSize().getWidth(); col++) {
                if(row == 2 && (col >0 && col<3)) tiles.add(new Tile(new Spot(row,col), Type.Obstacle));
                else tiles.add(new Tile(new Spot(row, col), Type.Field));
            }
        }

        for (int row = getSize().getHeight()-2; row < getSize().getHeight(); row++) {
            for (int col = 0; col < getSize().getWidth(); col++) {
                if (row % 2 == 0)
                    tiles.add((col % 2 == 0) ? new Tile(new Spot(row, col), Type.Castle) : new Tile(new Spot(row, col), Type.Castle2));
                if (row % 2 != 0)
                    tiles.add((col % 2 != 0) ? new Tile(new Spot(row, col), Type.Castle) : new Tile(new Spot(row, col), Type.Castle2));
            }
        }

    }
}
