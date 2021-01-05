package dk.revsbech.galgeleg.view;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import dk.revsbech.galgeleg.R;

public class GridViewAdapter extends BaseAdapter {
    Context context;
    int letters[];
    LayoutInflater inflater;
    public GridViewAdapter(Context applicationContext, int[] letters) {
        this.context = applicationContext;
        this.letters = letters;
        inflater = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return letters.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.letters_gridview, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.letter); // get the reference of ImageView
        icon.setImageResource(letters[i]); // set logo images
        return view;
    }
}