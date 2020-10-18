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
        Collections.sort(entries); //Sorts with smallest first using comparator

        //Remove the smallest size-X entries - e.g. 11-10 = 1 entries
        for (int i = 0; i < entries.size() - X; i++) {
            entries.remove(i);
        }

        Collections.reverse(entries); //Want largest first, not smallest first
    }

    public int getLowestEntryScore(){
        if (entries.size()==0){
            return 0;
        } else{
            //Return score of last entry, since they are sorted highest first
            return entries.get(entries.size()-1).score;
        }
    }

    public ArrayList<HSEntry> getEntries(){
        return this.entries;
    }

    public boolean isEmpty(){
        return entries.size()==0;
    }



}
