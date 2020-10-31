package dk.revsbech.galgeleg.backend;

import java.util.Date;

public class HSEntry implements Comparable<HSEntry> {
    private final String playerName;
    private final int score;
    private final Date date;

    HSEntry(HSEntryBuilder hsEntryBuilder) {
        this.playerName = hsEntryBuilder.playerName;
        this.score = hsEntryBuilder.score;
        this.date = hsEntryBuilder.date;
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

    public static class HSEntryBuilder {
        private final String playerName;
        private final int score;
        private Date date;

        public HSEntryBuilder(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        // for all class attributes we define an attribute method that returns a UserBuilder
        public HSEntryBuilder date() {
            this.date = new Date(System.currentTimeMillis());
            return this;
        }

        // finally we return a new instance of the User class copying the values of the UserBuilder
        public HSEntry build() {
            return new HSEntry(this);
        }
    }

}
