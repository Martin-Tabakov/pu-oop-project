package Panels.Log;

import Figure.Figure;
import Team.Side;

public class Heal extends Entry {
    int hpRegenerated;

    public Heal(int turn, Side perpetrator, Figure figure, int hpRegenerated) {
        super(turn, perpetrator, figure);
        this.hpRegenerated = hpRegenerated;
    }

    @Override
    public String toString() {
        return String.format("Turn %d: %s %s Healed for %d hp", turn, perpetrator, figure, hpRegenerated);
    }
}
