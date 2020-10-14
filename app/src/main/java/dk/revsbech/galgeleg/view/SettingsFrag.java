package dk.revsbech.galgeleg.view;


import dk.revsbech.galgeleg.R;

import android.content.Intent;
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
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class SettingsFrag extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner languageSpinner;
    Locale locale;
    String languageSelected;
    Button saveChangesButton;


    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceStat){
        View root = i.inflate(R.layout.settings_frag,container,false);
        saveChangesButton = root.findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(this);


        /*
        //These lines are borrowed from https://www.javatpoint.com/android-spinner-example-------------------
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        languageSpinner= (Spinner) root.findViewById(R.id.languageSpinner);
        languageSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        String[] languages = {"Dansk","English"};
        ArrayAdapter aa = new ArrayAdapter(this.getActivity(),android.R.layout.simple_spinner_item,languages);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        languageSpinner.setAdapter(aa);
        //-----------------------------

         */

        return root;
    }


    public void setLocale(String language){
        locale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this.getActivity(), MainActivity.class);
        refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(refresh);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selection = (String) adapterView.getSelectedItem();

        if (selection.equals("Dansk")){
            languageSelected ="da-dk";
            System.out.println("Danish selected");
        } else {
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
            setLocale(languageSelected);
        }
    }
}