package dk.revsbech.galgeleg.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.backend.HSManager;
import dk.revsbech.galgeleg.model.ChallengeModeLogic;
import dk.revsbech.galgeleg.model.HMLogic;

public class GameOver_CMfrag extends Fragment implements View.OnClickListener {

    TextView streakNumberTV, madeItOnHSTV, writeNameTV;
    EditText writeNameTE;
    Button submitButton;
    String category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.game_over__cmfrag, container, false);

        streakNumberTV = root.findViewById(R.id.GameOverStreakNumberTV);
        madeItOnHSTV=root.findViewById(R.id.MadeItHS);

        writeNameTV = root.findViewById(R.id.writeNameTextView);
        writeNameTE = root.findViewById(R.id.writeNameTextEdit);
        submitButton = root.findViewById(R.id.sendToHSButton);
        submitButton.setOnClickListener(this);


        int streak = ChallengeModeLogic.getInstance().getGamesWonStreak();
        String streakString = streak+"";
        streakNumberTV.setText(streakString);

        category = HMLogic.getInstance().getCategory();

        //Get HSManager to write to highscore if score made it
        boolean validForHS = HSManager.getInstance().canMakeItOnHs(category,streak,getContext());
        if (validForHS){
            System.out.println("Made it on HS with score of " + streak + " in category " + category);
            madeItOnHSTV.setText(getString(R.string.madeHs));
        } else{
            System.out.println("Didn't make it on HS with streak " + streak + " in category " + category);
            madeItOnHSTV.setText(getString(R.string.didntMakeHS));
            writeNameTE.setVisibility(View.INVISIBLE);
            writeNameTV.setVisibility(View.INVISIBLE);
            submitButton.setVisibility(View.INVISIBLE);

        }
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v==submitButton){
            String playerName = writeNameTE.getText().toString();
            int streak = ChallengeModeLogic.getInstance().getGamesWonStreak();
            HSManager.getInstance().addEntryToHS(category,streak,playerName,getContext());
            this.getActivity().finish();
        }
    }
}