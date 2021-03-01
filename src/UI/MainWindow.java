package UI;

import Panels.Board.*;
import Panels.Log.*;
import Figure.Figure;
import Panels.*;
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

    Spot clickfh = null;
    Spot clickb = null;
    Spot clickam = null;
    Pair<Figure, Integer> figData = null;

    boolean isNorthPlacing = true;
    boolean bothPlayersPlaced = false;

    public MainWindow(int height, int width) {
        this.north = new Team(Side.North);
        this.north.setPlaceableTiles(width, height);
        this.south = new Team(Side.South);
        this.south.setPlaceableTiles(width, height);
        this.currentPlayer = this.north;

        int windowWidth = (width + 8) * Tile.width;
        int windowHeight = (height + 2) * Tile.height;

        this.board = new Board(new Spot(1, 1), new Spot(height, width));
        this.figureHolder = new FigureHolder(new Spot(1, width + 2), new Spot(2, 3), north);
        this.actionMenu = new ActionMenu(new Spot(1, width + 2), new Spot(3, 2));
        this.log = new Log(new Spot(height - 2, width + 2), new Spot(3, 5));
        this.pointsDisplay = new PointsDisplay(new Spot(1, width + 5), new Spot(3, 2));

        this.draw = new Draw();
        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addMouseListener(this);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        draw.setGraphic(g);
        draw.drawBoard(board);
        draw.drawTeam(north, board.getPosition());
        draw.drawTeam(south, board.getPosition());
        draw.drawLogWindow(log);

        if (currentPlayer.getVisitedPlaces() != null)
            draw.drawMoveToPlaces(currentPlayer.getVisitedPlaces(), board.getPosition());
        if (currentPlayer.getAttackablePlaces() != null)
            draw.drawAttackablePlaces(currentPlayer.getAttackablePlaces(), board.getPosition());

        if (!bothPlayersPlaced) draw.drawFigureHolder(figureHolder, board.getPosition());
        else {
            draw.drawActionMenu(actionMenu, currentPlayer.getSide());
            draw.drawPointsDisplay(pointsDisplay, north, south);
        }
    }

    //region Figure placement phase
    public Pair<Figure, Integer> getFigureToPlace() {
        boolean isNorthFull = north.getFigures().size() == north.getTotalFigures();
        boolean isSouthFull = south.getFigures().size() == south.getTotalFigures();
        if (isNorthFull && isSouthFull) bothPlayersPlaced = true;

        currentPlayer = (isNorthPlacing) ? north : south;
        figureHolder.setCurrentPlacer(currentPlayer);

        Pair<Figure, Integer> toReturn = null;

        ArrayList<Pair<Figure, Integer>> totals = figureHolder.getPlacedFigures();
        for (int i = 0; i < 3; i++) {
            if (Spot.areValuesEqual(totals.get(i).getKey().getPlacement(), clickfh) && totals.get(i).getValue() < 2) {
                toReturn = totals.get(i);
                System.out.println("Clicked on valid option" + totals.get(i).getKey().getSymbols());
            }
        }
        return toReturn;
    }

    private void placeFigure() {
        for (Spot p : currentPlayer.getPlaceableTiles()) {
            if (p.hasEqualValues(clickb)) {
                currentPlayer.addFig(clickb, figData, p);
                board.getTile(clickb.getHeight(), clickb.getWidth()).setPlacedFig(figData.getKey());
                log.addEvent(new PlaceFig(log.getRounds(), currentPlayer.getSide(), figData.getKey()));
                log.countRound();
                isNorthPlacing = !isNorthPlacing;
                figData = null;
                return;
            }
        }
    }

    private void figurePlacing() {
        if (figData != null) placeFigure();
        if (figData == null) figData = getFigureToPlace();
    }

    //endregion
    String selectedAction = null;

    private void playGame() {

        if (selectedAction == null) {
            selectedAction = actionMenu.getAction(clickam);
            return;
        }
        doAction();
    }

    Figure doer = null;

    private void doAction() {
        if (doer == null) {
            doer = currentPlayer.getFigureOnPos(clickb);
        }
        if (doer != null) switch (selectedAction) {
            case "Attack": {
                if (currentPlayer.attackFig(doer, clickb, board, waitingPlayer)) {
                    currentPlayer.resetAttackablePlaces();
                    currentPlayer.markTargets = false;
                    prepareNextAction();
                    changeCurrentPlayer();
                }
                if(currentPlayer.markTargets && currentPlayer.getAttackablePlaces().size() == 0){
                    currentPlayer.resetAttackablePlaces();
                    currentPlayer.markTargets = false;
                    System.out.println("No possible targets!");
                    prepareNextAction();
                }
                break;
            }
            case "Move": {
                if (currentPlayer.moveFig(doer, clickb, board)) {
                    Spot initialSpot = currentPlayer.getVisitedPlaces().getFirst().getKey();
                    board.getTile(initialSpot.getHeight(), initialSpot.getWidth()).setPlacedFig(null);
                    board.getTile(clickb.getHeight(), clickb.getWidth()).setPlacedFig(doer);
                    log.addEvent(new Move(log.getRounds(), currentPlayer.getSide(), doer, clickb));
                    log.countRound();
                    currentPlayer.resetVisited();
                    prepareNextAction();
                    changeCurrentPlayer();
                }
                break;
            }
            case "Heal": {
                int healthRegen = Dice.throwDice(1, 6);
                if (currentPlayer.healFig(doer, healthRegen)) {
                    System.out.println("Healing regen is :" + healthRegen);
                    log.addEvent(new Heal(log.getRounds(), currentPlayer.getSide(), doer, doer.getHealth()));

                    if (healthRegen % 2 == 0) changeCurrentPlayer();
                }
                prepareNextAction();
                break;
            }
        }
    }

    private void prepareNextAction() {
        selectedAction = null;
        doer = null;
    }

    private void changeCurrentPlayer() {
        isNorthPlacing = !isNorthPlacing;
        currentPlayer = (isNorthPlacing) ? north : south;
        waitingPlayer = (!isNorthPlacing) ? north : south;
    }

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

        System.out.printf("Click x: %d y: %d\n", e.getX(), e.getY());

        System.out.println("fhNormalized = " + fhNormalized);
        System.out.println("gbNormalized = " + gbNormalized);
        System.out.println("amNormalized = " + amNormalized);

        clickfh = Spot.convertToPos(fhNormalized);
        clickb = Spot.convertToPos(gbNormalized);
        clickam = Spot.convertToPos(amNormalized);

        System.out.println("Clicked fh= " + clickfh);
        System.out.println("Clicked gb= " + clickb);
        System.out.println("Clicked am= " + clickam);

        if (!bothPlayersPlaced) figurePlacing();
        if (bothPlayersPlaced) playGame();

        repaint();
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