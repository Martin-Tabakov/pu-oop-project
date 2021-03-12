package Panels.Log.Entries;

import Figure.Figure;
import Team.Side;

public class Heal extends Entry {
    int currentHP;

    public Heal(int turn, Side perpetrator, Figure figure, int currentHP) {
        super(turn, perpetrator, figure, EntryType.Heal);
        this.currentHP = currentHP;
    }

    @Override
    public String toString() {
        return String.format("Turn %d: %s %s Healed to %d hp", turn, perpetrator, figure, currentHP);
    }
}
