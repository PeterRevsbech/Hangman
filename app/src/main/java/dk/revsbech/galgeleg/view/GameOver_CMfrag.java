package dk.revsbech.galgeleg.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.programlogic.ChallengeModeLogic;

public class GameOver_CMfrag extends Fragment {

    TextView streakNumberTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.game_over__cmfrag, container, false);

        streakNumberTV = root.findViewById(R.id.StreakNumberTV);
        String number = ChallengeModeLogic.getInstance().getGamesWonStreak()+"";
        streakNumberTV.setText(number);




        return root;
    }
}