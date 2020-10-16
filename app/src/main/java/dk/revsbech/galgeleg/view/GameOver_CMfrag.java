package dk.revsbech.galgeleg.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.backend.HSManager;
import dk.revsbech.galgeleg.programlogic.ChallengeModeLogic;
import dk.revsbech.galgeleg.programlogic.HMLogic;

public class GameOver_CMfrag extends Fragment {

    TextView streakNumberTV, madeItOnHSTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.game_over__cmfrag, container, false);

        streakNumberTV = root.findViewById(R.id.GameOverStreakNumberTV);
        madeItOnHSTV=root.findViewById(R.id.MadeItHS);

        int streak = ChallengeModeLogic.getInstance().getGamesWonStreak();
        String streakString = streak+"";
        streakNumberTV.setText(streakString);

        String category = HMLogic.getInstance().getCategory();

        //Get HSManager to write to highscore if score made it
        boolean madeItOnHS = HSManager.getInstance().addEntryCandidate(category,streak,"Player",getContext());

        if (madeItOnHS){
            System.out.println("Made it on HS with score of " + streak + " in category " + category);
            madeItOnHSTV.setText(getString(R.string.madeHs));
        } else{
            System.out.println("Didn't make it on HS with streak " + streak + " in category " + category);
            madeItOnHSTV.setText(getString(R.string.didntMakeHS));
        }



        return root;
    }
}