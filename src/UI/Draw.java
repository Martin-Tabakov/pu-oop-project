package UI;

import Panels.ActionMenu.ActionMenu;
import Panels.Board.*;
import Figure.*;
import Panels.FigureHolder.FigureHolder;
import Panels.Log.Entries.Entry;
import Panels.Log.Log;
import Panels.PointsDisplay.PointsDisplay;
import Team.*;
import Utility.Pair;
import Utility.Spot;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Draw {
    private Graphics g;

    /**
     * Sets the graphic component
     *
     * @param g Graphic component
     */
    public void setGraphic(Graphics g) {
        this.g = g;
        g.setFont(new Font("Arial", Font.BOLD, Math.min(Tile.height, Tile.width) / 2));
    }

    /**
     * Draws the game board
     *
     * @param board Board
     */
    public void drawBoard(Board board) {
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(board.getPixelPosW(), board.getPixelPosH(), board.getPixelW(), board.getPixelH());

        for (int row = 0; row < board.getSize().getHeight(); row++)
            for (int col = 0; col < board.getSize().getWidth(); col++)
                drawTile(board.getTile(row, col), board.getPosition());
    }

    /**
     * Draws a teams figures on the board
     *
     * @param team     Team
     * @param boardPos The position of the board where the figures will be standing
     */
    public void drawTeam(Team team, Spot boardPos) {
        g.setColor(getNationCol(team.getSide()));

        for (Figure figure : team.getFigures()) {
            int spotX = (figure.getPlace().getWidth() + boardPos.getWidth()) * Tile.width + Tile.width / 3;
            int spotY = (figure.getPlace().getHeight() + boardPos.getHeight()) * Tile.height + (int) (Tile.height / 1.5);
            g.drawString(figure.getSymbols(), spotX, spotY);
        }
    }

    /**
     * Gets the color based on the tile type
     *
     * @param type Tile type
     * @return The color that correspond to that tile type
     */
    private Color getTileColor(Type type) {
        return switch (type) {
            case Castle -> Colors.castle.value;
            case Castle2 -> Colors.castle2.value;
            case Field -> Colors.field.value;
            case Placeable -> Colors.placeable.value;
            case Obstacle -> Colors.obstacle.value;
        };
    }

    /**
     * Gets the color based on the nations side
     *
     * @param side Nations side
     * @return The color that corresponds to the side
     */
    private Color getNationCol(Side side) {
        return switch (side) {
            case North -> Colors.north.value;
            case South -> Colors.south.value;
        };
    }

    /**
     * Draws the figure holder and the placeable tiles for the selected figure from the figure holder
     *
     * @param fh            figure holder
     * @param boardPosition Board position
     */
    public void drawFigureHolder(FigureHolder fh, Spot boardPosition) {
        g.setColor(getTileColor(Type.Castle2));
        g.fillRect(fh.getPixelPosW() + 1, fh.getPixelPosH() + 1, fh.getPixelW() - 1, fh.getPixelH() - 1);
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(fh.getPixelPosW(), fh.getPixelPosH(), fh.getPixelW(), fh.getPixelH());

        if (fh.getCurrentPlacer() != null) {
            drawFiguresInHolder(fh);
            drawPlaceableTiles(fh, boardPosition);
        }
    }

    /**
     * Draws the available figures in the figure holder that the player can place on the game board
     *
     * @param fh figure holder
     */
    private void drawFiguresInHolder(FigureHolder fh) {
        g.setColor(getNationCol(fh.getCurrentPlacer().getSide()));
        g.drawString("Heroes", fh.getPixelPosW() + (int) (Tile.width / 1.5), fh.getPixelPosH() + (int) (Tile.height / 1.5));

        drawTile(new Tile(new Spot(1, 0), Type.Castle), fh.getPosition());
        drawTile(new Tile(new Spot(1, 1), Type.Castle2), fh.getPosition());
        drawTile(new Tile(new Spot(1, 2), Type.Castle), fh.getPosition());

        for (int i = 0; i < 3; i++) {

            if (fh.getPlacedFigures().get(i).getValue() < 2) {
                int coordX = Tile.width * i + Tile.width / 3 + fh.getPixelPosW();
                int coordY = Tile.height + (int) (Tile.height / 1.5) + fh.getPixelPosH();

                g.setColor(getNationCol(fh.getCurrentPlacer().getSide()));
                g.drawString(fh.getPlacedFigures().get(i).getKey().getSymbols(), coordX, coordY);
            }
        }
    }

    /**
     * Draws the tiles where a selected figure from the figure holder can be placed
     *
     * @param fh            Figure holder
     * @param boardPosition Board position
     */
    private void drawPlaceableTiles(FigureHolder fh, Spot boardPosition) {

        for (Spot p : fh.getCurrentPlacer().getPlaceableTiles()) {
            drawTile(p, Type.Placeable, boardPosition);
        }
    }

    /**
     * Draws a tile
     *
     * @param tile     Tile object
     * @param panelPos The position of the panel where the tile will be drawn
     */
    private void drawTile(Tile tile, Spot panelPos) {
        int coordX = (tile.getPos().getWidth() + panelPos.getWidth()) * Tile.width;
        int coordY = (tile.getPos().getHeight() + panelPos.getHeight()) * Tile.height;

        g.setColor(Colors.castle.value);
        g.drawRect(coordX, coordY, Tile.width, Tile.height);
        g.setColor(getTileColor(tile.getType()));
        g.fillRect(coordX + 1, coordY + 1, Tile.width - 1, Tile.height - 1);
    }

    /**
     * Fills a tile with a specific types color
     * @param p Position of the tile
     * @param t Type of the tile
     * @param panelPos The position of the panel where the tile will be drawn
     */
    private void drawTile(Spot p, Type t, Spot panelPos) {
        int coordX = (p.getWidth() + panelPos.getWidth()) * Tile.width;
        int coordY = (p.getHeight() + panelPos.getHeight()) * Tile.height;

        g.setColor(getTileColor(t));
        g.fillRect(coordX + 1, coordY + 1, Tile.width - 1, Tile.height - 1);
    }

    /**
     * Draws a log window
     * @param log Log window
     */
    public void drawLogWindow(Log log) {
        g.setColor(getTileColor(Type.Castle2));
        g.fillRect(log.getPixelPosW() + 1, log.getPixelPosH() + 1, log.getPixelW() - 1, log.getPixelH() - 1);
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(log.getPixelPosW(), log.getPixelPosH(), log.getPixelW(), log.getPixelH());
        g.drawString("Log", log.getPixelPosW() + 10, log.getPixelPosH() + (int) (Tile.height / 1.5));
        g.drawLine(log.getPixelPosW(), log.getPixelPosH() + Tile.height, log.getPixelPosW() + log.getPixelW(), log.getPixelPosH() + Tile.height);

        Font f = g.getFont();
        g.setFont(new Font("Arial", Font.PLAIN, Math.min(Tile.height, Tile.width) / 6));

        ArrayList<Entry> entries = Log.getEntries(3);
        for (int i = 0; i < entries.size(); i++) {
            int coordX = log.getPixelPosW() + Tile.width / 10;
            int coordY = log.getPixelPosH() + Tile.height + (Tile.height / 2) * (i + 1);
            g.drawString(entries.get(i).toString(), coordX, coordY);
        }
        g.setFont(f);
    }

    /**
     * Draws an action menu window
     * @param am ActionMenu
     * @param side The side of the current player
     */
    public void drawActionMenu(ActionMenu am, Side side) {
        g.setColor(getTileColor(Type.Castle2));
        g.fillRect(am.getPixelPosW() + 1, am.getPixelPosH() + 1, am.getPixelW() - 1, am.getPixelH() - 1);
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(am.getPixelPosW(), am.getPixelPosH(), am.getPixelW(), am.getPixelH());
        g.setColor(getNationCol(side));
        g.drawString("Attack", am.getPixelPosW() + 10, am.getPixelPosH() + (int) (Tile.height / 1.5));
        g.drawString("Move", am.getPixelPosW() + 10, am.getPixelPosH() + (int) (Tile.height / 1.5) + Tile.height);
        g.drawString("Heal", am.getPixelPosW() + 10, am.getPixelPosH() + (int) (Tile.height / 1.5) + Tile.height * 2);
    }

    /**
     * Draws the available places where a figure can move to
     * @param spots List of available places
     * @param panelPos The position of the panel where the tile will be drawn
     */
    public void drawMoveToPlaces(LinkedList<Pair<Spot, Integer>> spots, Spot panelPos) {
        for (Pair<Spot, Integer> p : spots) {
            drawTile(p.getKey(), Type.Placeable, panelPos);
        }
    }

    /**
     * Draws the panel where the points for both teams are shown
     * @param pd PointsDisplay
     * @param t Team north
     * @param t2 Team south
     */
    public void drawPointsDisplay(PointsDisplay pd, Team t, Team t2) {
        g.setColor(getTileColor(Type.Castle2));
        g.fillRect(pd.getPixelPosW() + 1, pd.getPixelPosH() + 1, pd.getPixelW() - 1, pd.getPixelH() - 1);
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(pd.getPixelPosW(), pd.getPixelPosH(), pd.getPixelW(), pd.getPixelH());

        g.drawString("Points", pd.getPixelPosW() + 10, pd.getPixelPosH() + (int) (Tile.height / 1.5));
        g.setColor(getNationCol(t.getSide()));
        g.drawString(String.valueOf(t.getPoints()), pd.getPixelPosW() + 10, pd.getPixelPosH() + (int) (Tile.height / 1.5) + Tile.height);
        g.setColor(getNationCol(t2.getSide()));
        g.drawString(String.valueOf(t2.getPoints()), pd.getPixelPosW() + 10, pd.getPixelPosH() + (int) (Tile.height / 1.5) + Tile.height * 2);
    }

    /**
     * Draws the places where a figure can execute an attack
     * @param attackablePlaces List of attackable places
     * @param panelPos The position of the panel where the tile will be drawn
     */
    public void drawAttackablePlaces(ArrayList<Spot> attackablePlaces, Spot panelPos) {
        for (Spot s : attackablePlaces) {
            drawTile(s, Type.Placeable, panelPos);
        }
    }
}