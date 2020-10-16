package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.backend.HSCategory;
import dk.revsbech.galgeleg.backend.HSEntry;
import dk.revsbech.galgeleg.backend.HSManager;
import dk.revsbech.galgeleg.programlogic.HMLogic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class HsAkt extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    String chosenCategory = "Default";
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

        // Lookup the recyclerview in activity layout
        RecyclerView hsRV = (RecyclerView) findViewById(R.id.rvContacts);

        // Initialize contacts
        HSCategory hsCategory = HSManager.getInstance().getHighScoreList(HsAkt.this).getCategoryList("Medicin");
        // Create adapter passing in the sample user data
        HSAdapter hsAdapter = new HSAdapter(hsCategory);
        // Attach the adapter to the recyclerview to populate items
        hsRV.setAdapter(hsAdapter);
        // Set layout manager to position the items
        hsRV.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}