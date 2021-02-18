package UI;

import Board.*;
import Figure.*;
import Team.*;
import Utility.Place;

import java.awt.Graphics;
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

    public MainWindow(int width,int height) {
        this.north = new Team(Side.North);
        this.south = new Team(Side.South);
        this.currentPlacer = this.north;

        this.board = new Board(width, height);

        int windowWidth = (width+6)*Tile.width;
        int windowHeight = (height+2)*Tile.height;

        this.draw = new Draw(new Place(Tile.height,Tile.width),new Place((width+1)*Tile.width,0));
        this.setSize(windowWidth,windowHeight);
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
        draw.drawFigureHolder(board.getHeight(),board.getWidth());
    }

    boolean isNorthPlacing = true;
    public void setTeams() {
            boolean isNorthFull = north.getFigures().size() == north.getTotalFigures();
            boolean isSouthFull = south.getFigures().size() == south.getTotalFigures();
            if (isNorthFull && isSouthFull) return;

            currentPlacer = (isNorthPlacing) ? north : south;

            ArrayList<Integer> totals= getPlacedFigures(currentPlacer);

            int placedDwarfs = totals.get(0);
            int placedKnights = totals.get(1);
            int placedElves = totals.get(2);

            System.out.println(placedDwarfs);
            System.out.println(placedKnights);
            System.out.println(placedElves);

            draw.setFigureHolderFigures(totals,currentPlacer);
            placeFigure();

            isNorthPlacing = !isNorthPlacing;
    }

    public ArrayList<Integer> getPlacedFigures(Team t){

        Dwarf d = new Dwarf(new Place(-1, -1));
        Knight k = new Knight(new Place(-1, -1));
        Elf e = new Elf(new Place(-1, -1));

        ArrayList<Integer> totals = new ArrayList<>();

        totals.add(getOccurrences(d,t.getFigures()));
        totals.add(getOccurrences(k,t.getFigures()));
        totals.add(getOccurrences(e,t.getFigures()));
        return totals;
    }

    private void placeFigure(){

    }

    private int getOccurrences(Figure f, ArrayList<Figure> figs) {
        int occ = 0;
        for (Figure fig : figs) {
            if (fig.getSymbols().equals(f.getSymbols())) occ++;
        }
        return occ;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Click");
        setTeams();
        repaint();
    }

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
}