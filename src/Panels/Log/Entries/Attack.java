package Panels.Log.Entries;

import Figure.Figure;
import Team.Side;

public class Attack extends Entry {
    Figure victim;

    public Attack(int turn, Side perpetrator, Figure attacker, Figure victim) {
        super(turn, perpetrator, attacker, EntryType.Attack);
        this.victim = victim;
    }

    @Override
    public String toString() {
        return String.format("Turn %d: %s %s attacked %s",turn, perpetrator, figure, victim);
    }
}
