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
    Team currentPlacer;

    Board board;
    FigureHolder figureHolder;
    Log log;

    Spot clickfh = null;
    Spot clickb = null;
    Pair<Figure, Integer> figData= null;

    boolean isNorthPlacing = true;
    boolean bothPlayersPlaced = false;

    public MainWindow( int height,int width) {
        this.north = new Team(Side.North);
        this.north.setPlaceableTiles(width,height);
        this.south = new Team(Side.South);
        this.south.setPlaceableTiles(width,height);
        this.currentPlacer = this.north;

        int windowWidth = (width + 8) * Tile.width;
        int windowHeight = (height + 2) * Tile.height;

        this.board = new Board(new Spot(1,1),new Spot(height,width));
        this.figureHolder = new FigureHolder(new Spot(1,width+2),new Spot(2,3), north);
        this.log = new Log(new Spot(height-3,width+2), new Spot(3,5));

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
        draw.drawTeam(north,board.getPosition());
        draw.drawTeam(south,board.getPosition());
        draw.drawFigureHolder(figureHolder,board.getPosition());
        draw.drawLogWindow(log);
    }

    public Pair<Figure, Integer> getFigureToPlace(Spot click) {
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

    private void placeFigure(){
        for (Spot p : currentPlacer.getPlaceableTiles()) {
            if(p.hasEqualValues(clickb)) {
                currentPlacer.addFig(clickb,figData,p);
                log.addEvent(new PlaceFig(log.getRounds() ,currentPlacer.getSide(),figData.getKey()));
                log.countRound();
                isNorthPlacing = !isNorthPlacing;
                figData = null;
                return;
            }
        }
    }
    private void figurePlacing(MouseEvent e){

        Spot fhNormalized = figureHolder.normalizePlace(new Spot(e.getY(), e.getX()));
        Spot gbNormalized = board.normalizePlace(new Spot(e.getY(), e.getX()));

        System.out.printf("Click x: %d y: %d\n",e.getX(),e.getY());

        System.out.println("fhNormalized = " + fhNormalized);
        System.out.println("gbNormalized = " + gbNormalized);

        clickfh = Tile.convertToPos(fhNormalized);
        clickb = Tile.convertToPos(gbNormalized);

        System.out.println("Clicked fh= " + clickfh);
        System.out.println("Clicked gb= " + clickb);

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