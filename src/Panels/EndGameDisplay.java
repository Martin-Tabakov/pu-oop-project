package Panels;

import Figure.Figure;
import Panels.Log.Entries.Entry;
import Panels.Log.Entries.LogType;
import Panels.Log.Log;
import Team.Side;
import Utility.Spot;

import java.util.ArrayList;

public class EndGameDisplay extends Panel{

    int totalRounds;
    int northPoints;
    int southPoints;
    ArrayList<Figure> northDeaths;
    ArrayList<Figure> southDeaths;

    public int getTotalRounds() {
        return totalRounds;
    }

    public int getNorthPoints() {
        return northPoints;
    }

    public int getSouthPoints() {
        return southPoints;
    }

    public ArrayList<Figure> getNorthDeaths() {
        return northDeaths;
    }

    public ArrayList<Figure> getSouthDeaths() {
        return southDeaths;
    }

    /**
     * Constructor
     *
     * @param position Position of the top left tile of the panel
     * @param size     The size of the panel measured in tiles.
     */
    public EndGameDisplay(Spot position, Spot size) {
        super(position, size);
    }

    public void setData(int northPoints,int southPoints){
            totalRounds = Log.getRounds();
            this.northPoints = northPoints;
            this.southPoints = southPoints;
            this.northDeaths = getDiedFigures(Side.North);
            this.southDeaths = getDiedFigures(Side.South);
    }

    private ArrayList<Figure> getDiedFigures(Side side){
        ArrayList<Figure> toReturn = new ArrayList<>();
        for(Entry e: Log.getEntries())
        if(e.getPerpetrator().equals(side) && e.getLogType().equals(LogType.Death))
            toReturn.add(e.getFigure());
        return toReturn;
    }
}
