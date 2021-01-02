package dk.revsbech.galgeleg.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HighScoreList {

    private HashMap<String, HSCategory> categoryHSMap;
    private final int maxEntriesPrCategory;

    public HighScoreList(int maxEntriesPrCategory){
        this.maxEntriesPrCategory = maxEntriesPrCategory;
        this.categoryHSMap = new HashMap<String, HSCategory>();
    }

    public boolean isEmpty(){
        //Check if hashmap is empty
        if (categoryHSMap.isEmpty()){
            return true;
        } 
        
        //Check if hashmap contains only empty categories
        for (HSCategory category : categoryHSMap.values()){
            if (!category.isEmpty()){
                return false;
            }
        }

        //If all were empty - return true
        return true;
    }


    public void ensureCategories(String[] proposedList){
        for (int i = 0; i < proposedList.length; i++) {
            if (!categoryHSMap.containsKey(proposedList[i])){
                categoryHSMap.put(proposedList[i],new HSCategory(proposedList[i]));
            }
        }
    }


    public void addEntry(String category, int score, String playerName){
        //Ensure that category exists
        String[] categoryArray = new String[1];
        categoryArray[0] =category;
        ensureCategories(categoryArray);

        //Add entry with HSEntry builder
        HSEntry entry = new HSEntry.HSEntryBuilder(playerName,score)
                .date()
                .build();
        categoryHSMap.get(category).addEntry(entry);

        //Refine to sort and assure that number of entires is correct
        categoryHSMap.get(category).refineTopX(maxEntriesPrCategory);
    }

    public boolean canMakeItOnHS(String category, int score){
        //Ensure that category exists
        String[] categoryArray = new String[1];
        categoryArray[0] =category;
        ensureCategories(categoryArray);

        //Valid entry if better than worst in category or score is non-zero and list is not full
        boolean betterThanWorst = score > categoryHSMap.get(category).getLowestEntryScore();
        boolean listNotFull = categoryHSMap.get(category).entries.size() < maxEntriesPrCategory;
        boolean scoreNotZero = score> 0;
        return betterThanWorst ||(listNotFull && scoreNotZero);
    }


    public HSCategory getCategoryList(String category){
        return categoryHSMap.get(category);
    }

    public HashMap<String, HSCategory> getCategoryHSMap(){
        return categoryHSMap;
    }
}
