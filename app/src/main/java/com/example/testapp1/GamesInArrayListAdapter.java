package com.example.testapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GamesInArrayListAdapter extends BaseAdapter {

    Context mContext;

    ArrayList<GameClass> games = new ArrayList<>();

    public GamesInArrayListAdapter(Context context, ArrayList<GameClass> games) {
        this.games = games;
        mContext = context;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, parent, false);
        }

        GameClass tempGame = (GameClass) getItem(position);

        TextView name = convertView.findViewById(R.id.textView_item_name);
        TextView description = convertView.findViewById(R.id.textView_item_description);
        TextView tasksCount = convertView.findViewById(R.id.textView_item_task_count);

        name.setText(tempGame.getName());
        description.setText(tempGame.getDescription());
        tasksCount.setText(String.valueOf(tempGame.getTasksCount()) + " местоположений");


        return convertView;
    }
}
