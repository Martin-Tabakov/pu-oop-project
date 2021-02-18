package UI;

import Figure.*;
import Team.*;
import Utility.*;

import java.util.ArrayList;

public class FigureHolder {

    private final Place place;
    private Team currentPlacer;

    public FigureHolder(Place place,Team currentPlacer){
        this.place = place;
        this.currentPlacer = currentPlacer;
    }

    public Team getCurrentPlacer() {
        return currentPlacer;
    }

    public void setCurrentPlacer(Team t) {
        currentPlacer = t;
    }
    public ArrayList<Pair<Figure,Integer>> getPlacedFigures(){

        Dwarf d = new Dwarf(new Place(1, 0));
        Knight k = new Knight(new Place(1, 1));
        Elf e = new Elf(new Place(1, 2));

        ArrayList<Pair<Figure,Integer>> totals = new ArrayList<>();

        totals.add(new Pair<>(d,getOccurrences(d)));
        totals.add(new Pair<>(k,getOccurrences(k)));
        totals.add(new Pair<>(e,getOccurrences(e)));

        return totals;
    }

    private int getOccurrences(Figure f) {
        int occ = 0;
        for (Figure fig : currentPlacer.getFigures()) {
            if (fig.getSymbols().equals(f.getSymbols())) occ++;
        }
        return occ;
    }

    public Place getPlace() {
        return place;
    }

    public Place normalizePlace(Place p){
        return new Place(p.getRow()-place.getRow(),p.getColumn()-place.getColumn());
    }
}
