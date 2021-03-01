package Panels.Log;

import Panels.Panel;
import Utility.*;

import java.util.ArrayList;
import java.util.Collections;

public class Log extends Panel {

    private int rounds;
    private final ArrayList<Entry> entries;

    public Log(Spot spot, Spot size) {
        super(spot, size);
        this.rounds = 0;
        this.entries = new ArrayList<>();
    }

    public int getRounds() {
        return rounds;
    }

    public void countRound() {
        this.rounds++;
    }

    public void addEvent(Attack attack) {
        this.entries.add(attack);
    }

    public void addEvent(Move move) {
        this.entries.add(move);
    }

    public void addEvent(Heal heal) {
        this.entries.add(heal);
    }

    public void addEvent(Death death) {
        this.entries.add(death);
    }
    public void addEvent(PlaceFig placeFig) {
        this.entries.add(placeFig);
    }

    public ArrayList<Entry> getEvents(int count) {
        ArrayList<Entry> res = new ArrayList<>(3);
        int added =0;
        Collections.reverse(entries);
        for (Entry e: entries){
            if(added ==3) break;
            if(e!=null){
                res.add(e);
                added++;
            }
        }
        Collections.reverse(entries);
        return res;
    }
}
