package com.example.star_identifier_app.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.star_identifier_app.R;

import java.util.List;

public class StarAdapter extends ArrayAdapter<Star> {

    public StarAdapter(Context context, List<Star> stars) {
        super(context, 0, stars);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Star star = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_star, parent, false);
        }

        TextView nameView = convertView.findViewById(R.id.textStarName);
        TextView raDecView = convertView.findViewById(R.id.textRaDec);

        nameView.setText(star.getName());
        raDecView.setText(String.format("RA: %.2f, DEC: %.2f", star.getRA(), star.getDEC()));

        return convertView;
    }
}