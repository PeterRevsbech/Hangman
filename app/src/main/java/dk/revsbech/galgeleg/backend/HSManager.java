package dk.revsbech.galgeleg.backend;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class HSManager {


    private HighScoreList highScoreList;
    private static HSManager single_instance=null;
    private int maxEntriesPrCategory =10;

    private HSManager(){

    }

    public static HSManager getInstance(){
        if (single_instance == null){
            single_instance = new HSManager();
        }
        return single_instance;
    }

    public HighScoreList getHighScoreList(Context context) {
        readHSfromPM(context);
        return highScoreList;
    }

    public boolean canMakeItOnHs(String category, int score, Context context){
        readHSfromPM(context);
        return highScoreList.canMakeItOnHS(category,score);
    }

    //Returns true if it made it to the HS
    public void addEntryToHS(String category, int score, String playerName, Context context){
        //Read list from PM
        readHSfromPM(context);
        //Add to list
        highScoreList.addEntry(category,score,playerName);
        //Save to PM
        saveHStoPM(context);

    }

    private void readHSfromPM(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("HighScores",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("HighScores", "");
        this.highScoreList = gson.fromJson(json, HighScoreList.class);
        if (this.highScoreList==null){//If there is no HS list yet
            this.highScoreList = new HighScoreList(maxEntriesPrCategory);
        }

    }

    private void saveHStoPM(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("HighScores",MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(this.highScoreList);
        prefsEditor.putString("HighScores", json);
        prefsEditor.apply();
    }

    //Uses network
    public void ensureCategories(String[] categories,Context context){
        readHSfromPM(context);
        highScoreList.ensureCategories(categories);
        saveHStoPM(context);
    }




}
