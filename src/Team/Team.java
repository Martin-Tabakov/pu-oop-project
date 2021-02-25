package Team;

import Figure.*;
import Panels.Board.Board;
import Utility.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Team {

    private final Side side;
    private ArrayList<Figure> figures;
    private ArrayList<Spot> placeableTiles;

    private int totalFigures = 6;

    public int getTotalFigures() {
        return totalFigures;
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public ArrayList<Spot> getPlaceableTiles() {
        return placeableTiles;
    }

    public Side getSide() {
        return this.side;
    }

    public Team(Side side) {
        this.side = side;
        this.figures = new ArrayList<>(totalFigures);
    }

    public void setPlaceableTiles(int boardWidth, int boardHeight) {
        placeableTiles = new ArrayList<>();
        int row = (side == Side.North) ? 0 : boardHeight - 2;
        for (int i = row; i < row + 2; i++) {
            for (int j = 0; j < boardWidth; j++) {
                placeableTiles.add(new Spot(i, j));
            }
        }
    }

    public void addFig(Spot click, Pair<Figure, Integer> figData, Spot toRemove) {
        placeableTiles.remove(toRemove);
        Figure f = figData.getKey();
        f.setPlacement(click);
        System.out.println(f);
        figures.add(f);
    }

    public Figure getFigureOnPos(Spot s) {

        for (Figure f : figures) {
            if (f.hasSamePos(s)) return f;
        }
        return null;
    }

    public boolean hasFigureOnPos(Spot p) {
        for (Figure f : figures) {
            if (f.hasSamePos(p)) return true;
        }
        return false;
    }

    public boolean attackFig(Figure f) {
        if (!selectedDest) {
            selectedDest = true;
            return false;
        }

        selectedDest = false;
        return f.attack();
    }

    boolean selectedDest = false;

    public boolean moveFig(Figure f, Spot moveToPos, Board board) {
        if (!selectedDest) {
            fillVisitedPlaces(f,board);
            selectedDest = true;
            return false;
        }
        if (visitedPlaces.stream().anyMatch(o -> o.getKey().hasEqualValues(f.getPlacement()))) {
            selectedDest = false;
            return f.move(moveToPos);
        }
        return false;
    }

    public LinkedList<Pair<Spot,Integer>> getVisitedPlaces(){
        return visitedPlaces;
    }

    public void resetVisited(){
        visitedPlaces = null;
    }

    LinkedList<Pair<Spot, Integer>> visitedPlaces = null;
    public void fillVisitedPlaces(Figure f, Board board) {
        //TODO Check if the selected tile is clear and the selected figure can be moved there
        //TODO 2 Include impassable tiles in the search
        Queue<Pair<Spot, Integer>> toVisit = new LinkedList<>();
        visitedPlaces = new LinkedList<>();
        toVisit.add(new Pair<>(f.getPlacement(), 1));

        ArrayList<Spot> neighbors = new ArrayList<>();
        neighbors.add(new Spot(-1, 0));
        neighbors.add(new Spot(1, 0));
        neighbors.add(new Spot(0, 1));
        neighbors.add(new Spot(0, -1));

        while (toVisit.peek() != null) {

            Pair<Spot, Integer> currentSpot = toVisit.peek();
            toVisit.remove();
            visitedPlaces.add(new Pair<>(currentSpot.getKey(), currentSpot.getValue()));

            for (int i = 0; i < neighbors.size(); i++) {
                Spot currentN;
                if ((currentN = currentSpot.getKey().add(neighbors.get(i))) != null && currentN.isInBound(board)) {
                    if (board.tiles[currentN.getHeight()][currentN.getWidth()].getPlacedFig() == null && currentSpot.getValue() <= f.getSpeed())
                        if (visitedPlaces.stream().noneMatch(a -> a.getKey().hasEqualValues(currentN))) {
                            toVisit.add(new Pair<>(currentN, currentSpot.getValue() + 1));
                        }
                }
            }
        }
    }

    public boolean healFig(Figure f, int healthRegen) {
        return f.heal(healthRegen);
    }
}
