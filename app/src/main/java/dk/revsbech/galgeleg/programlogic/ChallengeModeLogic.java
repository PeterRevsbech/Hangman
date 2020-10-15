package dk.revsbech.galgeleg.programlogic;

import java.io.IOException;

public class ChallengeModeLogic {

    private int gamesWonStreak;
    private String category;
    private static ChallengeModeLogic single_instance=null;

    private ChallengeModeLogic(){
    }

    public void reset(String category){
        this.category = category;
        this.gamesWonStreak=0;
    }

    public static ChallengeModeLogic getInstance(){
        if (single_instance == null){
            single_instance = new ChallengeModeLogic();
        }
        return single_instance;
    }

    public int getGamesWonStreak() {
        return gamesWonStreak;
    }

    public int increaseStreak(){
        return this.gamesWonStreak++;
    }


}
