package Panels.Log;

import Figure.Figure;
import Team.Side;

public class PlaceFig extends Entry{
    public PlaceFig(int turn, Side perpetrator, Figure figure) {
        super(turn, perpetrator, figure);
    }
    @Override
    public String toString() {
        return String.format("Turn %d: %s %s is placed on the board", turn, perpetrator, figure);
    }
}
