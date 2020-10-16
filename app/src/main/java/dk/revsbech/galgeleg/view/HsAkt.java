package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;
import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.programlogic.HMLogic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class HsAkt extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner categorySpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hs_akt);

        categorySpinner = (Spinner) findViewById(R.id.HSCategorySpinner);
        //Get categories - not from network
        String[] categories= HMLogic.getInstance().getCategories();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item, categories);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(this);

/*
        //Setup Listview
        String[] lande = {"Danmark", "Norge", "Sverige", "Finland", "Holland", "Italien", "Tyskland",
                "Frankrig", "Spanien", "Portugal", "Nepal", "Indien", "Kina", "Japan", "Thailand"};

        ArrayAdapter ListViewAdapter = new ArrayAdapter(this, R.layout.hslistelem, R.id.HSListName, lande);

        ListView listView = new ListView(this);
        listView.setOnItemClickListener(this);
        listView.setAdapter(ListViewAdapter);

        setContentView(listView);

 */



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}