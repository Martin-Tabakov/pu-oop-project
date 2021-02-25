package Panels.Board;

import Panels.Panel;
import Utility.Dice;
import Utility.Spot;

public class Board extends Panel {
    public Tile[][] tiles;

    public Board(Spot position, Spot size) {
        super(position,size);
        this.tiles = new Tile[getSize().getHeight()][getSize().getWidth()];
        this.setTiles();
    }


    private void setTiles() {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < getSize().getWidth(); col++) {
                if (row % 2 == 0)
                    tiles[row][col] = ((col % 2 == 0) ? new Tile(new Spot(row, col), Type.Castle) : new Tile(new Spot(row, col), Type.Castle2));
                if (row % 2 != 0)
                    tiles[row][col] = ((col % 2 != 0) ? new Tile(new Spot(row, col), Type.Castle) : new Tile(new Spot(row, col), Type.Castle2));
            }
        }

        int totalObstacles = Dice.throwDice(1,5);

        for (int row = 2; row < getSize().getHeight()-2; row++) {
            for (int col = 0; col < getSize().getWidth(); col++) {

                tiles[row][col] = (new Tile(new Spot(row, col), Type.Field));
            }
        }

        for (int row = getSize().getHeight()-2; row < getSize().getHeight(); row++) {
            for (int col = 0; col < getSize().getWidth(); col++) {
                if (row % 2 == 0)
                    tiles[row][col] = ((col % 2 == 0) ? new Tile(new Spot(row, col), Type.Castle) : new Tile(new Spot(row, col), Type.Castle2));
                if (row % 2 != 0)
                    tiles[row][col] = ((col % 2 != 0) ? new Tile(new Spot(row, col), Type.Castle) : new Tile(new Spot(row, col), Type.Castle2));
            }
        }

    }
}
