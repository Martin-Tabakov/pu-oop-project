package Team;

import Figure.*;
import Panels.Board.Board;
import Panels.Board.Tile;
import Panels.Board.Type;
import Panels.Log.Entries.*;
import Panels.Log.Log;
import Utility.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Team {

    private final Side side;
    private final ArrayList<Figure> figures;
    private ArrayList<Spot> placeableTiles;

    private final int totalFigures = 6;
    private int points = 0;

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
            if (Spot.areValuesEqual(f.getPlacement(), s)) return f;
        }
        return null;
    }

    public boolean hasFigureOnPos(Spot p) {
        for (Figure f : figures) {
            if (Spot.areValuesEqual(f.getPlacement(), p)) return true;
        }
        return false;
    }

    //region attack logic
    public boolean markTargets = false;
    private ArrayList<Spot> attackablePlaces = null;

    public ArrayList<Spot> getAttackablePlaces() {
        return attackablePlaces;
    }

    public void resetAttackablePlaces() {
        attackablePlaces = null;
    }

    private Figure attackedFig;

    public boolean attackFig(Figure f, Spot attackedSpot, Board board, Team enemyTeam) {
        if (!markTargets) {
            attackedFig = null;
            markPossibleTargets(f, board, enemyTeam);
            markTargets = true;
            return false;
        }
        if (board.getTile(attackedSpot).getType() == Type.Obstacle) {
            board.setTile(attackedSpot, new Tile(attackedSpot, Type.Field));
            Log.addEntry(new RemoveObstacle(Log.getRounds(), getSide(), f));
            markTargets = false;
            return true;
        }

        if (attackablePlaces.stream().anyMatch(o -> o.hasEqualValues(attackedSpot))) {
            Figure attackedFig = enemyTeam.getFigureOnPos(attackedSpot);
            int diceTotals = Dice.throwDice(1, 6) + Dice.throwDice(1, 6) + Dice.throwDice(1, 6);
            int damageDealt;
            if (diceTotals == attackedFig.getHealth()) damageDealt = 0;
            else if (diceTotals == 3) damageDealt = f.getAttackDmg() / 2;
            else damageDealt = f.getAttackDmg() - attackedFig.getArmor();

            markTargets = false;
            attackedFig = enemyTeam.getFigureOnPos(attackedSpot);
            int actualDamage = Math.min(damageDealt, attackedFig.getHealth());
            points += actualDamage;
            Log.addEntry(new Attack(Log.getRounds(), getSide(), f, attackedFig));

            if (attackedFig.attack(actualDamage)) {
                Log.addEntry(new Death(Log.getRounds(), enemyTeam.getSide(), attackedFig));
                board.getTile(attackedSpot).setPlacedFig(null);
                enemyTeam.figures.remove(attackedFig);
            }
            return true;
        }
        return false;
    }

    private void markPossibleTargets(Figure f, Board board, Team enemyTeam) {
        attackablePlaces = new ArrayList<>();
        for (int i = 1; i <= f.getAttackRange(); i++) {
            Spot newSpot = new Spot(f.getPlacement().getHeight() + i, f.getPlacement().getWidth());
            if (!newSpot.isInBound(board)) break;
            if(getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlacement(), newSpot))) break;

            if (f.getSymbols().equals("E")) {
                if (board.getTile(newSpot).getType() == Type.Obstacle) {
                    attackablePlaces.add(newSpot);
                }
                if (enemyTeam.getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlacement(), newSpot))) {
                    attackablePlaces.add(newSpot);
                    break;
                }
            }
            else if (enemyTeam.getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlacement(), newSpot)) || board.getTile(newSpot).getType() == Type.Obstacle) {
                attackablePlaces.add(newSpot);
                break;
            }

        }
        for (int i = 1; i <= f.getAttackRange(); i++) {
            Spot newSpot = new Spot(f.getPlacement().getHeight(), f.getPlacement().getWidth() + i);
            if (!newSpot.isInBound(board)) break;
            if(getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlacement(), newSpot))) break;

            if (f.getSymbols().equals("E")) {
                if (board.getTile(newSpot).getType() == Type.Obstacle) {
                    attackablePlaces.add(newSpot);
                }
                if (enemyTeam.getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlacement(), newSpot))) {
                    attackablePlaces.add(newSpot);
                    break;
                }
            }
            else if (enemyTeam.getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlacement(), newSpot)) || board.getTile(newSpot).getType() == Type.Obstacle) {
                attackablePlaces.add(newSpot);
                break;
            }

        }
        for (int i = 1; i <= f.getAttackRange(); i++) {
            Spot newSpot = new Spot(f.getPlacement().getHeight() - i, f.getPlacement().getWidth());
            if (!newSpot.isInBound(board)) break;
            if(getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlacement(), newSpot))) break;

            if (f.getSymbols().equals("E")) {
                if (board.getTile(newSpot).getType() == Type.Obstacle) {
                    attackablePlaces.add(newSpot);
                }
                if (enemyTeam.getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlacement(), newSpot))) {
                    attackablePlaces.add(newSpot);
                    break;
                }
            }
            else if (enemyTeam.getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlacement(), newSpot)) || board.getTile(newSpot).getType() == Type.Obstacle) {
                attackablePlaces.add(newSpot);
                break;
            }

        }
        for (int i = 1; i <= f.getAttackRange(); i++) {
            Spot newSpot = new Spot(f.getPlacement().getHeight(), f.getPlacement().getWidth() - i);
            if (!newSpot.isInBound(board)) break;
            if(getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlacement(), newSpot))) break;

            if (f.getSymbols().equals("E")) {
                if (board.getTile(newSpot).getType() == Type.Obstacle) {
                    attackablePlaces.add(newSpot);
                }
                if (enemyTeam.getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlacement(), newSpot))) {
                    attackablePlaces.add(newSpot);
                    break;
                }
            }
            else if (enemyTeam.getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlacement(), newSpot)) || board.getTile(newSpot).getType() == Type.Obstacle) {
                attackablePlaces.add(newSpot);
                break;
            }

        }
    }
    //endregion

    //region movement logic
    boolean selectedDest = false;

    public boolean moveFig(Figure f, Spot moveToPos, Board board) {
        if (!selectedDest) {
            fillVisitedPlaces(f, board);
            selectedDest = true;
            return false;
        }
        if (visitedPlaces.stream().anyMatch(o -> o.getKey().hasEqualValues(moveToPos))) {
            selectedDest = false;
            Log.addEntry(new Move(Log.getRounds(), getSide(), f, moveToPos));
            return f.move(moveToPos);
        }
        return false;
    }

    public LinkedList<Pair<Spot, Integer>> getVisitedPlaces() {
        return visitedPlaces;
    }

    public void resetVisited() {
        visitedPlaces = null;
    }

    LinkedList<Pair<Spot, Integer>> visitedPlaces = null;

    public void fillVisitedPlaces(Figure f, Board board) {
        //TODO 4 Exclude the tile where the figure is standing as a valid tile to move to
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
                    if (board.getTile(currentN.getHeight(), currentN.getWidth()).getPlacedFig() == null && currentSpot.getValue() <= f.getSpeed() && board.getTile(currentN.getHeight(), currentN.getWidth()).getType() != Type.Obstacle)
                        if (visitedPlaces.stream().noneMatch(a -> a.getKey().hasEqualValues(currentN))) {
                            toVisit.add(new Pair<>(currentN, currentSpot.getValue() + 1));
                        }
                }
            }
        }
    }

    //endregion
    public boolean healFig(Figure f, int healthRegen) {
        if (f.getMaxHealth() == f.getHealth()) return false;

        f.heal(Math.min(f.getMaxHealth() - f.getHealth(), healthRegen));

        Log.addEntry(new Heal(Log.getRounds(), getSide(), f, f.getHealth()));
        return true;
    }
}
