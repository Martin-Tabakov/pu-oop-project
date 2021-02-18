package Team;

import Figure.*;
import Utility.Place;

import java.util.ArrayList;

public class Team {

    private final Side side;
    private ArrayList<Figure> figures;

    private int totalFigures = 6;

    public int getTotalFigures() {
        return totalFigures;
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public Side getSide() {
        return this.side;
    }

    public Team(Side side) {
        this.side = side;
        this.figures = new ArrayList<>(totalFigures);
        setFigures();
    }

    public void setFigures(){
        int row= 5;
        if(side == Side.North) row =1;
        //this.figures.add(new Dwarf(new Place(row,0)));
        this.figures.add(new Dwarf(new Place(row,1)));
        this.figures.add(new Knight(new Place(row,2)));
        //this.figures.add(new Knight(new Place(row,3)));
        this.figures.add(new Elf(new Place(row,4)));
        this.figures.add(new Elf(new Place(row,5)));
    }
    public boolean hasFigureOnPos(Place p){
        for (Figure f: figures) {
            if(f.hasSamePos(p)) return true;
        }
        return false;
    }
}
