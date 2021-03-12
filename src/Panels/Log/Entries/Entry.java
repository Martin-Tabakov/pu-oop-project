package Panels.Log.Entries;

import Figure.Figure;
import Team.Side;

public abstract class Entry {
    protected final int turn;
    protected final Figure figure;
    protected final Side perpetrator;
    protected final EntryType entryType;

    public EntryType getLogType() {
        return entryType;
    }

    public Figure getFigure() {
        return figure;
    }

    public Side getPerpetrator() {
        return perpetrator;
    }

    public Entry(int turn, Side perpetrator, Figure figure, EntryType entryType) {
        this.turn = turn;
        this.perpetrator = perpetrator;
        this.figure = figure;
        this.entryType = entryType;
    }
}
