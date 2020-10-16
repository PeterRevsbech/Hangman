package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import dk.revsbech.galgeleg.programlogic.ChallengeModeLogic;
import dk.revsbech.galgeleg.programlogic.HMLogic;
import dk.revsbech.galgeleg.R;


public class PlayAkt extends AppCompatActivity implements View.OnClickListener {

    Button guessButton, cheatButton;
    HMLogic hmLogic;
    TextView wordView;
    TextView info;
    EditText inputField;
    ImageView nooseImage;
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
        // logic object (singleton)
        hmLogic = HMLogic.getInstance();

        //guess button
        guessButton = findViewById(R.id.play_guessButton);
        guessButton.setOnClickListener(this);

        //Input field
        inputField = findViewById(R.id.play_guessTextEdit);
        inputField.setOnClickListener(this);

        //Word view
        wordView = findViewById(R.id.play_WordView);
        wordView.setText(hmLogic.getVisibleWord());

        //Noose image
        nooseImage = findViewById(R.id.play_NooseImage);

        //Guess list
        guesses = findViewById(R.id.play_guessesText);
        guesses.setText(hmLogic.getUsedLetters().toString());

        ///Info text
        info = findViewById(R.id.play_infoText);
        info.setText("");

        //Cheat button
        cheatButton = findViewById(R.id.CheatButton);
        cheatButton.setOnClickListener(this);

        //Cheat word Text View
        cheatWordTV = findViewById(R.id.CheatWordTextView);
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
            hmLogic.guessLetter(guess);
            if (hmLogic.isLastGuessCorrect()) {
                wordView.setText(hmLogic.getVisibleWord());
            } else {
                updateNoose();
            }

            //If game is won
            if (hmLogic.isGameWon()) {
                gameWon();
            } else
                //If game is lost
                if (hmLogic.isGameLost()) {
                    gameOver();
                } else {
                    updateGuessList();
                }


        } else if (view == cheatButton) {
            cheatWordTV.setText(hmLogic.getSecretWord());
        }
    }

    public void updateGuessList() {
        guesses.setText(hmLogic.getUsedLetters().toString());
    }

    public void displayMsg(String msg) {
        info.setText(msg);
        System.out.println(msg);
    }

    public void updateNoose() {
        int wrongGuesses = hmLogic.getNumOfWrongGuesses();
        switch (wrongGuesses) {
            case 1:
                nooseImage.setImageResource(R.drawable.forkert1);
                break;
            case 2:
                nooseImage.setImageResource(R.drawable.forkert2);
                break;
            case 3:
                nooseImage.setImageResource(R.drawable.forkert3);
                break;
            case 4:
                nooseImage.setImageResource(R.drawable.forkert4);
                break;
            case 5:
                nooseImage.setImageResource(R.drawable.forkert5);
                break;
            case 6:
                nooseImage.setImageResource(R.drawable.forkert6);
                break;
            case 7:

                break;
        }
    }

    //Returns errormessage if invalid. Returns null otherwise
    public String validateGuess(String letter) {
        String invalidMsg = null;
        char guessChar = letter.toLowerCase().charAt(0);
        if (hmLogic.isGameWon()) {
            invalidMsg = (String) getText(R.string.alreadyWon);
        } else if (hmLogic.isGameLost()) {
            invalidMsg = (String) getText(R.string.alreadyLost);
        } else if (letter.length() != 1) {
            invalidMsg = (String) getText(R.string.onlyGuessOneLetter);
        } else if (hmLogic.getUsedLetters().contains(letter)) {
            invalidMsg = getText(R.string.alreadyGuessed) + " " + letter;
        } else if (!((guessChar >= 97 && guessChar <= 122) || (guessChar == 229 || guessChar == 230 || guessChar == 248))) {
            invalidMsg = (String) getText(R.string.invalidGuess);
        }
        if (invalidMsg != null) System.out.println(invalidMsg);
        return invalidMsg;
    }

    public void gameOver() {
        Intent i = new Intent(this, GameOverAkt.class);
        i.putExtra("SecretWord", hmLogic.getSecretWord());
        i.putExtra("gameMode", gameMode);
        startActivity(i);
    }

    public void gameWon() {
        Intent i = new Intent(this, GameWonAkt.class);
        i.putExtra("SecretWord", hmLogic.getSecretWord());
        i.putExtra("NumOfGuesses", hmLogic.getNumOfWrongGuesses());
        i.putExtra("gameMode", gameMode);
        startActivity(i);
    }

    public void onBackPressed() {
        if (gameMode.equals("challenge")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.saveStreakCM));
            String saveOrNot = String.format(getString(R.string.saveStreakOrNot), ChallengeModeLogic.getInstance().getGamesWonStreak());
            builder.setMessage(saveOrNot);

            builder.setPositiveButton(getString(R.string.yesSaveStreak), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    PlayAkt.super.onBackPressed();
                }
            });
            builder.setNegativeButton(getString(R.string.noExitWithoutSave), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    PlayAkt.super.onBackPressed();
                }
            });
            builder.setNeutralButton(getString(R.string.dontExit), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.show();

        } else if (gameMode.equals("single")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.exitOrNot));
            builder.setMessage(R.string.sureToExit);
            builder.setPositiveButton(getString(R.string.yesSure), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    PlayAkt.super.onBackPressed();
                }
            });
            builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.show();


        }


    }
}
