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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import dk.revsbech.galgeleg.R;

public class GameWonAkt extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap googleMap;
    TextView secretWord;
    TextView wrongGuesses;
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

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

        //MAPVIEW---------------------------------------------------------------
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}