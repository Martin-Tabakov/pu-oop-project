package Team;

import Figure.*;
import Utility.*;

import java.util.ArrayList;

public class Team {

    private final Side side;
    private ArrayList<Figure> figures;
    private ArrayList<Place> placeableTiles;

    private int totalFigures = 6;

    public int getTotalFigures() {
        return totalFigures;
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public ArrayList<Place> getPlaceableTiles() {
        return placeableTiles;
    }

    public Side getSide() {
        return this.side;
    }

    public Team(Side side) {
        this.side = side;
        this.figures = new ArrayList<>(totalFigures);
    }

    public void setPlaceableTiles(int boardWidth,int boardHeight) {
        placeableTiles = new ArrayList<>();
        int row = (side == Side.North) ? 0 : boardHeight - 2;
        for (int i = row; i < row + 2; i++) {
            for (int j = 0; j < boardWidth; j++) {
                placeableTiles.add(new Place(i,j));
            }
        }
    }
    public void addFig(Place click, Pair<Figure, Integer> figData,Place toRemove){
        placeableTiles.remove(toRemove);
        Figure f = figData.getKey();
        f.setPlacement(click);
        System.out.println(f);
        figures.add(f);
    }

    public boolean hasFigureOnPos(Place p){
        for (Figure f: figures) {
            if(f.hasSamePos(p)) return true;
        }
        return false;
    }
}
