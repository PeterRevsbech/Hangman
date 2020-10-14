package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.programlogic.HMLogic;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button playButton, settingsButton, hsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_akt);

        playButton = findViewById(R.id.playButton);
        settingsButton = findViewById(R.id.settingsButton);
        hsButton = findViewById(R.id.hsButton);

        playButton.setOnClickListener(this);
        playButton.setEnabled(false);
        settingsButton.setOnClickListener(this);
        hsButton.setOnClickListener(this);

        //Create HMlogic object in background thread
        Executor bgThread = Executors.newSingleThreadExecutor();
        Handler uiThread = new Handler();
        bgThread.execute(() ->{
            System.out.println("Creating HMLogic");
            HMLogic.getInstance();
            uiThread.post(()->{
                playButton.setEnabled(true);
            });
        });


    }

    @Override
    public void onClick(View view) {
        if (view == playButton){
            //Start new game, when starting the activity
            HMLogic.getInstance().startNewGame();

            //Switch activity
            Intent i = new Intent(this,PlayAkt.class);
            i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
            startActivity(i);
        } else if (view == settingsButton){
            /*
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Fragment fragment = new SettingsFrag();
            fragmentTransaction.add(R.id.settingsFrag,fragment).commit();


             */





        } else if (view == hsButton){
            Intent i = new Intent(this,HsAkt.class);
            startActivity(i);

        }
    }


}