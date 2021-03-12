package Panels.Log.Entries;

import Figure.Figure;
import Team.Side;

public class PlaceFig extends Entry{
    public PlaceFig(int turn, Side perpetrator, Figure figure) {
        super(turn, perpetrator, figure,LogType.PlaceFigure);
    }
    @Override
    public String toString() {
        return String.format("%s %s is placed on the board", perpetrator, figure);
    }
}
