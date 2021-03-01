package Team;

import Figure.*;
import Panels.Board.Board;
import Panels.Board.Type;
import Utility.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Team {

    private final Side side;
    private final ArrayList<Figure> figures;
    private ArrayList<Spot> placeableTiles;

    private final int totalFigures = 6;
    private final int points = 0;

    public int getPoints() {
        return points;
    }

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
            if (Spot.areValuesEqual(f.getPlacement(),s)) return f;
        }
        return null;
    }

    public boolean hasFigureOnPos(Spot p) {
        for (Figure f : figures) {
            if (Spot.areValuesEqual(f.getPlacement(),p)) return true;
        }
        return false;
    }

    public boolean markTargets = false;
    private ArrayList<Spot> attackablePlaces = null;

    public ArrayList<Spot> getAttackablePlaces(){
        return attackablePlaces;
    }

    public void resetAttackablePlaces(){
        attackablePlaces = null;
    }

    public boolean attackFig(Figure f,Spot attackedSpot,Board board, Team enemyTeam) {
        if (!markTargets) {
            markPossibleTargets(f, board, enemyTeam);
            markTargets = true;
            return false;
        }

        if (attackablePlaces.stream().anyMatch(o -> o.hasEqualValues(attackedSpot))) {
            Figure attackedFig = enemyTeam.getFigureOnPos(attackedSpot);
            int diceTotals = Dice.throwDice(1,6) + Dice.throwDice(1,6) + Dice.throwDice(1,6);
            int damageDealt;
            if(diceTotals == attackedFig.getHealth()) damageDealt = 1110;
            else if(diceTotals == 3) damageDealt = f.getAttackDmg()/2;
            else damageDealt = f.getAttackDmg()- attackedFig.getArmor();

            markTargets = false;
            if(enemyTeam.getFigureOnPos(attackedSpot).attack(damageDealt))
            {
                board.getTile(attackedSpot).setPlacedFig(null);
                enemyTeam.figures.remove(attackedFig);
            }
            return true;
        }
        return false;
    }

    private void markPossibleTargets(Figure f,Board board, Team enemyTeam){
        //TODO Include elf attack
        attackablePlaces = new ArrayList<>();
        for(int i=1;i<= f.getAttackRange();i++)
        {
            Spot newSpot = new Spot(f.getPlacement().getHeight()+i,f.getPlacement().getWidth());
            if(!newSpot.isInBound(board) || board.getTile(newSpot).getType() == Type.Obstacle) break;
            if(enemyTeam.getFigures().stream().anyMatch( o -> Spot.areValuesEqual(o.getPlacement(),newSpot))){
                attackablePlaces.add(newSpot);
                break;
            }

        }for(int i=1;i<= f.getAttackRange();i++)
        {
            Spot newSpot = new Spot(f.getPlacement().getHeight(),f.getPlacement().getWidth()+i);
            if(!newSpot.isInBound(board) || board.getTile(newSpot).getType() == Type.Obstacle) break;
            if(enemyTeam.getFigures().stream().anyMatch( o -> Spot.areValuesEqual(o.getPlacement(),newSpot))){
                attackablePlaces.add(newSpot);
                break;
            }

        }for(int i=1;i<= f.getAttackRange();i++)
        {
            Spot newSpot = new Spot(f.getPlacement().getHeight()-i,f.getPlacement().getWidth());
            if(!newSpot.isInBound(board) || board.getTile(newSpot).getType() == Type.Obstacle) break;
            if(enemyTeam.getFigures().stream().anyMatch( o -> Spot.areValuesEqual(o.getPlacement(),newSpot))){
                attackablePlaces.add(newSpot);
                break;
            }

        }for(int i=1;i<= f.getAttackRange();i++)
        {
            Spot newSpot = new Spot(f.getPlacement().getHeight(),f.getPlacement().getWidth()-i);
            if(!newSpot.isInBound(board) || board.getTile(newSpot).getType() == Type.Obstacle) break;
            if(enemyTeam.getFigures().stream().anyMatch( o -> Spot.areValuesEqual(o.getPlacement(),newSpot))){
                attackablePlaces.add(newSpot);
                break;
            }

        }
    }

    boolean selectedDest = false;

    public boolean moveFig(Figure f, Spot moveToPos, Board board) {
        if (!selectedDest) {
            fillVisitedPlaces(f,board);
            selectedDest = true;
            return false;
        }
        if (visitedPlaces.stream().anyMatch(o -> o.getKey().hasEqualValues(moveToPos))) {
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
        //TODO 3 Include Elf movement
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

            for (Spot neighbor : neighbors) {
                Spot currentN;
                if ((currentN = currentSpot.getKey().addTo(neighbor)) != null && currentN.isInBound(board)) {
                    if (board.getTile(currentN.getHeight(),currentN.getWidth()).getPlacedFig() == null && currentSpot.getValue() <= f.getSpeed() && board.getTile(currentN.getHeight(),currentN.getWidth()).getType() != Type.Obstacle)
                        if (visitedPlaces.stream().noneMatch(a -> a.getKey().hasEqualValues(currentN))) {
                            toVisit.add(new Pair<>(currentN, currentSpot.getValue() + 1));
                        }
                }
            }
        }
    }

    public boolean healFig(Figure f, int healthRegen) {
        if(f.getMaxHealth() == f.getHealth()) return false;
        f.heal(healthRegen);
        if(f.getHealth()> f.getMaxHealth()) f.setHealth(f.getMaxHealth());
        return true;
    }
}
