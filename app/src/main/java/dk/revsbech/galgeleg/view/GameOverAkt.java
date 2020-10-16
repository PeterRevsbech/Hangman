package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.TextView;

import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.backend.HSManager;
import dk.revsbech.galgeleg.programlogic.HMLogic;

public class GameOverAkt extends AppCompatActivity {

    TextView secretWordTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_akt);


        String secretWordString = getIntent().getStringExtra("SecretWord");
        secretWordTV=findViewById(R.id.GameOverWordTextView);
        secretWordTV.setText(secretWordString);


        //If it is challenge mode
        if (getIntent().getStringExtra("gameMode").equals("challenge")){
            Fragment fragment = new GameOver_CMfrag();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.GameOver_ChallengeModeFL, fragment)  // Empty container in layout
                    .commit();
        }




    }
}