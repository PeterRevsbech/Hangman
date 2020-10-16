package dk.revsbech.galgeleg.backend;

import java.util.Date;

public class HSEntry implements Comparable<HSEntry> {
    String playerName;
    int score;
    Date date;

    HSEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
        this.date = new Date(System.currentTimeMillis());
    }

    @Override
    public int compareTo(HSEntry other) {
        if (this.score > other.score) {
            return 1;
        } else if (this.score == other.score) {
            //If this entry is older than the other - it is a better score
            if (this.date.compareTo(other.date) < 0) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public Date getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }

    public String getPlayerName() {
        return playerName;
    }
}
