package com.example.star_identifier_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.star_identifier_app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StarsAdapter extends RecyclerView.Adapter<StarsAdapter.StarsAdapterHolder>{
    Context context;
    List<StarCamera> Stars;

    public StarsAdapter(Context context, List<StarCamera> stars) {
        this.context = context;
        this.Stars = stars;
    }
    @NonNull
    @Override
    public StarsAdapter.StarsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_view_more_information,parent,false);
        return new StarsAdapter.StarsAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StarsAdapter.StarsAdapterHolder holder, int position) {
        StarCamera Star = Stars.get(position);
        holder.Star = Star;
        holder.tvStarName.setText(Star.getName());
        holder.tvStarClarity.setText("Ra "+Star.getRa());
        holder.tvStarMore.setText("Dec "+Star.getDec());

    }


    @Override
    public int getItemCount() {
        return this.Stars.size();
    }

    public class StarsAdapterHolder extends RecyclerView.ViewHolder {
        StarCamera Star;
        TextView tvStarName, tvStarClarity, tvStarMore;

        LinearLayout cartStarMore;

        public StarsAdapterHolder(View itemView) {
            super(itemView);
            tvStarName = itemView.findViewById(R.id.tvStarName);
            tvStarClarity = itemView.findViewById(R.id.tvStarClarity);
            tvStarMore = itemView.findViewById(R.id.tvStarMore);
            cartStarMore = itemView.findViewById(R.id.cartStarMore);
            itemView.setOnClickListener((v) ->{
            });
        }
    }
}
