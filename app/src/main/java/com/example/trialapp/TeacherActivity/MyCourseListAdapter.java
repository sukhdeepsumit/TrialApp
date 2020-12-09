package com.example.trialapp.TeacherActivity;
  
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trialapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyCourseListAdapter extends RecyclerView.Adapter<MyCourseListAdapter.MyCourseViewHolder> {

    ArrayList<String> subjects;

    public MyCourseListAdapter(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public static class MyCourseViewHolder extends RecyclerView.ViewHolder {

        Button subjectButton;

        public MyCourseViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectButton = itemView.findViewById(R.id.subject);
        }
    }

    @NonNull
    @Override
    public MyCourseListAdapter.MyCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_course_list_design, parent, false);
        return new MyCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCourseListAdapter.MyCourseViewHolder holder, int position) {
        holder.subjectButton.setText(subjects.get(position));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
