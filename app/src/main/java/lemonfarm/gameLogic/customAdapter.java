package cmpt276.as3.lemonfarm.gameLogic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cmpt276.as3.lemonfarm.R;

/**
 * custom adapter for score list view
 * holder keeps score info, set text box with info in holder
 */
public class customAdapter extends ArrayAdapter<score> {
    private scoreManager manager;
    private Context context;

    private static class Holder {
        TextView nickname;
        TextView score;
        TextView gameMode;
    }

    public customAdapter(ArrayList<score> data, Context c, scoreManager manager) {
        super(c, R.layout.score_list, data);
        this.manager = manager;
        this.context = c;
    }

    @androidx.annotation.NonNull
    @Override
    public View getView(final int position, View convertView, @androidx.annotation.NonNull ViewGroup parent) {
        context = parent.getContext();
        Holder viewHolder;

        if (convertView == null) {

            viewHolder = new Holder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.score_list, parent, false);

            //values to be stored
            viewHolder.nickname = convertView.findViewById(R.id.nickname);
            viewHolder.score = convertView.findViewById(R.id.score);
            viewHolder.gameMode = convertView.findViewById(R.id.gameMode);

            convertView.setTag(viewHolder);

        } else{
            viewHolder = (Holder) convertView.getTag();
        }

        //set values to the holders
        viewHolder.nickname.setText(manager.getMyScore().get(position).getNickname());
        viewHolder.score.setText(manager.getMyScore().get(position).getScore());
        viewHolder.gameMode.setText(manager.getMyScore().get(position).getGameMode());

        return convertView;
    }
}
