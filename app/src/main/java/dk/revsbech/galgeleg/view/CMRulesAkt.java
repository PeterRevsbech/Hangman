package dk.revsbech.galgeleg.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.model.ChallengeModeLogic;

public class CMRulesAkt extends AppCompatActivity implements View.OnClickListener {

    Button okButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cm_rules_akt);
        okButton = findViewById(R.id.startCMbutton);
        okButton.setOnClickListener(this );
    }


    @Override
    public void onClick(View v) {
        if (v==okButton){
            Intent i = new Intent(this,PlayAkt.class);
            i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Don't add to backstack
            i.putExtra("gameMode","challenge");
            ChallengeModeLogic.getInstance().reset();
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
    }
}