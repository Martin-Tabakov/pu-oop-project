package UI;

import Board.Board;
import Board.Tile;
import Team.Side;
import Team.Team;
import java.awt.Color;
import java.awt.Graphics;

public class Draw {
    private Graphics g;

    public Draw() {
    }

    public void setGraphic(Graphics g) {
        this.g = g;
    }

    public void drawBoard(Board board) {
        g.translate(75, 75);
        for (Tile tile: board.tiles) {

            g.drawRect(75 * tile.pos.getColumn(), 75 * tile.pos.getRow(), 75, 75);
        }

    }

    public void drawTeam(Team team) {
    }

    private Color getCol(Side side) {
        return switch (side) {
            case North -> Colors.north.value;
            case South -> Colors.south.value;
        };
    }
}