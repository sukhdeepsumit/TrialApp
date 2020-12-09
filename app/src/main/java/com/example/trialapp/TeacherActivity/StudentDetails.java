package com.example.trialapp.TeacherActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trialapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StudentDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_details);
        recyclerView=findViewById(R.id.recyclerView);
        add=findViewById(R.id.addSubject);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDetails.this, StudentAddForm.class));
            }
        });


    }
}