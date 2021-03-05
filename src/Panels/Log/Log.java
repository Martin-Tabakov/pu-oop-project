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

    public static int getRounds() {
        return rounds;
    }

    public static void countRound() {
        fullRound = !fullRound;
        if(!fullRound) return;
        rounds++;
    }

    public static void addEntry(Entry entry) {
        entries.add(entry);
    }

    public static ArrayList<Entry> getEvents(int count) {
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
