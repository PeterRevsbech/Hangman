package dk.revsbech.galgeleg.model;

import java.util.ArrayList;

public class HMGame {

    private String secretWord;
    private ArrayList<String> usedLetters = new ArrayList<>();
    private String visibleWord;
    private int numOfWrongGuesses;
    private boolean lastGuessCorrect;
    private boolean gameWon;
    private boolean gameLost;

    public HMGame() {
        startNewGame();
    }


    public ArrayList<String> getUsedLetters() {
        return usedLetters;
    }

    public String getVisibleWord() {
        return visibleWord;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public int getNumOfWrongGuesses() {
        return numOfWrongGuesses;
    }

    public boolean isLastGuessCorrect() {
        return lastGuessCorrect;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameLost() {
        return gameLost;
    }


    public void startNewGame() {
        usedLetters.clear();
        numOfWrongGuesses = 0;
        gameWon = false;
        gameLost = false;
        secretWord = HMConfig.getInstance().getNewSecretWord();
        System.out.println("New game: The word is: " + secretWord);
        updateVisibleWord();
    }

    private void updateVisibleWord() {
        StringBuilder sb = new StringBuilder();
        gameWon = true;
        for (int n = 0; n < secretWord.length(); n++) {
            String letter = secretWord.substring(n, n + 1);
            if (letter.equals("-")) {
                sb.append("-");
            } else if (letter.equals(" ")) {
                sb.append(" ");
            } else if (usedLetters.contains(letter)) {
                sb.append(letter);
            } else {
                sb.append("*");
                gameWon = false;
            }
        }
        visibleWord=sb.toString();
    }


    public void guessLetter(String letter) {
        if (letter.length() != 1) return;
        System.out.println("The guessed letter is: " + letter);
        if (usedLetters.contains(letter)) return;
        if (gameWon || gameLost) return;

        usedLetters.add(letter);

        if (secretWord.contains(letter)) {
            lastGuessCorrect = true;
            System.out.println("The letter was correct: " + letter);
        } else {
            //Wrong guess
            lastGuessCorrect = false;
            System.out.println("The letter was incorrect: " + letter);
            numOfWrongGuesses = numOfWrongGuesses + 1;
            if (numOfWrongGuesses > 6) {
                gameLost = true;
            }
        }
        updateVisibleWord();
    }
}
