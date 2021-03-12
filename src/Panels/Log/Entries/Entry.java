package Panels.Log.Entries;

import Figure.Figure;
import Team.Side;

public abstract class Entry {
    protected final int turn;
    protected final Figure figure;
    protected final Side perpetrator;
    protected final LogType logType;

    public LogType getLogType() {
        return logType;
    }

    public int getTurn() {
        return turn;
    }

    public Figure getFigure() {
        return figure;
    }

    public Side getPerpetrator() {
        return perpetrator;
    }

    public Entry(int turn, Side perpetrator, Figure figure, LogType logType) {
        this.turn = turn;
        this.perpetrator = perpetrator;
        this.figure = figure;
        this.logType = logType;
    }
}
