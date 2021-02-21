package Panels.Log;

import Figure.Figure;
import Team.Side;

public abstract class Entry {
    protected final int turn;
    protected final Figure figure;
    protected final Side perpetrator;

    public Entry(int turn, Side perpetrator,Figure figure) {
        this.turn = turn;
        this.perpetrator = perpetrator;
        this.figure = figure;
    }
}
