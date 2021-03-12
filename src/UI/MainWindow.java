package UI;

import Panels.ActionMenu.ActionMenu;
import Panels.Board.*;
import Panels.EndGameDisplay;
import Panels.FigureHolder.FigureHolder;
import Figure.Figure;
import Panels.Log.Log;
import Panels.PointsDisplay.PointsDisplay;
import Team.*;
import Utility.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MainWindow extends JFrame implements MouseListener {

    Draw draw;
    Team north;
    Team south;
    Team currentPlayer;
    Team waitingPlayer;

    Board board;
    FigureHolder figureHolder;
    ActionMenu actionMenu;
    Log log;
    PointsDisplay pointsDisplay;
    EndGameDisplay endGameDisplay;

    Spot clickfh = null;
    Spot clickb = null;
    Spot clickam = null;
    Spot clickegp = null;
    Pair<Figure, Integer> figData = null;

    boolean isNorthPlacing = true;
    boolean bothPlayersPlaced = false;
    int height;
    int width;

    GameState gameState = GameState.Preparation;

    /**
     * Constructor for the frame, where the game will be played
     *
     * @param height height of the game board measured in tiles
     * @param width  width of the game board measured in tiles
     */
    public MainWindow(int height, int width) {
        this.height = height;
        this.width = width;
        this.north = new Team(Side.North);
        this.north.setPlaceableTiles(width, height);
        this.south = new Team(Side.South);
        this.south.setPlaceableTiles(width, height);
        this.currentPlayer = this.north;
        this.waitingPlayer = this.south;

        this.board = new Board(new Spot(1, 1), new Spot(height, width));
        this.figureHolder = new FigureHolder(new Spot(1, width + 2), new Spot(2, 3), north);
        this.actionMenu = new ActionMenu(new Spot(1, width + 2), new Spot(3, 2));
        this.log = new Log(new Spot(height - 2, width + 2), new Spot(3, 5));
        this.pointsDisplay = new PointsDisplay(new Spot(1, width + 5), new Spot(3, 2));
        this.endGameDisplay = new EndGameDisplay(new Spot(1, 1), new Spot(height - 1, width));

        Log.reset();
        this.draw = new Draw();
        this.setSize((width + 8) * Tile.width, (height + 2) * Tile.height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addMouseListener(this);
        this.setVisible(true);
    }

    /**
     * Used for visualization of all graphic elements
     *
     * @param g Graphic component g
     */
    public void paint(Graphics g) {
        super.paint(g);
        draw.setGraphic(g);

        switch (gameState) {
            case Preparation -> {
                draw.drawBoard(board);
                draw.drawTeam(north, board.getPosition());
                draw.drawTeam(south, board.getPosition());
                draw.drawLogWindow(log);
                draw.drawFigureHolder(figureHolder, board.getPosition());
            }
            case Playing -> {
                draw.drawBoard(board);
                draw.drawTeam(north, board.getPosition());
                draw.drawTeam(south, board.getPosition());
                draw.drawLogWindow(log);
                if (currentPlayer.getVisitedPlaces() != null)
                    draw.drawMoveToPlaces(currentPlayer.getVisitedPlaces(), board.getPosition());
                if (currentPlayer.getAttackablePlaces() != null)
                    draw.drawAttackablePlaces(currentPlayer.getAttackablePlaces(), board.getPosition());

                draw.drawActionMenu(actionMenu, currentPlayer.getSide());
                draw.drawPointsDisplay(pointsDisplay, north, south);
            }
            case End -> {
                this.setSize((width+2)*Tile.width,(height+1)*Tile.height);
                draw.drawEndGameDisplay(endGameDisplay);
            }

        }
    }

    //region Figure placement phase

    /**
     * Method that executes the stage of the game where the players place their figures on the board.
     * Switches between a player selecting a figure and placing it on the board.
     * Afterwards the other player does the same until all figures have been placed on the board
     */
    private void figurePlacing() {
        if (figData != null) placeFigure();
        if (figData == null) figData = getFigureToPlace();
    }

    /**
     * Executes when a player has to select a figure from the figure holder panel so it can be placed on the game board
     *
     * @return The player selected figure, and the times its type has been placed on the game board
     */
    public Pair<Figure, Integer> getFigureToPlace() {
        boolean isNorthFull = north.getFigures().size() == north.getTotalFigures();
        boolean isSouthFull = south.getFigures().size() == south.getTotalFigures();
        if (isNorthFull && isSouthFull) bothPlayersPlaced = true;

        currentPlayer = (isNorthPlacing) ? north : south;
        figureHolder.setCurrentPlacer(currentPlayer);

        Pair<Figure, Integer> toReturn = null;

        ArrayList<Pair<Figure, Integer>> totals = figureHolder.getPlacedFigures();
        for (int i = 0; i < 3; i++) {
            if (Spot.areValuesEqual(totals.get(i).getKey().getPlace(), clickfh) && totals.get(i).getValue() < 2) {
                toReturn = totals.get(i);
                System.out.println("Clicked on valid option " + totals.get(i).getKey().getSymbols());
            }
        }
        return toReturn;
    }

    /**
     * Method that is called after a figure has been selected to to be placed.
     * Places the figure if the player has selected a valid tile for the figure
     */
    private void placeFigure() {
        for (Spot p : currentPlayer.getPlaceableTiles()) {
            if (p.hasEqualValues(clickb)) {
                currentPlayer.addFig(clickb, figData, p);
                board.getTile(clickb.getHeight(), clickb.getWidth()).setPlacedFig(figData.getKey());
                isNorthPlacing = !isNorthPlacing;
                figData = null;
                return;
            }
        }
    }

    //endregion

    //region Game in progress
    String selectedAction = null;

    /**
     * Executed at the stage of the game where the two players are fighting against each other.
     * Switches between a player selecting an action and executing that same action.
     * Afterwards the other player does the same until one of the players is left without figures
     */
    private void playGame() {
        if (selectedAction == null) {
            selectedAction = actionMenu.getAction(clickam);
            return;
        }
        doAction();
    }

    Figure doer = null;

    /**
     * Executes an action that the player has selected from the action menu
     */
    private void doAction() {
        if (doer == null) {
            doer = currentPlayer.getFigureOnPos(clickb);
        }
        if (doer != null) switch (selectedAction) {
            case "Attack" -> executeAttack();
            case "Move" -> executeMove();
            case "Heal" -> executeHeal();
        }
    }

    /**
     * Executes an attack action, where one figure attacks another enemy figure or an obstacle tile
     */
    private void executeAttack() {
        if (currentPlayer.attackFig(doer, clickb, board, waitingPlayer)) {
            currentPlayer.resetAttackablePlaces();
            currentPlayer.markTargets = false;
            prepareNextAction();
            changeCurrentPlayer();
        }
        if (currentPlayer.markTargets && currentPlayer.getAttackablePlaces().size() == 0) {
            currentPlayer.resetAttackablePlaces();
            currentPlayer.markTargets = false;
            System.out.println("No possible targets!");
            prepareNextAction();
        }
    }

    /**
     * Executes a movement for a figure to another tile if the tile is valid
     */
    private void executeMove() {
        if (currentPlayer.moveFig(doer, clickb, board)) {
            Spot initialSpot = currentPlayer.figureStandingPlace.getKey();
            board.getTile(initialSpot.getHeight(), initialSpot.getWidth()).setPlacedFig(null);
            board.getTile(clickb.getHeight(), clickb.getWidth()).setPlacedFig(doer);
            currentPlayer.resetVisited();
            prepareNextAction();
            changeCurrentPlayer();
        }
    }

    /**
     * Executes a heal for a figure if its health is below its maximum health
     */
    private void executeHeal() {
        int healthRegen = Dice.throwDice(1, 6);
        if (currentPlayer.healFig(doer, healthRegen))
            if (Dice.throwDice(2, 4) % 2 == 0) changeCurrentPlayer();
        prepareNextAction();
    }

    /**
     * Clears the selected action so another action can be chosen
     */
    private void prepareNextAction() {
        selectedAction = null;
        doer = null;
    }

    /**
     * Switches the current and waiting player and counts a round
     */
    private void changeCurrentPlayer() {
        isNorthPlacing = !isNorthPlacing;
        currentPlayer = (isNorthPlacing) ? north : south;
        waitingPlayer = (!isNorthPlacing) ? north : south;
        Log.countRound();
    }
    //endregion

    //region Game ended

    private void completeGame() {
        //Check if the player clicks on the button, starting a new game
        //endGameDisplay.setData(north.getPoints(),south.getPoints());
        if (clickegp.getHeight() == 5 && clickegp.getWidth() >= 3 && clickegp.getWidth() <= 5) {
            this.dispose();
            new MainWindow(height, width);
        }
    }

    //endregion

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        Spot fhNormalized = figureHolder.normalizePlace(new Spot(e.getY(), e.getX()));
        Spot gbNormalized = board.normalizePlace(new Spot(e.getY(), e.getX()));
        Spot amNormalized = actionMenu.normalizePlace(new Spot(e.getY(), e.getX()));
        Spot egpNormalized = endGameDisplay.normalizePlace(new Spot(e.getY(), e.getX()));

        clickfh = Spot.convertToPos(fhNormalized);
        clickb = Spot.convertToPos(gbNormalized);
        clickam = Spot.convertToPos(amNormalized);
        clickegp = Spot.convertToPos(egpNormalized);

        switch (gameState) {
            case Preparation -> figurePlacing();
            case Playing -> playGame();
            case End -> completeGame();
        }
        gameState = setState();

        endGameDisplay.setData(north.getPoints(), south.getPoints());
        repaint();
    }

    private GameState setState() {
        if (!bothPlayersPlaced) return GameState.Preparation;
        if (currentPlayer.getFigures().size() == 0 || waitingPlayer.getFigures().size() == 0) return GameState.End;
        if (bothPlayersPlaced) return GameState.Playing;
        return null;
    }

    enum GameState {
        Preparation,
        Playing,
        End
    }
    //region Unused mouse events

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
    //endregion
}