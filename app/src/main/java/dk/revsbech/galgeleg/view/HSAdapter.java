package dk.revsbech.galgeleg.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dk.revsbech.galgeleg.R;
import dk.revsbech.galgeleg.backend.HSCategory;
import dk.revsbech.galgeleg.backend.HSEntry;

public class HSAdapter extends
        RecyclerView.Adapter<HSAdapter.ViewHolder> {

    public HSCategory hsCategory;

    public HSAdapter(HSCategory hsCategory){
        this.hsCategory =hsCategory;
    }

    @Override
    public HSAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.hsrecyclerelem, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(HSAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        HSEntry entry = hsCategory.getEntries().get(position);

        // Set item views based on your views and data model
        TextView nameTV = holder.name;
        nameTV.setText(entry.getPlayerName());
        TextView dateTV = holder.date;
        dateTV.setText(entry.getDate().toString());
        TextView scoreTV = holder.name;
        String scoreString = entry.getScore()+"";
        scoreTV.setText(scoreString);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return hsCategory.getEntries().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,score,date;
        public ViewHolder(View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.HSListName);
            score = (TextView) itemView.findViewById(R.id.HSListScore);
            date = (TextView) itemView.findViewById(R.id.HSListTime);
        }
    }
}
