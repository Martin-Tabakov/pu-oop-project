package Panels.Log;

import Panels.Log.Entries.Entry;
import Panels.Panel;
import Utility.*;

import java.util.ArrayList;
import java.util.Collections;

public class Log extends Panel {

    private static int rounds = 1;
    private static boolean fullRound = true;
    private static final ArrayList<Entry> entries = new ArrayList<>();

    public Log(Spot spot, Spot size) {
        super(spot, size);
    }

    /**
     * Returns the amount of played round
     * @return int
     */
    public static int getRounds() {
        return rounds;
    }

    /**
     * Increases the amount of rounds if a complete round if made
     */
    public static void countRound() {
        fullRound = !fullRound;
        if(!fullRound) return;
        rounds++;
    }

    /**
     * Adds a log entry
     * @param entry Entry
     */
    public static void addEntry(Entry entry) {
        entries.add(entry);
    }

    /**
     * Returns the last entries that happened
     * @param count Amount of entries to return
     * @return  ArrayList<Entry> entries
     */
    public static ArrayList<Entry> getEntries(int count) {
        ArrayList<Entry> res = new ArrayList<>(count);
        int added =0;
        Collections.reverse(entries);
        for (Entry e: entries){
            if(added ==count) break;
            if(e!=null){
                res.add(e);
                added++;
            }
        }
        Collections.reverse(entries);
        return res;
    }
}
