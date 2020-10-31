package dk.revsbech.galgeleg.model;

public class ChallengeModeLogic {

    private int gamesWonStreak;
    private static ChallengeModeLogic single_instance=null;

    private ChallengeModeLogic(){
    }

    public void reset(){
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

    public void increaseStreak(){
        this.gamesWonStreak++;
    }


}
