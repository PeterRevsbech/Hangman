package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.backend.HSManager;
import dk.revsbech.galgeleg.model.ChallengeModeLogic;
import dk.revsbech.galgeleg.model.HMConfig;

public class MainAkt extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button singleGameButton, settingsButton, hsButton, challengeModeButton;
    Spinner categorySpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_akt);

        singleGameButton = findViewById(R.id.singleGameButton);
        singleGameButton.setOnClickListener(this);

        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(this);

        hsButton = findViewById(R.id.hsButton);
        hsButton.setOnClickListener(this);

        challengeModeButton = findViewById(R.id.challengeModeButton);
        challengeModeButton.setOnClickListener(this);


        //Create HMlogic object in background thread
        Executor bgThread = Executors.newSingleThreadExecutor();
        Handler uiThread = new Handler();
        bgThread.execute(() ->{
            System.out.println("Initializing HMLogic");
            HMConfig.getInstance().initWithNetwork();
        });


        //Get categories and set spinner
        bgThread.execute(() ->{
            categorySpinner = (Spinner) findViewById(R.id.MainAktCategorySpinner);
            //Get categories
            String[] categories= HMConfig.getInstance().getCategories();

            //Ensure that the cateogires are in the HS
            HSManager.getInstance().ensureCategories(categories, MainAkt.this);

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this,android.R.layout.simple_selectable_list_item, categories);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            uiThread.post(()->{
                categorySpinner.setAdapter(adapter);
                categorySpinner.setOnItemSelectedListener(this);
            });
        });


    }

    @Override
    public void onClick(View view) {
        if (view == singleGameButton){
            startGameIfReady("single");

        } else if (view == settingsButton){

            Fragment fragment = new SettingsFrag();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.Main_SettingsFragFL, fragment)  // tom container i layout
                    .addToBackStack(null).commit();



        } else if (view == hsButton){
            Intent i = new Intent(this,HsAkt.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        } else if (view == challengeModeButton){
            startGameIfReady("challenge");
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String selection = (String) adapterView.getSelectedItem();

        Executor bgThread = Executors.newSingleThreadExecutor();
        Handler uiThread = new Handler();
        bgThread.execute(() ->{
            HMConfig.getInstance().switchCategory(selection);
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void startGameIfReady(String gameMode){
        //If it is done loading
        if (HMConfig.getInstance().isLoadingDone()){

            //Switch activity
            Intent i;
            if (gameMode.equals("challenge")){
                i = new Intent(this,CMRulesAkt.class);
            } else {
                i = new Intent(this,PlayAkt.class);
                i.putExtra("gameMode",gameMode);
            }
            i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Don't add to backstack
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        } else { //If it is not done loading
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.waitForLoad),
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void playMusic() {
        Intent intent = new Intent(MainAkt.this, BackgroundSoundService.class);
        startService(intent);
    }

    public void stopMusicService(){
        Intent stopIntent = new Intent(getBaseContext(),BackgroundSoundService.class);
        stopIntent.setAction(BackgroundSoundService.ACTION_STOP);
        stopService(stopIntent);
    }

    public boolean isMusicOn(){
        SharedPreferences mPrefs = getSharedPreferences("Preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("musicOn", "false");
        Boolean cheatsOn = gson.fromJson(json, Boolean.class);
        return cheatsOn;
    }


    @Override
    protected void onStop() {
        if (HMConfig.getInstance().isRefreshing()){
            //Do nothing yet - isRefreshing will be set false onDestroy()
        } else{
            //stop the music (maybe the app is no longer in foreground)
            stopMusicService();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (!HMConfig.getInstance().isRefreshing()){
            //If MainAkt is closed normally - stop the music
            stopMusicService();
        } else{
            //If MainAkt was refreshing due to lanugage change - dont stop the music
            HMConfig.getInstance().setRefreshing(false);
        }
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        if (isMusicOn()) {
            playMusic();
        }
        super.onStart();
    }


}