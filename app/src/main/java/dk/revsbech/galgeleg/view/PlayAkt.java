package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;

import dk.revsbech.galgeleg.model.ChallengeModeLogic;
import dk.revsbech.galgeleg.model.HMGame;
import dk.revsbech.galgeleg.R;


public class PlayAkt extends AppCompatActivity implements View.OnClickListener {

    Button cheatButton;
    HMGame hmGame;
    TextView wordView;
    TextView info;
    TextView cheatWordTV;
    String gameMode;
    GridView simpleGrid;
    int letterImgs[] = {R.drawable.letter0,R.drawable.letter1,R.drawable.letter2,R.drawable.letter3,R.drawable.letter4,R.drawable.letter5,R.drawable.letter6,R.drawable.letter7,R.drawable.letter8,R.drawable.letter9,R.drawable.letter10,R.drawable.letter11,R.drawable.letter12,R.drawable.letter13,R.drawable.letter14,R.drawable.letter15,R.drawable.letter16,R.drawable.letter17,R.drawable.letter18,R.drawable.letter19,R.drawable.letter20,R.drawable.letter21,R.drawable.letter22,R.drawable.letter23,R.drawable.letter24,R.drawable.letter25,R.drawable.letter26,R.drawable.letter27,R.drawable.letter28};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_akt);

        //Set gamemode
        gameMode = getIntent().getStringExtra("gameMode");

        //Initializing--------------------------------------------------------------
        // Make new HMGame object
        hmGame = new HMGame();


        //Word view
        wordView = findViewById(R.id.play_WordView);
        wordView.setText(hmGame.getVisibleWord());
        if (hmGame.getSecretWord().length()>20){
            wordView.setTextSize(20);
        } else if (hmGame.getSecretWord().length()>15){
            wordView.setTextSize(30);
        } else if (hmGame.getSecretWord().length()>10){
            wordView.setTextSize(40);
        }


        ///Info text
        info = findViewById(R.id.play_infoText);
        info.setText("");

        //Cheat button
        cheatButton = findViewById(R.id.CheatButton);
        cheatButton.setOnClickListener(this);

        //Cheat word Text View
        cheatWordTV = findViewById(R.id.CheatWordTextView);
        cheatWordTV.setText("");

        //If cheats turned off - cheat button and cheat word should be invisible
        if (!isCheatsOn()){
            cheatButton.setVisibility(View.INVISIBLE);
            cheatWordTV.setVisibility(View.INVISIBLE);
        }

        //GridView setup
        simpleGrid = (GridView) findViewById(R.id.letterGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        GridViewAdapter gridViewAdapter = new GridViewAdapter(getApplicationContext(), letterImgs);
        simpleGrid.setAdapter(gridViewAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Onclick on letter
                String guess;
                if (position==26){
                    guess="æ";
                } else if (position==27){
                    guess="ø";
                } else if (position==28){
                    guess="å";
                } else{
                    guess = ""+(char) (position + 97);
                }
                //Checks if guess is valid
                validateGuess(guess);

                //Display message
                displayMsg(getString(R.string.youguessed) + " " + guess.toUpperCase());

                //If guess was vaild - make guess
                hmGame.guessLetter(guess);
                if (hmGame.isLastGuessCorrect()) {
                    wordView.setText(hmGame.getVisibleWord().toUpperCase());
                } else {
                    updateNoose();
                }

                //In any case - hide the clicked letter
                view.setVisibility(View.INVISIBLE);

                //If game is won
                if (hmGame.isGameWon()) {
                    gameWon();
                } else
                    //If game is lost
                    if (hmGame.isGameLost()) {
                        gameOver();
                    }


            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view == cheatButton) {
            cheatWordTV.setText(hmGame.getSecretWord().toUpperCase());
        }
    }


    public void displayMsg(String msg) {
        info.setText(msg);
        System.out.println(msg);
    }


    public void updateNoose() {
        int wrongGuesses = hmGame.getNumOfWrongGuesses();
        View background = findViewById(R.id.playAkt);
        switch (wrongGuesses) {
            case 1:
                background.setBackgroundResource(R.drawable.wrong1);
                break;
            case 2:
                background.setBackgroundResource(R.drawable.wrong2);
                break;
            case 3:
                background.setBackgroundResource(R.drawable.wrong3);
                break;
            case 4:
                background.setBackgroundResource(R.drawable.wrong4);
                break;
            case 5:
                background.setBackgroundResource(R.drawable.wrong5);
                break;
            case 6:
                background.setBackgroundResource(R.drawable.wrong6);
                break;
            case 7:

                break;
        }
    }

    //Returns errormessage if invalid. Returns null otherwise
    public String validateGuess(String letter) {
        String invalidMsg = null;
        char guessChar = letter.toLowerCase().charAt(0);
        if (hmGame.isGameWon()) {
            invalidMsg = (String) getText(R.string.alreadyWon);
        } else if (hmGame.isGameLost()) {
            invalidMsg = (String) getText(R.string.alreadyLost);
        } else if (letter.length() != 1) {
            invalidMsg = (String) getText(R.string.onlyGuessOneLetter);
        } else if (hmGame.getUsedLetters().contains(letter)) {
            invalidMsg = getText(R.string.alreadyGuessed) + " " + letter;
        } else if (!((guessChar >= 97 && guessChar <= 122) || (guessChar == 229 || guessChar == 230 || guessChar == 248))) {
            invalidMsg = (String) getText(R.string.invalidGuess);
        }
        if (invalidMsg != null) System.out.println(invalidMsg);
        return invalidMsg;
    }

    public void gameOver() {
        Intent i = new Intent(this, GameOverAkt.class);
        i.putExtra("SecretWord", hmGame.getSecretWord());
        i.putExtra("gameMode", gameMode);
        i.putExtra("givenUp","false");
        i.putExtra("guessedLastWord","false");

        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }

    public void gameWon() {
        if (gameMode.equals("challenge")){
            ChallengeModeLogic.getInstance().increaseStreak();
        }
        Intent i = new Intent(this, GameWonAkt.class);
        i.putExtra("SecretWord", hmGame.getSecretWord());
        i.putExtra("NumOfGuesses", hmGame.getNumOfWrongGuesses());
        i.putExtra("gameMode", gameMode);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.exitOrNot));
        if (gameMode.equals("challenge")){
            int streak = ChallengeModeLogic.getInstance().getGamesWonStreak();
            builder.setMessage(String.format(getString(R.string.sureToExitCM2),streak));
        } else if (gameMode.equals("single")){
            builder.setMessage(R.string.sureToExit);
        }


        builder.setPositiveButton(getString(R.string.yesSure), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(getApplicationContext(), GameOverAkt.class);
                i.putExtra("gameMode", gameMode);
                i.putExtra("givenUp","true");
                i.putExtra("guessedLastWord","false");
                i.putExtra("secretWord", hmGame.getSecretWord());
                //Finish current acitivity
                finish();
                //Switch to new one
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }

        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();
    }

    boolean isCheatsOn(){
        SharedPreferences mPrefs = getSharedPreferences("Preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("cheatsOn", "false");
        Boolean cheatsOn = gson.fromJson(json, Boolean.class);
        return cheatsOn;
    }
}
