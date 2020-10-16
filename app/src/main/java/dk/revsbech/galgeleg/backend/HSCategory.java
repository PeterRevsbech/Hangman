package dk.revsbech.galgeleg.backend;

import java.util.ArrayList;
import java.util.Collections;

public class HSCategory {
    String category;
    ArrayList<HSEntry> entries = new ArrayList<HSEntry>();

    HSCategory(String category) {
        this.category = category;
    }

    void addEntry(HSEntry entry) {
        entries.add(entry);
    }

    void refineTopX(int X) {
        Collections.sort(entries);

        //Remove the smallest size-X entries - e.g. 11-10 = 1 entries
        for (int i = 0; i < entries.size() - X; i++) {
            entries.remove(i);
        }
    }

    public int getLowestEntryScore(){
        if (entries.size()==0){
            return 0;
        } else{
            return entries.get(0).score;
        }
    }

    public ArrayList<HSEntry> getEntries(){
        return this.entries;
    }



}
