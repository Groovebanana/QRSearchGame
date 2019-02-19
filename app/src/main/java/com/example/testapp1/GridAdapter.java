package com.example.testapp1;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    Context context;
    ArrayList<Bitmap> list;

    public GridAdapter(Context context, ArrayList<Bitmap> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.gridview_item, parent, false);
        }

        Bitmap bitmap = (Bitmap) getItem(position);

        ImageView qr = convertView.findViewById(R.id.imageView_qr_print);
        TextView count = convertView.findViewById(R.id.textView_Qr_id_print);

        qr.setImageBitmap(bitmap);
        count.setText(String.valueOf(getItemId(position)));


        return convertView;
    }
}
