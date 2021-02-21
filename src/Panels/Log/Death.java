package Panels.Log;

import Figure.Figure;
import Team.Side;

public class Death extends Entry {
    public Death(int turn, Side perpetrator, Figure figure) {
        super(turn, perpetrator, figure);
    }

    @Override
    public String toString() {
        return String.format("Turn %d: %s %s died", turn, perpetrator, figure);
    }
}
