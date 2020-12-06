package com.example.trialapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    String[]data;

    public SubjectAdapter(String[] data) {
        this.data = data;
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder
    {
        Button button;


        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.button);
        }
    }




    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.card,parent,false);
        return new SubjectViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        holder.button.setText(data[position]);


    }

    @Override
    public int getItemCount() {
        return data.length;
    }


}