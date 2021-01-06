package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.backend.HSManager;
import dk.revsbech.galgeleg.model.ChallengeModeLogic;
import dk.revsbech.galgeleg.model.HMConfig;

public class GameOverAkt extends AppCompatActivity {

    TextView secretWordTV, headerTV;
    boolean givenUp,guessedLastWord,challengeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_akt);

        secretWordTV=findViewById(R.id.GameOverWordTextView);
        headerTV = findViewById(R.id.DidntGuessWord);

        givenUp = getIntent().getStringExtra("givenUp").equals("true");
        guessedLastWord = getIntent().getStringExtra("guessedLastWord").equals("true");
        challengeMode = getIntent().getStringExtra("gameMode").equals("challenge");

        if (!givenUp && !guessedLastWord){ //Player just lost a game
            //Set header
            headerTV.setText(getString(R.string.didntguessword));

            //Tell player what secret word was
            String secretWordString = getIntent().getStringExtra("SecretWord");
            secretWordTV.setText(secretWordString);

        } else if (givenUp && !guessedLastWord){ //Player Gave up while in a game
            //Set header
            headerTV.setText(getString(R.string.youGaveUpTheWordWas));

            //Tell player what secret word was
            String secretWordString = getIntent().getStringExtra("secretWord").toUpperCase();
            secretWordTV.setText(secretWordString);

        } else if (givenUp && guessedLastWord){ //Player chose to exit after winning
            //Set header
            headerTV.setText(getString(R.string.youGaveUp));

            //Hide secretWordTV
            secretWordTV.setVisibility(View.INVISIBLE);

        }

        //If it is challenge mode
        if (challengeMode){
            Fragment fragment = new GameOver_CMfrag();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.GameOver_ChallengeModeFL, fragment)  // Empty container in layout
                    .commit();
        }
    }


    public void onBackPressed() {
        //If in challenge-mode
        if (getIntent().getStringExtra("gameMode").equals("challenge")){

            //Find out if valid for highscores
            int streak = ChallengeModeLogic.getInstance().getGamesWonStreak();
            String category = HMConfig.getInstance().getCurrentCategory();
            boolean validForHS = HSManager.getInstance().canMakeItOnHs(category,streak,getApplicationContext());

            if (validForHS){ //If it is a valid high score - ask before leaving
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.exitOrNot));
                builder.setMessage(R.string.sureToExitBeforeHS);

                builder.setPositiveButton(getString(R.string.yesSure), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Normal back-press
                        GameOverAkt.super.onBackPressed();
                    }
                });
                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Do nothing
                    }
                });

                builder.show();
            } else { //If in Challenge mode but not valid for HS
                //Normal back-press
                GameOverAkt.super.onBackPressed();
            }

        } else{ //If not in Challenge mode
            //Normal back-press
            GameOverAkt.super.onBackPressed();
        }
    }

}