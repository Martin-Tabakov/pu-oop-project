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

    /**
     * Returns the points for this team
     *
     * @return int points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Returns the figure quantity of a team
     *
     * @return int quantity
     */
    public int getTotalFigures() {
        return totalFigures;
    }

    /**
     * returns the figures of the team
     *
     * @return ArrayList<Figure> figures
     */
    public ArrayList<Figure> getFigures() {
        return figures;
    }

    /**
     * returns the positions of tiles where a figure can be placed
     *
     * @return ArrayList<Spot> placeableTiles
     */
    public ArrayList<Spot> getPlaceableTiles() {
        return placeableTiles;
    }

    /**
     * Returns the side of the team
     *
     * @return Side
     */
    public Side getSide() {
        return this.side;
    }

    /**
     * Constructor for the team
     *
     * @param side The teams side
     */
    public Team(Side side) {
        this.side = side;
        this.figures = new ArrayList<>(totalFigures);
    }

    /**
     * Sets the tiles where a figure can be placed
     *
     * @param boardWidth  game board width in tiles
     * @param boardHeight game board height in tiles
     */
    public void setPlaceableTiles(int boardWidth, int boardHeight) {
        placeableTiles = new ArrayList<>();
        int row = (side == Side.North) ? 0 : boardHeight - 2;
        for (int i = row; i < row + 2; i++) {
            for (int j = 0; j < boardWidth; j++) {
                placeableTiles.add(new Spot(i, j));
            }
        }
    }

    /**
     * Adds a figure to the list of placed figures and removes the tile from placeable tiles
     *
     * @param click    Position on the game board, where a tile is to be placed
     * @param figData  Data containing the figure to be placed
     * @param toRemove The Spot that has to be removed from the placeable tiles positions
     */
    public void addFig(Spot click, Pair<Figure, Integer> figData, Spot toRemove) {
        Log.addEntry(new PlaceFig(Log.getRounds(), getSide(), figData.getKey()));
        placeableTiles.remove(toRemove);
        Figure f = figData.getKey();
        f.setPlacement(click);
        figures.add(f);
    }

    /**
     * Returns a figure that occupies a certain spot
     *
     * @param s The spot, where the figure is standing
     * @return The figure if a figure occupies that spot, if no figure is present at that spot - null
     */
    public Figure getFigureOnPos(Spot s) {

        for (Figure f : figures) {
            if (Spot.areValuesEqual(f.getPlace(), s)) return f;
        }
        return null;
    }

    //region attack logic
    public boolean markTargets = false;
    private ArrayList<Spot> attackablePlaces = null;

    /**
     * Returns the places where a figure can execute an attack
     *
     * @return ArrayList<Spot> attackablePlaces
     */
    public ArrayList<Spot> getAttackablePlaces() {
        return attackablePlaces;
    }

    /**
     * Sets the attackablePlaces to null
     */
    public void resetAttackablePlaces() {
        attackablePlaces = null;
    }

    private Figure attackedFig;

    /**
     * Executes an attack.
     *
     * @param f            The attacking figure
     * @param attackedSpot The place that it is called to attack on
     * @param board        The game board
     * @param enemyTeam    Enemy Team
     * @return True if an attack is made successfully
     */
    public boolean attackFig(Figure f, Spot attackedSpot, Board board, Team enemyTeam) {
        if (!markTargets) {
            attackedFig = null;
            markPossibleTargets(f, board, enemyTeam);
            markTargets = true;
            return false;
        }
        if (attackObstacle(board, attackedSpot, f)) return true;

        return attackFigure(enemyTeam, attackedSpot, board, f);
    }

    /**
     * Finds a target on a destined spot and attacks the figure on it
     *
     * @param enemyTeam    Enemy Team
     * @param attackedSpot The Spot where the attack will occur
     * @param board        The game board
     * @param f            The figure executing an attack
     * @return True if an attack is successful
     */
    private boolean attackFigure(Team enemyTeam, Spot attackedSpot, Board board, Figure f) {
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

    /**
     * Executes an attack on an obstacle
     *
     * @param board        The game board
     * @param attackedSpot The Spot where the attack will occur
     * @param f            The figure executing an attack
     * @return True if an attack is successful
     */
    private boolean attackObstacle(Board board, Spot attackedSpot, Figure f) {
        if (board.getTile(attackedSpot).getType() == Type.Obstacle) {
            board.setTile(attackedSpot, new Tile(attackedSpot, Type.Field));
            Log.addEntry(new RemoveObstacle(Log.getRounds(), getSide(), f));
            markTargets = false;
            return true;
        }
        return false;
    }

    /**
     * Fills the attackablePlaces arrayList with locations of possible targets positions
     *
     * @param f         The figure executing an attack
     * @param board     The game board
     * @param enemyTeam Enemy team
     */
    private void markPossibleTargets(Figure f, Board board, Team enemyTeam) {
        attackablePlaces = new ArrayList<>();
        for (int i = 1; i <= f.getAttackRange(); i++) {
            Spot newSpot = new Spot(f.getPlace().getHeight(), f.getPlace().getWidth());
            addAttackableSpots(board, newSpot.addTo(new Spot(i, 0)), enemyTeam, f);
            addAttackableSpots(board, newSpot.addTo(new Spot(-i, 0)), enemyTeam, f);
            addAttackableSpots(board, newSpot.addTo(new Spot(0, i)), enemyTeam, f);
            addAttackableSpots(board, newSpot.addTo(new Spot(0, -i)), enemyTeam, f);
        }
    }

    /**
     * Checks whether a spot is a viable option to execute an attack on
     *
     * @param board     Game board
     * @param newSpot   The spot that will be checked
     * @param enemyTeam Enemy Team
     * @param f         Figure executing an attack
     */
    private void addAttackableSpots(Board board, Spot newSpot, Team enemyTeam, Figure f) {
        if (!newSpot.isInBound(board)) return;
        if (getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlace(), newSpot))) return;

        if (f.getSymbols().equals("E")) {
            if (board.getTile(newSpot).getType() == Type.Obstacle) {
                attackablePlaces.add(newSpot);
            }
            if (enemyTeam.getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlace(), newSpot))) {
                attackablePlaces.add(newSpot);
            }
        } else if (enemyTeam.getFigures().stream().anyMatch(o -> Spot.areValuesEqual(o.getPlace(), newSpot)) || board.getTile(newSpot).getType() == Type.Obstacle) {
            attackablePlaces.add(newSpot);
        }
    }
    //endregion

    //region movement logic
    boolean selectedDest = false;

    /**
     * Executes a move action an a figure
     *
     * @param f         The figure changing its position
     * @param moveToPos The new position where the figure is to move
     * @param board     The game board
     * @return True if a move is made, otherwise false
     */
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

    /**
     * Gets the spots with the available places for a figure to move on to
     *
     * @return The spots with the available places for a figure to move on to
     */
    public LinkedList<Pair<Spot, Integer>> getVisitedPlaces() {
        return visitedPlaces;
    }

    /**
     * Resets the List to null
     */
    public void resetVisited() {
        visitedPlaces = null;
    }

    LinkedList<Pair<Spot, Integer>> visitedPlaces = null;
    public Pair<Spot, Integer> figureStandingPlace = null;

    /**
     * Fills the Linked list with the spots where the figure can move to with its distance from the figure
     *
     * @param f     Figure
     * @param board Game board
     */
    public void fillVisitedPlaces(Figure f, Board board) {
        Queue<Pair<Spot, Integer>> toVisit = new LinkedList<>();
        visitedPlaces = new LinkedList<>();
        toVisit.add(new Pair<>(f.getPlace(), 1));

        ArrayList<Spot> neighbors = new ArrayList<>();
        neighbors.add(new Spot(-1, 0));
        neighbors.add(new Spot(1, 0));
        neighbors.add(new Spot(0, 1));
        neighbors.add(new Spot(0, -1));

        traverseNeighbours(toVisit, neighbors, board, f);
        figureStandingPlace = visitedPlaces.getFirst();
        visitedPlaces.removeFirst();
    }

    /**
     * Goes trough all tiles within reach of the figure and fills the visitedPlaces with viable move to spots
     *
     * @param toVisit   Queue of Spots to visit
     * @param neighbors The neighbouring tiles of the current one when their coordinates are added to each other
     * @param board     Game board
     * @param f         Figure
     */
    private void traverseNeighbours(Queue<Pair<Spot, Integer>> toVisit, ArrayList<Spot> neighbors, Board board, Figure f) {
        while (toVisit.peek() != null) {

            Pair<Spot, Integer> currentSpot = toVisit.peek();
            toVisit.remove();
            visitedPlaces.add(new Pair<>(currentSpot.getKey(), currentSpot.getValue()));

            for (Spot neighbor : neighbors) {
                Spot currentN;
                if ((currentN = currentSpot.getKey().addTo(neighbor)) != null && currentN.isInBound(board)) {
                    if (board.getTile(currentN).getPlacedFig() == null && currentSpot.getValue() <= f.getSpeed() && board.getTile(currentN).getType() != Type.Obstacle)
                        if (visitedPlaces.stream().noneMatch(a -> a.getKey().hasEqualValues(currentN))) {
                            toVisit.add(new Pair<>(currentN, currentSpot.getValue() + 1));
                        }
                }
            }
        }
    }

    //endregion

    /**
     * Executes a healing action on a figure
     *
     * @param f           Figure
     * @param healthRegen Amount of hp to regenerate
     * @return true if health points are restored to the figure
     */
    public boolean healFig(Figure f, int healthRegen) {
        if (f.getMaxHealth() == f.getHealth()) return false;

        f.heal(Math.min(f.getMaxHealth() - f.getHealth(), healthRegen));

        Log.addEntry(new Heal(Log.getRounds(), getSide(), f, f.getHealth()));
        return true;
    }
}
