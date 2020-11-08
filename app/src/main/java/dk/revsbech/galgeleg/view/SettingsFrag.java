package dk.revsbech.galgeleg.view;


import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.backend.HighScoreList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFrag extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner languageSpinner;
    Locale locale;
    String languageSelected= "da-dk";
    Button saveChangesButton;
    Switch cheatsSwtich;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View root = inflater.inflate(R.layout.settings_frag,container,false);
        saveChangesButton = root.findViewById(R.id.Settings_SaveButton);
        saveChangesButton.setOnClickListener(this);

        languageSpinner = (Spinner) root.findViewById(R.id.languageSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.languages_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        languageSpinner.setAdapter(adapter);
        languageSpinner.setOnItemSelectedListener(this);


        //Setup cheats-switch
        cheatsSwtich = root.findViewById(R.id.cheatsSwitch);
        SharedPreferences mPrefs = getActivity().getSharedPreferences("HighScores",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("cheatsOn", "false");
        Boolean cheatsOn = gson.fromJson(json, Boolean.class);
        cheatsSwtich.setChecked(cheatsOn);

        return root;
    }


    public void setLocale(String language){
        locale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this.getActivity(), MainAkt.class);
        refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(refresh);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selection = (String) adapterView.getSelectedItem();

        if (selection.equals("Dansk")){
            languageSelected ="da-dk";
            System.out.println("Danish selected");
        } else if (selection.equals("English")){
            languageSelected ="en";
            System.out.println("English selected");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onClick(View view) {
        if (view ==saveChangesButton){
            System.out.println("Save button pressed");
            //TODO fix this
            //Tries to set locale - doesnt work properly
            setLocale(languageSelected);

            //Saves cheat preference to preference manager
            Boolean cheatPreference = cheatsSwtich.isChecked();
            SharedPreferences mPrefs = getActivity().getSharedPreferences("HighScores",MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(cheatPreference);
            prefsEditor.putString("cheatsOn", json);
            prefsEditor.apply();

        }

    }
}