package dk.revsbech.galgeleg.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import dk.revsbech.galgeleg.backend.SheetReader;

public class HMConfig {
    ArrayList<String> wordList = new ArrayList<>();
    private boolean loadingDone = false;
    private static HMConfig single_instance =null;
    private String currentCategory = "Default";
    private boolean refreshing = false;

    private HMConfig() {}

    public static HMConfig getInstance(){
        if(single_instance==null){
            single_instance = new HMConfig();
        }
        return single_instance;
    }

    //Uses Network communication to get wordlist from a new category
    public void switchCategory(String newCategory){
        loadingDone=false;
        try {
            wordList = SheetReader.getInstance().readSpecificCategory(newCategory);
            this.currentCategory =newCategory;
        } catch (IOException e){
            e.printStackTrace();
        }
        loadingDone=true;
    }

    public String getCurrentCategory(){
        return this.currentCategory;
    }

    public void initWithNetwork(){
        loadingDone=false;

        SheetReader sr = SheetReader.getInstance();

        try{
            sr.readCategoryList();
            wordList =sr.readSpecificCategory(null);

        } catch (IOException e){
            e.printStackTrace();
            //backup words if reading the google sheet fails
            wordList.add("bil");
            wordList.add("computer");
            wordList.add("programmering");
            wordList.add("motorvej");
            wordList.add("busrute");
            wordList.add("gangsti");
            wordList.add("skovsnegl");
            wordList.add("solsort");
            wordList.add("tyve");
        }
        loadingDone=true;
    }

    public boolean isLoadingDone() {
        return loadingDone;
    }

    public String getNewSecretWord(){
        return wordList.get(new Random().nextInt(wordList.size()));
    }

    public String[] getCategories(){
        return SheetReader.getInstance().getCategories();
    }

    public boolean isRefreshing() {
        return refreshing;
    }

    public void setRefreshing(boolean refreshing) {
        this.refreshing = refreshing;
    }


}
