package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.model.ChallengeModeLogic;
import dk.revsbech.galgeleg.model.HMConfig;

public class GameWonAkt extends AppCompatActivity{

    TextView secretWord;
    TextView wrongGuesses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_won_akt);

        String secretWordString = getIntent().getStringExtra("SecretWord").toUpperCase();
        secretWord=findViewById(R.id.SecretWordTextView);
        secretWord.setText(secretWordString);

        int wrongGuessesInt = getIntent().getIntExtra("NumOfGuesses",0);
        wrongGuesses=findViewById(R.id.InGuessesTextView);

        if (wrongGuessesInt==1){
            wrongGuesses.setText(getString(R.string.InOneGuess));
        }else{
            wrongGuesses.setText(String.format(wrongGuesses.getText().toString(),wrongGuessesInt));
        }


        //If it is challenge mode
        if (getIntent().getStringExtra("gameMode").equals("challenge")){
            Fragment fragment = new GameWon_CMfrag();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.GameWon_ChallengeModeFL, fragment)  // Empty container in layout
                    .commit();
        }


    }

    public void onBackPressed() {
        //If in challenge-mode
        if (getIntent().getStringExtra("gameMode").equals("challenge")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.exitOrNot));
            builder.setMessage(String.format(getString(R.string.sureToExitCM2), ChallengeModeLogic.getInstance().getGamesWonStreak()));

            builder.setPositiveButton(getString(R.string.yesSure), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent i = new Intent(getApplicationContext(), GameOverAkt.class);
                    i.putExtra("gameMode", "challenge");
                    i.putExtra("givenUp","true");
                    i.putExtra("guessedLastWord","true");
                    //Finish current acitivity
                    finish();
                    //Switch to new one
                    startActivity(i);

                }
            });
            builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });

            builder.show();


        } else{
            //Normal back-press
            GameWonAkt.super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}