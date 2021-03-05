package Panels.Log.Entries;

import Figure.Figure;
import Team.Side;

public class RemoveObstacle extends Entry{
    public RemoveObstacle(int turn, Side perpetrator, Figure figure) {
        super(turn, perpetrator, figure);
    }

    @Override
    public String toString() {
        return String.format("Turn %d: %s %s Cleared obstacle",turn, perpetrator, figure);
    }
}
