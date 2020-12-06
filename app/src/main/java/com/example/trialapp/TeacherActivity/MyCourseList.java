package com.example.trialapp.TeacherActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trialapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyCourseList extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_list);

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.addSubject);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyCourseList.this, SubjectList.class));
            }
        });
    }
}