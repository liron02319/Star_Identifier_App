package com.example.star_identifier_app.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.star_identifier_app.R;

import java.util.ArrayList;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {

    ArrayList<AboutDescription> arrayList;
    Context context;

    public AboutAdapter(ArrayList<AboutDescription> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AboutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_about_design,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(arrayList.get(position).title);
        holder.description.setText(arrayList.get(position).description);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.get(position).isVisible){

                    holder.description.setVisibility(View.GONE);
                    holder.rl_descrtiption_line.setVisibility(View.GONE);
                    holder.rl_title_line.setVisibility(View.VISIBLE);
                    arrayList.get(position).isVisible=false;
                }
                else{
                    holder.description.setVisibility(View.VISIBLE);
                    holder.rl_descrtiption_line.setVisibility(View.VISIBLE);
                    holder.rl_title_line.setVisibility(View.GONE);
                    arrayList.get(position).isVisible=true;

                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        RelativeLayout rl_title_line;
        RelativeLayout rl_descrtiption_line;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            rl_title_line=itemView.findViewById(R.id.rl_title_line);
            rl_descrtiption_line=itemView.findViewById(R.id.rl_description_line);




        }
    }
}