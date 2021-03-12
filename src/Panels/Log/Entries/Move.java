package Panels.Log.Entries;

import Figure.Figure;
import Team.Side;
import Utility.Spot;

public class Move extends Entry {
    Spot newSpot;

    public Move(int turn, Side perpetrator, Figure figure, Spot newSpot) {
        super(turn, perpetrator, figure, EntryType.Move);
        this.newSpot = newSpot;
    }
    @Override
    public String toString() {
        return String.format("Turn %d: %s %s moved to %s", turn, perpetrator, figure.getSymbols(), newSpot);
    }
}
