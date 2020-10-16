package dk.revsbech.galgeleg.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class HighScoreList {

    private HashMap<String,CategoryHS> categoryHSMap = new HashMap<String,CategoryHS>();
    private int maxEntriesPrCategory;


    public void ensureCategories(String[] proposedList){
        for (int i = 0; i < proposedList.length; i++) {
            if (!categoryHSMap.containsKey(proposedList[i])){
                categoryHSMap.put(proposedList[i],new CategoryHS(proposedList[i]));
            }
        }
    }


    public void addEntry(String category, int score, String playerName){
        //Ensure that category exists
        String[] categoryArray = new String[1];
        categoryArray[0] =category;
        ensureCategories(categoryArray);

        //Add entry
        categoryHSMap.get(category).addEntry(playerName,score);

        //If there are too many entries
        if(categoryHSMap.get(category).entries.size()>maxEntriesPrCategory){
            categoryHSMap.get(category).refineTopX(maxEntriesPrCategory);
        }
    }


    private class CategoryHS{
        String category;
        ArrayList<HSentry> entries = new ArrayList<HSentry>();

        CategoryHS(String category){
            this.category = category;
        }

        void addEntry(String playerName, int score){
            entries.add(new HSentry(playerName,score));
        }

        void refineTopX(int X){
            Collections.sort(entries);

            //Remove the smallest size-X entries - e.g. 11-10 = 1 entries
            for (int i = 0; i < entries.size()-X; i++) {
                entries.remove(i);
            }
        }


        private class HSentry implements Comparable<HSentry>{
            String playerName;
            int score;
            Date date;
            HSentry(String playerName,int score){
                this.playerName=playerName;
                this.score=score;
                this.date = new Date(System.currentTimeMillis());
            }

            @Override
            public int compareTo(HSentry other) {
                if (this.score>other.score){
                    return 1;
                } else if (this.score==other.score){
                    //If this entry is older than the other - it is a better score
                    if (this.date.compareTo(other.date)<0){
                        return 1;
                    } else{
                        return -1;
                    }
                } else {
                    return -1;
                }
            }


        }
    }
}
