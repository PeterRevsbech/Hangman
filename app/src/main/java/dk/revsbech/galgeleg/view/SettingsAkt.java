package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;

import dk.revsbech.galgeleg.R;

public class SettingsAkt extends AppCompatActivity implements View.OnClickListener {

    Spinner languageSpinner;
    String[] languages_array = {"Dansk (DK)", "English (EN)","A","B","C"};
    Button danishButton, englishButton, saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_akt);


        /*
        languageSpinner = (Spinner) findViewById(R.id.languageSpinner);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,languages_array);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(aa);
         */

        danishButton = findViewById(R.id.Settings_DanishButton);
        danishButton.setOnClickListener(this);
        englishButton = findViewById(R.id.Settings_EnglishButton);
        englishButton.setOnClickListener(this);
        saveButton = findViewById(R.id.Settings_SaveButton);
        saveButton.setOnClickListener(this);





    }

    @Override
    public void onClick(View view) {
        if (view==danishButton){
            setLocale("DA");
        } else if (view == englishButton){
            setLocale("EN");
        } else if (view == saveButton){

        }
    }

    public void setLocale(String language){
        Locale locale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(refresh);
    }
}