package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import dk.revsbech.galgeleg.R;

public class GameWonAkt extends AppCompatActivity {

    TextView secretWord;
    TextView wrongGuesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_won_akt);


        String secretWordString = getIntent().getStringExtra("SecretWord");
        secretWord=findViewById(R.id.SecretWordTextView);
        secretWord.setText(secretWordString);

        int wrongGuessesInt = getIntent().getIntExtra("NumOfGuesses",0);
        wrongGuesses=findViewById(R.id.InGuessesTextView);

        if (wrongGuessesInt==1){
            wrongGuesses.setText(getString(R.string.InOneGuess));
        }else{
            wrongGuesses.setText(String.format(wrongGuesses.getText().toString(),wrongGuessesInt));
        }




    }
}