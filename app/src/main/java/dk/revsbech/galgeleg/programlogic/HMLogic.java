package dk.revsbech.galgeleg.programlogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import dk.revsbech.galgeleg.backend.SheetReader;

public class HMLogic {

    /** AHT afprøvning er muligeOrd synlig på pakkeniveau */
    ArrayList<String> wordList = new ArrayList<String>();
    private String secretWord;
    private ArrayList<String> usedLetters = new ArrayList<String>();
    private String visibleWord;
    private int numOfWrongGuesses;
    private boolean lastGuessCorrect;
    private boolean gameWon;
    private boolean gameLost;
    private static HMLogic single_instance =null;

    private HMLogic() {

        SheetReader sr = SheetReader.getInstance();

        try{
            sr.readCategories();
            wordList =sr.readSheet(null);

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
    }


    public static HMLogic getInstance(){
        if(single_instance==null){
            single_instance = new HMLogic();
        }
        return single_instance;
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

    public boolean isGameDone() {
        return gameLost || gameWon;
    }


    public void startNewGame() {
        usedLetters.clear();
        numOfWrongGuesses = 0;
        gameWon = false;
        gameLost = false;
        if (wordList.isEmpty()) throw new IllegalStateException("Empty list of words");
        secretWord = wordList.get(new Random().nextInt(wordList.size()));
        System.out.println("New game: The word is: "+ secretWord);
        updateVisibleWord();
    }


    private void updateVisibleWord() {
        visibleWord = "";
        gameWon = true;
        for (int n = 0; n < secretWord.length(); n++) {
            String letter = secretWord.substring(n, n + 1);
            if (letter.equals("-")){
                visibleWord=visibleWord+"-";
            } else if (letter.equals(" ")){
                visibleWord = visibleWord+" ";
            } else if (usedLetters.contains(letter)) {
                visibleWord = visibleWord + letter;
            } else {
                visibleWord = visibleWord + "*";
                gameWon = false;
            }
        }
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
    public String[] getCategories(){
        return SheetReader.getInstance().getCategories();
    }

    public void switchCategory(String newCategory){
        try {
            wordList = SheetReader.getInstance().readSheet(newCategory);
        } catch (IOException e){
            e.printStackTrace();
        }
    }



}
