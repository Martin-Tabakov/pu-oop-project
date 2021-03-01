package Panels.Board;

import Panels.Panel;
import Utility.Dice;
import Utility.Spot;

public class Board extends Panel {
    private final Tile[][] tiles;
    private final int casteAreaHeight = 2;

    public Tile getTile(int row, int column){
        return tiles[row][column];
    }
    public Tile getTile(Spot s){
        return tiles[s.getHeight()][s.getWidth()];
    }
    public void setTile(Spot s,Tile t){
        tiles[s.getHeight()][s.getWidth()] = t;
    }

    /**
     * Constructor for the board, where the game is being played
     * @param position The position of the top left tile of the board
     * @param size The dimensions of the board - height and width
     */
    public Board(Spot position, Spot size) {
        super(position, size);
        this.tiles = new Tile[getSize().getHeight()][getSize().getWidth()];
        this.setTiles();
    }

    /**
     * Fills the board with tiles
     */
    private void setTiles() {
        setCastleTiles(0, casteAreaHeight);
        setFieldTiles();
        setCastleTiles(getSize().getHeight() - casteAreaHeight, getSize().getHeight());
    }

    /**
     * Fills the caste areas of the board with tiles
     * @param startRow The starting row of the castle area
     * @param endRow The ending row of the castle area
     */
    private void setCastleTiles(int startRow, int endRow) {
        for (int row = startRow; row < endRow; row++) {
            for (int col = 0; col < getSize().getWidth(); col++) {
                if (row % 2 == 0)
                    tiles[row][col] = new Tile(new Spot(row, col), (col % 2 == 0) ? Type.Castle : Type.Castle2);
                if (row % 2 != 0)
                    tiles[row][col] = new Tile(new Spot(row, col), (col % 2 != 0) ? Type.Castle : Type.Castle2);
            }
        }
    }

    /**
     * Sets the tiles in-between the caste areas. This area consists of field and obstacle tiles
     */
    private void setFieldTiles() {

        int totalObstacles = Dice.throwDice(1, 5);

        for (int i = 0; i < totalObstacles; i++) {
            int row = Dice.throwDice(casteAreaHeight, getSize().getHeight() - casteAreaHeight -1);
            int column = Dice.throwDice(0, getSize().getHeight() - 1);
            tiles[row][column] = new Tile(new Spot(row, column), Type.Obstacle);
        }

        for (int row = casteAreaHeight; row < getSize().getHeight() - casteAreaHeight; row++) {
            for (int col = 0; col < getSize().getWidth(); col++)
                if (tiles[row][col] == null) tiles[row][col] = (new Tile(new Spot(row, col), Type.Field));
        }
    }
}
