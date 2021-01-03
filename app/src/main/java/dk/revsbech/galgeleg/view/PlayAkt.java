package dk.revsbech.galgeleg.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import dk.revsbech.galgeleg.model.ChallengeModeLogic;
import dk.revsbech.galgeleg.model.HMConfig;
import dk.revsbech.galgeleg.model.HMGame;
import dk.revsbech.galgeleg.R;


public class PlayAkt extends AppCompatActivity implements View.OnClickListener {

    Button guessButton, cheatButton;
    HMGame hmGame;
    TextView wordView;
    TextView info;
    EditText inputField;
    TextView guesses;
    TextView cheatWordTV;
    String gameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_akt);

        //Set gamemode
        gameMode = getIntent().getStringExtra("gameMode");

        //Initializing--------------------------------------------------------------
        // Make new HMGame object
        hmGame = new HMGame();

        //guess button
        guessButton = findViewById(R.id.play_guessButton);
        guessButton.setOnClickListener(this);

        //Input field
        inputField = findViewById(R.id.play_guessTextEdit);
        inputField.setOnClickListener(this);

        //Word view
        wordView = findViewById(R.id.play_WordView);
        wordView.setText(hmGame.getVisibleWord());

        //Guess list
        guesses = findViewById(R.id.play_guessesText);
        guesses.setText(hmGame.getUsedLetters().toString());

        ///Info text
        info = findViewById(R.id.play_infoText);
        info.setText("");

        //Cheat button
        cheatButton = findViewById(R.id.CheatButton);
        cheatButton.setOnClickListener(this);

        //Cheat word Text View
        cheatWordTV = findViewById(R.id.CheatWordTextView);

        //If cheats turned off - cheat button and cheat word should be invisible
        if (!isCheatsOn()){
            cheatButton.setVisibility(View.INVISIBLE);
            cheatWordTV.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == guessButton) {
            String unprocessedGuess = inputField.getText().toString();
            if (unprocessedGuess.length() == 0) {
                return;
            }
            String guess = unprocessedGuess.toLowerCase().substring(0, 1);
            displayMsg(getText(R.string.youguessed) + " " + guess);
            inputField.setText("");


            //Checks if guess is valid
            String invalidMsg = validateGuess(guess);
            //If guess is invalid - display and return
            if (invalidMsg != null) {
                displayMsg(invalidMsg);
                return;
            }

            //If guess was vaild - make guess
            hmGame.guessLetter(guess);
            if (hmGame.isLastGuessCorrect()) {
                wordView.setText(hmGame.getVisibleWord());
            } else {
                updateNoose();
            }

            //If game is won
            if (hmGame.isGameWon()) {
                gameWon();
            } else
                //If game is lost
                if (hmGame.isGameLost()) {
                    gameOver();
                } else {
                    updateGuessList();
                }


        } else if (view == cheatButton) {
            cheatWordTV.setText(hmGame.getSecretWord());
        }
    }

    public void updateGuessList() {
        guesses.setText(hmGame.getUsedLetters().toString());
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
            builder.setMessage(String.format(getString(R.string.sureToExitCM),streak));
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
        SharedPreferences mPrefs = getSharedPreferences("HighScores",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("cheatsOn", "false");
        Boolean cheatsOn = gson.fromJson(json, Boolean.class);
        return cheatsOn;
    }
}
