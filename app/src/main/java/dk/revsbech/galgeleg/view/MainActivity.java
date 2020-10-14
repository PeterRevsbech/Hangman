package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dk.revsbech.galgeleg.R;

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
        settingsButton.setOnClickListener(this);
        hsButton.setOnClickListener(this);






    }

    @Override
    public void onClick(View view) {
        if (view == playButton){
            Intent i = new Intent(this,PlayAkt.class);
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