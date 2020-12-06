package com.example.trialapp.TeacherActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.trialapp.R;
import com.example.trialapp.SubjectAdapter;

public class SubjectList extends AppCompatActivity {
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String arr[]={"Physics","Chemistry","Mathematics","Biology","Accountancy","Economics","Business Studies",
                "History","Geography","Computer Fundamentals","English"};

        recyclerView.setAdapter(new SubjectAdapter(arr));

    }
}