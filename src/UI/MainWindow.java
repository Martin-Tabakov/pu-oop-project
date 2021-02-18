package UI;

import Board.*;
import Figure.Figure;
import Team.*;
import Utility.Pair;
import Utility.Place;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MainWindow extends JFrame implements MouseListener {

    Draw draw;
    Board board;
    Team north;
    Team south;
    Team currentPlacer;

    FigureHolder figureHolder;

    public MainWindow(int width, int height) {
        this.north = new Team(Side.North);
        this.north.setPlaceableTiles(width,height);
        this.south = new Team(Side.South);
        this.south.setPlaceableTiles(width,height);
        this.currentPlacer = this.north;

        this.board = new Board(width, height);

        int windowWidth = (width + 6) * Tile.width;
        int windowHeight = (height + 2) * Tile.height;

        this.draw = new Draw(new Place(Tile.height, Tile.width), new Place((width + 1) * Tile.width, 0));
        this.figureHolder = new FigureHolder(new Place(Tile.height, (width + 2) * Tile.width), north);
        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addMouseListener(this);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        draw.setGraphic(g);
        draw.drawBoard(board);
        draw.drawTeam(north);
        draw.drawTeam(south);
        draw.drawFigureHolder(board.getHeight(), board.getWidth(), figureHolder);
    }

    boolean isNorthPlacing = true;
    boolean bothPlayersPlaced = false;

    public Pair<Figure, Integer> getFigureToPlace(Place click) {
        boolean isNorthFull = north.getFigures().size() == north.getTotalFigures();
        boolean isSouthFull = south.getFigures().size() == south.getTotalFigures();
        if (isNorthFull && isSouthFull) bothPlayersPlaced = true;

        currentPlacer = (isNorthPlacing) ? north : south;
        figureHolder.setCurrentPlacer(currentPlacer);

        Pair<Figure, Integer> toReturn = null;

        ArrayList<Pair<Figure, Integer>> totals = figureHolder.getPlacedFigures();
        for (int i = 0; i < 3; i++) {
            if (totals.get(i).getKey().hasSamePos(click) && totals.get(i).getValue() < 2) {
                toReturn = totals.get(i);
                System.out.println("Clicked on valid option" + totals.get(i).getKey().getSymbols());
            }
        }
        return toReturn;
    }

    Place clickfh = null;
    Place clickb = null;
    Pair<Figure, Integer> figData= null;

    private void placeFigure(){
        for (Place p : currentPlacer.getPlaceableTiles()) {
            if(p.hasSamePos(clickb)) {
                currentPlacer.addFig(clickb,figData,p);
                isNorthPlacing = !isNorthPlacing;
                figData = null;
                return;
            }
        }
    }
    private void figurePlacing(MouseEvent e){

        Place fhNormalized = figureHolder.normalizePlace(new Place(e.getY(), e.getX()));
        Place gbNormalized = board.normalizePlace(new Place(e.getY(), e.getX()));

        System.out.println("fhNormalized = " + fhNormalized);
        System.out.println("gbNormalized = " + gbNormalized);

        clickfh = Tile.convertToPos(fhNormalized);
        clickb = Tile.convertToPos(gbNormalized);

        System.out.println("Clicked = " + clickfh);

        if(figData!=null) placeFigure();
        if(figData== null)figData= getFigureToPlace(clickfh);
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        if (!bothPlayersPlaced) figurePlacing(e);

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