package UI;

import Panels.Board.*;
import Panels.*;
import Figure.*;
import Panels.Log.Entry;
import Panels.Log.Log;
import Team.*;
import Utility.Spot;

import java.awt.*;
import java.util.ArrayList;

public class Draw {
    private Graphics g;

    public Draw() {
    }

    public void setGraphic(Graphics g) {
        this.g = g;
        g.setFont(new Font("Arial", Font.BOLD, Math.min(Tile.height, Tile.width) / 2));
    }

    public void drawBoard(Board board) {
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(board.getPixelPosW(), board.getPixelPosH(), board.getPixelW(), board.getPixelH());

        for (Tile tile : board.tiles) {
            drawTile(tile, board.getPosition());
        }
    }

    public void drawTeam(Team team, Spot boardPosition) {
        for (Figure figure : team.getFigures()) {
            g.setColor(getNationCol(team.getSide()));

            int spotX = (figure.getPlacement().getWidth() + boardPosition.getWidth()) * Tile.width + Tile.width / 3;
            int spotY = (figure.getPlacement().getHeight() + boardPosition.getHeight()) * Tile.height + (int) (Tile.height / 1.5);
            g.drawString(figure.getSymbols(), spotX, spotY);
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

    public void drawFigureHolder(FigureHolder fh, Spot boardPosition) {
        g.setColor(getTileColor(Type.Castle2));
        g.fillRect(fh.getPixelPosW()+1, fh.getPixelPosH()+1, fh.getPixelW() - 1, fh.getPixelH() - 1);
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(fh.getPixelPosW(), fh.getPixelPosH(), fh.getPixelW(), fh.getPixelH());

        if (fh.getCurrentPlacer() != null) {
            drawFiguresInHolder(fh);
            drawPlaceableTiles(fh,boardPosition);
        }
    }

    private void drawFiguresInHolder(FigureHolder fh) {
        g.setColor(getNationCol(fh.getCurrentPlacer().getSide()));
        g.drawString("Heroes", fh.getPixelPosW()+(int) (Tile.width / 1.5),fh.getPixelPosH ()+(int) (Tile.height / 1.5));

        drawTile(new Tile(new Spot(1, 0), Type.Castle), fh.getPosition());
        drawTile(new Tile(new Spot(1, 1), Type.Castle2), fh.getPosition());
        drawTile(new Tile(new Spot(1, 2), Type.Castle), fh.getPosition());

        for (int i = 0; i < 3; i++) {

            if (fh.getPlacedFigures().get(i).getValue() < 2) {
                int coordX = Tile.width * i + Tile.width / 3 + fh.getPixelPosW();
                int coordY = Tile.height + (int) (Tile.height / 1.5)+ fh.getPixelPosH();

                g.setColor(getNationCol(fh.getCurrentPlacer().getSide()));
                g.drawString(fh.getPlacedFigures().get(i).getKey().getSymbols(), coordX, coordY);
            }
        }
    }

    private void drawPlaceableTiles(FigureHolder fh,Spot boardPosition) {

        for (Spot p : fh.getCurrentPlacer().getPlaceableTiles()) {
            drawTile(p, Type.Placeable,boardPosition);
        }
    }

    private void drawTile(Tile tile, Spot parentPosition) {
        g.setColor(Colors.castle.value);
        g.drawRect((tile.getPos().getWidth() + parentPosition.getWidth()) * Tile.width, (tile.getPos().getHeight() + parentPosition.getHeight()) * Tile.height, Tile.width, Tile.height);
        g.setColor(getTileColor(tile.getType()));
        g.fillRect((tile.getPos().getWidth() + parentPosition.getWidth()) * Tile.width + 1, (tile.getPos().getHeight() + parentPosition.getHeight()) * Tile.height + 1, Tile.width - 1, Tile.height - 1);
    }

    private void drawTile(Spot p, Type t,Spot parentPosition) {

        g.setColor(getTileColor(t));
        g.fillRect((p.getWidth() + parentPosition.getWidth()) * Tile.width + 1, (p.getHeight() + parentPosition.getHeight()) * Tile.height + 1, Tile.width - 1, Tile.height - 1);
    }

    public void drawLogWindow(Log log) {
        g.setColor(getTileColor(Type.Castle2));
        g.fillRect(log.getPixelPosW() + 1, log.getPixelPosH() + 1, log.getPixelW() - 1, log.getPixelH() - 1);
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(log.getPixelPosW(), log.getPixelPosH(), log.getPixelW(), log.getPixelH());
        g.drawString("Log", log.getPixelPosW() + 10, log.getPixelPosH() + (int) (Tile.height / 1.5));
        g.drawLine(log.getPixelPosW(), log.getPixelPosH()+Tile.height, log.getPixelPosW()+log.getPixelW(), log.getPixelPosH()+Tile.height);

        Font f = g.getFont();
        g.setFont(new Font("Arial", Font.PLAIN, Math.min(Tile.height, Tile.width) / 6));

        ArrayList<Entry> entries = log.getEvents(3);
        for (int i = 0; i < entries.size(); i++) {
            g.drawString(entries.get(i).toString(),log.getPixelPosW()+Tile.width / 10, log.getPixelPosH()+Tile.height + (Tile.height / 2) * (i + 1));
        }
    }
}