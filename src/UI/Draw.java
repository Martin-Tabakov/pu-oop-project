package UI;

import Board.*;
import Figure.Figure;
import Team.*;
import Utility.Place;

import java.awt.*;

public class Draw {
    private Graphics g;
    private final Place boardPlacement;
    private final Place figureHolderPlacement;

    public Draw(Place boardPlacement, Place figureHolderPlacement) {
        this.boardPlacement = boardPlacement;
        this.figureHolderPlacement = figureHolderPlacement;
    }

    public void setGraphic(Graphics g) {
        this.g = g;
        g.setFont(new Font("Arial", Font.BOLD, Math.min(Tile.height,Tile.width) / 2));
    }

    public void drawBoard(Board board) {
        g.translate(boardPlacement.getColumn(), boardPlacement.getRow());
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(0, 0, Tile.width * board.getWidth(), Tile.height * board.getHeight());

        for (Tile tile : board.tiles) {
            drawTile(tile);
        }
    }

    public void drawTeam(Team team) {
        for (Figure figure : team.getFigures()) {
            g.setColor(getNationCol(team.getSide()));

            int coordX = figure.getPlacement().getColumn() * Tile.width + Tile.width / 3;
            int coordY = figure.getPlacement().getRow() * Tile.height + (int) (Tile.height / 1.5);
            g.drawString(figure.getSymbols(), coordX, coordY);
        }
    }

    private Color getTileColor(Type type) {
        return switch (type) {
            case Castle -> Colors.castle.value;
            case Castle2 -> Colors.castle2.value;
            case Field -> Colors.field.value;
            case Placeable -> Colors.placeable.value;
            case Obstacle -> Colors.obstacle.value;
        };
    }

    private Color getNationCol(Side side) {
        return switch (side) {
            case North -> Colors.north.value;
            case South -> Colors.south.value;
        };
    }

    public void drawFigureHolder(int boardHeight, int boardWidth, FigureHolder fh) {
        g.translate(figureHolderPlacement.getRow(), figureHolderPlacement.getColumn());
        g.setColor(getTileColor(Type.Castle2));
        g.fillRect(1, 1, Tile.width * 3 - 1, Tile.height * 2 - 1);
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(0, 0, Tile.width * 3, Tile.height * 2);

        if (fh.getCurrentPlacer() != null) {
            drawFiguresInHolder(fh);
            drawPlaceableTiles(boardHeight, boardWidth, fh);
        }
    }

    private void drawFiguresInHolder(FigureHolder fh) {
        g.setColor(getNationCol(fh.getCurrentPlacer().getSide()));
        g.drawString("Heroes", (int) (Tile.width / 1.5), (int) (Tile.height / 1.5));

        drawTile(new Tile(new Place(1, 0), Type.Castle));
        drawTile(new Tile(new Place(1, 1), Type.Castle2));
        drawTile(new Tile(new Place(1, 2), Type.Castle));

        for (int i = 0; i < 3; i++) {

            if (fh.getPlacedFigures().get(i).getValue() < 2) {
                int coordX = Tile.width * i + Tile.width / 3;
                int coordY = Tile.height + (int) (Tile.height / 1.5);

                g.setColor(getNationCol(fh.getCurrentPlacer().getSide()));
                g.drawString(fh.getPlacedFigures().get(i).getKey().getSymbols(), coordX, coordY);
            }
        }
    }

    private void drawPlaceableTiles(int boardHeight, int boardWidth, FigureHolder fh) {
        g.translate(-(boardWidth + 1) * Tile.width, 0);

        for (Place p : fh.getCurrentPlacer().getPlaceableTiles()) {
            drawTile(p,Type.Placeable);
        }
        /*
        int row = (fh.getCurrentPlacer().getSide() == Side.North) ? 0 : boardHeight - 2;
        for (int i = row; i < row + 2; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (!fh.getCurrentPlacer().hasFigureOnPos(new Place(i, j))) drawTile(new Place(i, j), Type.Placeable);
            }
        }*/
    }

    private void drawTile(Tile tile) {
        g.setColor(Colors.castle.value);
        g.drawRect(Tile.width * tile.getPos().getColumn(), Tile.height * tile.getPos().getRow(), Tile.width, Tile.height);
        g.setColor(getTileColor(tile.getType()));
        g.fillRect(Tile.width * tile.getPos().getColumn() + 1, Tile.height * tile.getPos().getRow() + 1, Tile.width - 1, Tile.height - 1);
    }

    private void drawTile(Place p, Type t) {

        g.setColor(getTileColor(t));
        g.fillRect(Tile.width * p.getColumn() + 1, Tile.height * p.getRow() + 1, Tile.width - 1, Tile.height - 1);
    }

}