package dk.revsbech.galgeleg.backend;

public class HSManager {


    private HighScoreList highScoreList;
    private static HSManager single_instance=null;

    private HSManager(){

    }

    public static HSManager getInstance(){
        if (single_instance == null){
            single_instance = new HSManager();
        }
        return single_instance;
    }

    public HighScoreList getHighScoreList() {
        return highScoreList;
    }




}
