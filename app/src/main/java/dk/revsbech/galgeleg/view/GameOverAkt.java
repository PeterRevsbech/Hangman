package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import dk.revsbech.galgeleg.R;

public class GameOverAkt extends AppCompatActivity {

    TextView secretWordTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_akt);


        String secretWordString = getIntent().getStringExtra("SecretWord");
        secretWordTV=findViewById(R.id.GameOverWordTextView);
        secretWordTV.setText(secretWordString);





    }
}