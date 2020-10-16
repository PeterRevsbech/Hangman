package dk.revsbech.galgeleg.backend;

import java.util.ArrayList;
import java.util.HashMap;

public class HighScoreList {

    private HashMap<String, HSCategory> categoryHSMap;
    private int maxEntriesPrCategory;

    public HighScoreList(){
        this.maxEntriesPrCategory = 3;
        this.categoryHSMap = new HashMap<String, HSCategory>();
    }


    public void ensureCategories(String[] proposedList){
        for (int i = 0; i < proposedList.length; i++) {
            if (!categoryHSMap.containsKey(proposedList[i])){
                categoryHSMap.put(proposedList[i],new HSCategory(proposedList[i]));
            }
        }
    }


    public boolean addEntry(String category, int score, String playerName){
        //Ensure that category exists
        String[] categoryArray = new String[1];
        categoryArray[0] =category;
        ensureCategories(categoryArray);


        boolean betterThanWorst = score > categoryHSMap.get(category).getLowestEntryScore();
        boolean listNotFull = categoryHSMap.get(category).entries.size() < maxEntriesPrCategory;
        boolean scoreNotZero = score> 0;

        //Add entry if better than worst in category or score is non-zero and list is not full
        if (betterThanWorst || (listNotFull && scoreNotZero)){
            //Add entry
            categoryHSMap.get(category).addEntry(new HSEntry(playerName,score));

            //If there are too many entries
            if(categoryHSMap.get(category).entries.size()>maxEntriesPrCategory){
                categoryHSMap.get(category).refineTopX(maxEntriesPrCategory);
            }
            //Indicate, that it made it on the HS
            return true;
        }
        //Indicate that it didn't make it on the HS
        return false;
    }


    public HSCategory getCategoryList(String category){
        return categoryHSMap.get(category);
    }
}
