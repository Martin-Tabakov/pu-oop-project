package UI;

import Board.*;
import Figure.Figure;
import Team.Side;
import Team.Team;

import java.awt.*;

public class Draw {
    private Graphics g;

    public Draw() {
    }

    public void setGraphic(Graphics g) {
        this.g = g;
    }

    public void drawBoard(Board board) {
        g.translate(Tile.width, Tile.height);
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(-1,-1,Tile.width*board.getWidth()+1,Tile.height*board.getHeight()+1);

        for (Tile tile: board.tiles) {
            g.setColor(Colors.castle.value);
            g.drawRect(Tile.width * tile.getPos().getColumn()-1, Tile.height * tile.getPos().getRow()-1, Tile.width+1, Tile.height+1);
            g.setColor(getTileColor(tile.getType()));
            g.fillRect(Tile.width * tile.getPos().getColumn(), Tile.height * tile.getPos().getRow(), Tile.width, Tile.height);
        }
    }

    public void drawTeam(Team team) {
        for(Figure figure : team.getFigures()){
            g.setColor(getNationCol(team.getSide()));
            g.setFont(new Font("Arial",Font.BOLD,Tile.height/2));
            int coordX = figure.getPlacement().getColumn()*Tile.width+Tile.width/3;
            int coordY = figure.getPlacement().getRow()*Tile.height+(int)(Tile.height/1.5);
            g.drawString(figure.getSymbols(),coordX,coordY);
        }
    }

    private Color getTileColor(Type type){
        return switch (type){
            case Castle -> Colors.castle.value;
            case Castle2 -> Colors.castle2.value;
            case Field -> Colors.field.value;
        };
    }

    private Color getNationCol(Side side) {
        return switch (side) {
            case North -> Colors.north.value;
            case South -> Colors.south.value;
        };
    }
}