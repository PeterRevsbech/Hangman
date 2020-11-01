package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import dk.revsbech.galgeleg.R;

public class GameWonAkt extends AppCompatActivity {

    MapView mMapView;
    private GoogleMap googleMap;
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


        //If it is challenge mode
        if (getIntent().getStringExtra("gameMode").equals("challenge")){
            Fragment fragment = new GameWon_CMfrag();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.GameWon_ChallengeModeFL, fragment)  // Empty container in layout
                    .commit();
        }





    }
}