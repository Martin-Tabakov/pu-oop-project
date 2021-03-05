package Panels.FigureHolder;

import Figure.*;
import Panels.Panel;
import Team.*;
import Utility.*;

import java.util.ArrayList;

public class FigureHolder extends Panel {

    private Team currentPlacer;

    public FigureHolder(Spot position,Spot size, Team currentPlacer){
        super(position,size);
        this.currentPlacer = currentPlacer;
    }

    /**
     * Returns the current team that is placing figures
     * @return Team
     */
    public Team getCurrentPlacer() {
        return currentPlacer;
    }

    /**
     * Sets the current team that  is placing figures
     * @param t Team
     */
    public void setCurrentPlacer(Team t) {
        currentPlacer = t;
    }

    /**
     * Returns the placed figures with the amount of each type placed
     * @return ArrayList<Pair<Figure,Integer>> totals
     */
    public ArrayList<Pair<Figure,Integer>> getPlacedFigures(){

        Dwarf d = new Dwarf(new Spot(1, 0));
        Knight k = new Knight(new Spot(1, 1));
        Elf e = new Elf(new Spot(1, 2));

        ArrayList<Pair<Figure,Integer>> totals = new ArrayList<>();

        totals.add(new Pair<>(d,getOccurrences(d)));
        totals.add(new Pair<>(k,getOccurrences(k)));
        totals.add(new Pair<>(e,getOccurrences(e)));

        return totals;
    }

    /**
     * Counts the amount of placed figures of a specified figure type
     * @param f Figure
     * @return int - total amount of placed figures
     */
    private int getOccurrences(Figure f) {
        int occ = 0;
        for (Figure fig : currentPlacer.getFigures()) {
            if (fig.getSymbols().equals(f.getSymbols())) occ++;
        }
        return occ;
    }
}
