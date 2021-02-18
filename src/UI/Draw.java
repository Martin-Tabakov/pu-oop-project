package UI;

import Board.*;
import Figure.Figure;
import Team.Side;
import Team.Team;
import Utility.Place;

import java.awt.*;
import java.util.ArrayList;

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
        g.setFont(new Font("Arial", Font.BOLD, Tile.height / 2));
    }

    public void drawBoard(Board board) {
        g.translate(boardPlacement.getColumn(), boardPlacement.getRow());
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(0, 0, Tile.width * board.getWidth(), Tile.height * board.getHeight());

        for (Tile tile : board.tiles) { drawTile(tile);
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

    ArrayList<Integer> figs = new ArrayList<>();
    Team player;

    public void drawFigureHolder(int boardHeight,int boardWidth) {
        g.translate(figureHolderPlacement.getRow(), figureHolderPlacement.getColumn());
        g.setColor(getTileColor(Type.Castle2));
        g.fillRect(1, 1, Tile.width * 3-1, Tile.height * 2-1);
        g.setColor(getTileColor(Type.Castle));
        g.drawRect(0, 0, Tile.width * 3, Tile.height * 2);
        //g.drawLine(0,Tile.height,Tile.width * 3,Tile.height);
        if(player!= null) {
            drawFiguresInHolder();
            drawPlaceableTiles(boardHeight,boardWidth);
        }

    }
    private void drawFiguresInHolder(){
        g.setColor(getNationCol(player.getSide()));
        g.drawString("Heroes",(int) (Tile.width / 1.5),(int) (Tile.height / 1.5));
        drawTile(new Tile(new Place(1,0),Type.Castle));
        drawTile(new Tile(new Place(1,1),Type.Castle2));
        drawTile(new Tile(new Place(1,2),Type.Castle));
        if (figs.get(0) < 2) {
            int coordX = Tile.width / 3;
            int coordY = Tile.height + (int) (Tile.height / 1.5);

            //drawTile(new Tile(new Place(1,0),Type.Castle));

            g.setColor(getNationCol(player.getSide()));
            g.drawString("D", coordX, coordY);
        }
        if (figs.get(1) < 2) {
            int coordX = Tile.width+Tile.width / 3;
            int coordY = Tile.height + (int) (Tile.height / 1.5);

            //drawTile(new Tile(new Place(1,2),Type.Castle2));

            g.setColor(getNationCol(player.getSide()));
            g.drawString("K", coordX, coordY);
        }
        if (figs.get(2) < 2) {
            int coordX = Tile.width*2+Tile.width / 3;
            int coordY = Tile.height + (int) (Tile.height / 1.5);

            //drawTile(new Tile(new Place(1,2),Type.Castle));

            g.setColor(getNationCol(player.getSide()));
            g.drawString("E", coordX, coordY);
        }
    }

    private void drawPlaceableTiles(int boardHeight,int boardWidth){
        g.translate(-(boardWidth+1)*Tile.width,0);
        int row = (player.getSide()==Side.North)?0:boardHeight-2;
        for (int i = row; i < row+2; i++) {
            for(int j=0;j<boardWidth;j++){
                if(!player.hasFigureOnPos(new Place(i,j))) drawTile(new Place(i,j),Type.Placeable);
            }
        }
    }
    private void drawTile(Tile tile){
        g.setColor(Colors.castle.value);
        g.drawRect(Tile.width * tile.getPos().getColumn(), Tile.height * tile.getPos().getRow(), Tile.width, Tile.height);
        g.setColor(getTileColor(tile.getType()));
        g.fillRect(Tile.width * tile.getPos().getColumn()+1, Tile.height * tile.getPos().getRow()+1, Tile.width-1, Tile.height-1);
    }
    private void drawTile(Place p, Type t){

        g.setColor(getTileColor(t));
        g.fillRect(Tile.width * p.getColumn()+1, Tile.height * p.getRow()+1, Tile.width-1, Tile.height-1);
    }

    public void setFigureHolderFigures(ArrayList<Integer> figs, Team currentPlayer) {
        this.figs = figs;
        this.player = currentPlayer;
    }
}