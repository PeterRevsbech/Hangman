package dk.revsbech.galgeleg.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.model.ChallengeModeLogic;
import dk.revsbech.galgeleg.model.HMGame;

public class GameWon_CMfrag extends Fragment implements View.OnClickListener {

    Button nextWordButton;
    TextView streakNumberTV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.game_won_cmfrag, container, false);
        streakNumberTV = root.findViewById(R.id.WonStreakNumberTV);

       //Get streak number
        String number = ChallengeModeLogic.getInstance().getGamesWonStreak()+"";
        streakNumberTV.setText(number);


        //Configure next word button
        nextWordButton=root.findViewById(R.id.NextWordButton);
        nextWordButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        if (view == nextWordButton){
            //Switch activity
            Intent i = new Intent(getActivity(),PlayAkt.class);
            i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
            i.putExtra("gameMode","challenge");
            getActivity().finish();
            startActivity(i);
            getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        }
    }

}